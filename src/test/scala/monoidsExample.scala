package ohnosequences.scalaguide.test.typeclasses

class groupOpsAndSyntax extends org.scalatest.FunSuite {

  import ohnosequences.scalaguide.typeclasses.algs._

  test("syntax for int group") {

    import IntPlusModule._
    import StdGroupSyntax._

    val two = IntPlus := 2
    val four = two + two

    assert {

      four === (IntPlus := 4)
    }
  }

}