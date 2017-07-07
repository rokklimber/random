package org.example.hello

/**
 * Simple teaser service interface, that defines simple interface to service
 * for teaser example.  All subclasses will compute the maximal summing set of integers
 * from a list of random digits
 */
trait TeaserService[T]
{
  /**
   * style name f this style used to map into restful service map
   */
  val style : String
  
  /**
   * return maximal set of numbers with greatest sum given random digits
   */
  def getMaxNumbers()(digits : List[T], outputs : Int) : Array[T]
}

/**
 * 
 */
abstract class TeaserServiceWrapper extends TeaserService[Int]