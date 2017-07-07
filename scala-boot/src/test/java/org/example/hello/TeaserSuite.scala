package org.example.hello

import java.util.Random

import org.example.hello.service.FasterTeaser
import org.example.hello.service.SimpleTeaser
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Stopwatch
import org.junit.runner.Description
import org.scalatest.junit.AssertionsForJUnit;

/**
 * simple junit test suite for teaser application
 * 
 * runs each instance of teaser 10,000 times to calculate runtime statistics
 */
class TeaserSuite extends AssertionsForJUnit
{
  def r : Random = new Random()
  
  /**
   * stopwatch rule, calculates start and stop for each test
   */
  @Rule
  def timer = new Stopwatch
  {
    override def succeeded(nanos: Long, description : Description)
    {
      println(description.getMethodName() + " succeeded, time taken " + nanos);
    }
    
    override def failed(nanos: Long, t : Throwable, description : Description)
    {
      println(description.getMethodName() + " failed, time taken " + nanos);
    }
    
    override def finished(nanos: Long, description : Description)
    {
      println(description.getMethodName() + " finished, time taken " + nanos);
    }
  }
      
  /**
   * tests simple teaser implementation
   */
  @Test def simpleTest()
  {
    val realLen = 7
    var digits = List.fill(realLen)(r.nextInt(10))
    val simple = new SimpleTeaser()
   
    1 to 10 foreach
    {
      _ =>
        
      1 to 1000 foreach
      {
        _ => 
        val output = simple.getMaxNumbers()(digits.asInstanceOf[List[Int]], 2).asInstanceOf[Array[Int]]
      }
      
    }
  }
  
  @Test def fasterTest()
  {
    val realLen = 7
    var digits = List.fill(realLen)(r.nextInt(10))
    val faster = new FasterTeaser()
   
    1 to 10 foreach
    {
      _ =>
        
      1 to 1000 foreach
      {
        _ => 
        val output = faster.getMaxNumbers()(digits.asInstanceOf[List[Int]], 2).asInstanceOf[Array[Int]]
      }
      
    }
  }
    
  /**
   * tests better teaser implementation
   */
  @Test def valueTest()
  {
    val realLen = 20
    var digits = List.fill(realLen)(r.nextInt(10))
    val simple = new SimpleTeaser()
    val faster = new FasterTeaser()
   
    1 to 10 foreach
    {
      _ =>
        
      1 to 1000 foreach
      {
        _ => 
        val simpleOutput = (simple.getMaxNumbers()(digits.asInstanceOf[List[Int]], 2)).asInstanceOf[Array[Int]]
        val fasterOutput = (simple.getMaxNumbers()(digits.asInstanceOf[List[Int]], 2)).asInstanceOf[Array[Int]]
        
        Assert.assertEquals(simpleOutput(0), fasterOutput(0))
        Assert.assertEquals(simpleOutput(1), fasterOutput(1))
      }
      
    }
  }
}