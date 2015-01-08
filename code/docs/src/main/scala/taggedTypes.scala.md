
```scala
package ohnosequences.scalaguide

trait Type {
  
  type Me = this.type
  type Raw

  implicit def typeOf(value: ValueOf[Me]): Me = this

}
final class ValueOf[T <: Type](val value: T#Raw) extends AnyVal {}

object Type {

  type RawOf[T <: Type] = T#Raw

  implicit class TypeOps[T <: Type](val t: T) {

    def =>>(value: Type.RawOf[T]): ValueOf[T] = new ValueOf[T](value)
    def apply(value: Type.RawOf[T]): ValueOf[T] = new ValueOf[T](value)
  }

  implicit def toRaw[T <: Type](value: ValueOf[T]): T#Raw = value.value

  implicit class AnyOps[T](val t: T) {

    def isA[TT <: Type { type Raw = T } ](tt: TT): ValueOf[TT] = new ValueOf[TT](t)
  }

}

trait VertexType extends Type

object User extends VertexType { 

  type Raw = Int

  implicit def userOps(value: ValueOf[User.type]) = new VertexValueOps[User.type](value) {

    def sayHi = 23
  }

}

object Dog extends VertexType {

  type Raw = String

  implicit def dogOps(value: ValueOf[Dog.type]) = new VertexValueOps[Dog.type](value) {

    def sayHi = "fuck you Tagged"
  }
}

abstract class VertexValueOps[V <: VertexType](val value: ValueOf[V]) {

  def sayHi: V#Raw
}

trait useUser {
  
  val buh = User =>> 234234

  val z: Int = buh.sayHi

  val doge = Dog =>> "woof"

  val uho = Dog("hola")

  println(doge.sayHi)
}


```


------

### Index

+ src
  + test
    + scala
      + [Scalaguide.scala][test/scala/Scalaguide.scala]
      + [errors.scala][test/scala/errors.scala]
  + main
    + scala
      + [override.scala][main/scala/override.scala]
      + [typeMembers.scala][main/scala/typeMembers.scala]
      + [taggedTypes.scala][main/scala/taggedTypes.scala]
      + [refinementsAndWith.scala][main/scala/refinementsAndWith.scala]
      + [existentials.scala][main/scala/existentials.scala]
      + [Scalaguide.scala][main/scala/Scalaguide.scala]
      + [errors.scala][main/scala/errors.scala]

[test/scala/Scalaguide.scala]: ../../test/scala/Scalaguide.scala.md
[test/scala/errors.scala]: ../../test/scala/errors.scala.md
[main/scala/override.scala]: override.scala.md
[main/scala/typeMembers.scala]: typeMembers.scala.md
[main/scala/taggedTypes.scala]: taggedTypes.scala.md
[main/scala/refinementsAndWith.scala]: refinementsAndWith.scala.md
[main/scala/existentials.scala]: existentials.scala.md
[main/scala/Scalaguide.scala]: Scalaguide.scala.md
[main/scala/errors.scala]: errors.scala.md