
```scala
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
```




[test/scala/monoidsExample.scala]: monoidsExample.scala.md
[test/scala/errors.scala]: errors.scala.md
[main/scala/override.scala]: ../../main/scala/override.scala.md
[main/scala/typeMembers.scala]: ../../main/scala/typeMembers.scala.md
[main/scala/taggedTypes.scala]: ../../main/scala/taggedTypes.scala.md
[main/scala/refinementsAndWith.scala]: ../../main/scala/refinementsAndWith.scala.md
[main/scala/typeclasses.scala]: ../../main/scala/typeclasses.scala.md
[main/scala/existentials.scala]: ../../main/scala/existentials.scala.md
[main/scala/errors.scala]: ../../main/scala/errors.scala.md