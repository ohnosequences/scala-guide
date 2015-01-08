package ohnosequences.scalaguide.test.typeclasses

class groupOpsAndSyntax extends org.scalatest.FunSuite {

  import ohnosequences.scalaguide.typeclasses.algs._

  test("syntax for int group") {

    import IntPlusModule._
    import StdGroupSyntax._

    val two = IntPlus := 2
    val four = two plus two

    assert {

      four === (IntPlus := 4)
    }
  }

  test("syntax for fields") {

    import ohnosequences.cosas.types._

    import IntField._
    import StdMultGroupSyntax._
    import IntMultModule._
    import IntPlusModule._


    val two = IntField := 2
    val oo = IntMult := 2
    val four = two * two
    val fourAgain = two plus two
    val eight = fourAgain plus four
  }

}