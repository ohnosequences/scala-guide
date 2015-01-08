
```scala
package ohnosequences.scalaguide

trait AnyImageOfAux {
 
  type F[X]
  type A
  type FA
}

trait ImageOf[F0[_], FA0] extends AnyImageOfAux {
  
  type F[X] = F0[X]
  type FA = FA0
  type A
}

sealed class ImageOfAux[F0[_], FA0 <% F0[A0], A0] extends ImageOf[F0, FA0] with AnyImageOfAux {

  type A = A0

  implicit def as(uh: FA0): F0[A0] = uh
}


 
object ImageOf {
 
  type imgOf[F[_]] = {
    type is[FA] = ImageOf[F,FA]
  }

  type Apply[G[_]] = {

    type to[B] = {

      type is[GB] = ImageOfAux[G,GB,B]
    }
  }
  
  implicit def inImage[F0[_], FA0 <% F0[A0], A0]: ImageOfAux[F0, FA0, A0] = new ImageOfAux[F0, FA0, A0] {}

  implicit def asWrapped[F[_], FA <% F[A], A](fa: FA): F[A] = fa
}
 
object Test {
 
  import ImageOf._
 
  // compiler crashes here
  object stringList {

    def a = "asdfad"

    val uh = List("aasfd", "ad")

    val oooo = implicitly[List ImageOf List[String]]

    val ep = inImage[List, List[String], String]

    trait A {

      val something: Int = 1
    }

    def doSomething[O, LO](lo: LO)(implicit conv: Apply[List]#to[O]#is[LO]): O = {

      import conv._

      lo.head
    }

    val buh:String = doSomething(uh)

    val hub = doSomething(List(new A {})).something


    // functors
    trait AnyFunctor { type F[X] }

    trait Functor[F0[_]] extends AnyFunctor { type F[X] = F0[X] }

    trait AnyFunctorOps[T <: AnyFunctor] {

      def map[X,Y](f: X => Y): T#F[X] => T#F[Y]
    }

    trait KleisliOps[T <: AnyFunctor] {

      def compose[X,Y,Z](f: X => T#F[Y], g: Y => T#F[Z]): X => T#F[Z]
    }

    object AnyList extends Functor[List] {}

    object DefaultListOps extends AnyFunctorOps[AnyList.type] with KleisliOps[AnyList.type] {

      def map[X,Y](f: X => Y): List[X] => List[Y] = {

        x => (x map f)
      }

      def compose[X,Y,Z](f: X => List[Y], g: Y => List[Z]): X => List[Z] = {

        x: X => ( f(x) flatMap g )
      }
    }

    trait Category {

      type Obj

      type Morph <: Morphism { type Source <: Obj; type Target <: Obj }
    }

    trait Morphism {

      type Source
      type Target
    }

    trait CFunctor[C <: Category, D <: Category] {

      type F0[X <: C#Obj] <: D#Obj

      type F1[f <: C#Morph] <: D#Morph { type Source = F0[f#Source]; type Target = F0[f#Target] }
    }

    
  }
}

```




[main/scala/errors.scala]: errors.scala.md
[main/scala/existentials.scala]: existentials.scala.md
[main/scala/refinementsAndWith.scala]: refinementsAndWith.scala.md
[main/scala/taggedTypes.scala]: taggedTypes.scala.md
[main/scala/typeMembers.scala]: typeMembers.scala.md
[test/scala/errors.scala]: ../../test/scala/errors.scala.md
