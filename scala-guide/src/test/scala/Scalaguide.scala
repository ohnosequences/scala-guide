package ohnosequences.scalaguide.test

import ohnosequences.scalaguide._

import org.scalatest.FunSuite


import ohnosequences.scalaguide._

import Type._


class ScalaguideTest extends FunSuite {

  def buh[T <: Type](value: ValueOf[T])(implicit from: ValueOf[T] => T): T = from(value)

  test("Dummy test coming from the template") {

    val z = new useUser {}

    val dog = Dog =>> "asfdadf"
    val stringyDog: String = dog

    assert( "asfdadf" eq (dog:String) )
    assert(buh(dog) == Dog)

    val asdfasdfasdf = User( 123123 )

    val ug = "edu" isA Dog
  }
}
