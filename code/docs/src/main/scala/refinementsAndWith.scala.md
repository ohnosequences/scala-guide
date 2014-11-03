
```scala
package ohnosequences.scalaguide

trait Uh
case class Uh1() extends Uh
trait Oh
case class UhOh1() extends Uh with Oh

trait Foo[A] { def bar[B](b: B): Foo[A with B] }
  
object Foo {
  
  def baz[A](foo: Foo[A]) = foo bar 1

  def baz2[A](foo: Foo[A]): Foo[A with Int] = foo bar[Int] 1

  // crashes
  // def baz3[X](foo: Foo[X]): Foo[X with Int] = foo bar 1
}
```


------

### Index

+ src
  + test
    + scala
      + [Scalaguide.scala][test/scala/Scalaguide.scala]
  + main
    + scala
      + [typeMembers.scala][main/scala/typeMembers.scala]
      + [taggedTypes.scala][main/scala/taggedTypes.scala]
      + [refinementsAndWith.scala][main/scala/refinementsAndWith.scala]
      + [existentials.scala][main/scala/existentials.scala]
      + [Scalaguide.scala][main/scala/Scalaguide.scala]
      + [errors.scala][main/scala/errors.scala]

[test/scala/Scalaguide.scala]: ../../test/scala/Scalaguide.scala.md
[main/scala/typeMembers.scala]: typeMembers.scala.md
[main/scala/taggedTypes.scala]: taggedTypes.scala.md
[main/scala/refinementsAndWith.scala]: refinementsAndWith.scala.md
[main/scala/existentials.scala]: existentials.scala.md
[main/scala/Scalaguide.scala]: Scalaguide.scala.md
[main/scala/errors.scala]: errors.scala.md