package org.example.hello

import java.util.Date
import java.util.Random

import scala.collection.JavaConversions.mapAsScalaMap

import org.example.hello.service.FasterTeaser
import org.example.hello.service.SimpleTeaser
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Inject
import com.google.inject.TypeLiteral
import com.google.inject.multibindings.MapBinder

import javax.annotation.PostConstruct

/**
 * simple restful web controller for teaser web app
 */
@Controller
class AppController extends AbstractModule
  {
  
  // create a binding mapper for dynamic injection
  var diBinder : MapBinder[String, TeaserService[Int]] = null
  def r : Random = new Random()
  
  // di inject map of services
  var map : Map[String, TeaserService[Int]] = null
  
  /**
   * simple hello world style alive service
   */
  @RequestMapping(Array("/alive"))
  def alive(@RequestParam(value="name", required=false, defaultValue="Unknown") name: String, 
      model: Model) : String = {
    model.addAttribute("name", name)
    model.addAttribute("date", new Date())
    "greeting"
  }
  
  /**
   * entry point into teaser context, will display index page with list of teasers
   */
  @RequestMapping(Array("/teasers"))
  def teasers() : String = {
    "teaser"
  }
  
  /**
   * map teaser requests to rest interface
   * /teaser/v{version}/{style}[?length=<int>}
   * 
   * version integer value version of this interface
   * style type of teaser object requested must match value mapped to service
   * length number of digits to use, should be less than 18
   */
  @RequestMapping(Array("/teaser/v{version}/{style}"))
  def teaser(@PathVariable version : String, @PathVariable style: String, @RequestParam(required = false, value = "length") length : String, model : Model) : String = {
    model.addAttribute("teaserStyle", style)
    model.addAttribute("apiVersion", version)

    
    // parse length value or use default if not parseable
    var realLen = -1
    toInt(length) match
    {
      case Some(i) => realLen = i
      case None => realLen = 7
    }
    
    // max digits 18, set explanation message
    if(length.toInt > 18)
    {
      realLen = 18
      model.addAttribute("message", "Length exceeds max (18), reduced to 18 to prevent overflow")
    }
    
    // create array of random digits
    val digits = List.fill(realLen)(r.nextInt(10))
    
    // bind call to mapped injector
    val output = map.apply(style).getMaxNumbers()(digits.asInstanceOf[List[Int]], 2).asInstanceOf[Array[Int]]
    model.addAttribute("num1",output(0))
    model.addAttribute("num2",output(1))
    model.addAttribute("sum",((output(0).toLong + output(1).toLong).toString()))
    model.addAttribute("requestedLen", realLen)
    
    "teaserOutput"
  }
  
  /**
   * create injector for service mapping at object construction time
   */
  @PostConstruct
  def init()
  {
    Guice.createInjector(this).injectMembers(this)
  }

  def configure()
  {
    println("Controller Configure Called")
    diBinder = MapBinder.newMapBinder(binder, new TypeLiteral[String] {}, new TypeLiteral[TeaserService[Int]] {})
    
    // add simple mapping
    val simple = new SimpleTeaser()
    diBinder.addBinding(simple.style()).toInstance(simple)
    val faster = new FasterTeaser()
    diBinder.addBinding(faster.style()).toInstance(faster)
  }
  
  /**
   * optional length conversion, returns none if int not parseable or empty
   * 
   * s string to convert to int
   */
  def toInt(s: String): Option[Int] = {
    try {
      Some(s.toInt)
    } catch {
      case e: Exception => None
    }
  }
  
  /**
   * di assignment of map from binder to class object
   */
  @Inject
  def mapServices(map : java.util.Map[String, TeaserService[Int]])
  {
    this.map = map.toMap
  }
}