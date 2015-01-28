package ohnosequences.scalaguide.typeclasses

import ohnosequences.cosas.types._


/*
## Typeclasses

You need to be able to indentify at compile time

1. the algebra type: *this* particular monoid.
2. operations, defined abstractly over a `Raw` type
3. that the `Raw` type is being viewed as denoting a particular monoid

Given all that, if you have a value of the corresponding type `x: NatRing := Int` you will get an instance of the corresponding type class through an implicit conversion `(Int Denotes NatRing) => RingOps[NatRing, Int]`. `RingOps` is another value class, wrapping `Int` in this case.

_TODO: coercions?_
*/
object algs {

  /* This the base type that you are defining */
  trait AnyAlg extends AnyType
  /* 
  We use denotations to recognize at compile-time that a type (`T#Raw` in this case) is used to denote `T`. For example, the additive monoid for integers. We would have something like `IntPlusMonoid <: AnyAlg`, which could be denoted by `Int`s; we would have to specify then `IntPlusMonoid#Raw = Int`.
  */

  /*
  A way of thinking about ops is as the real signature for your type, abstracted over the type used to denote it.
  */
  trait AnyAlgOps extends Any {

    type Alg <: AnyAlg
  }

  /*
  ### A simple example: Groups
  */
  trait AnyGroup extends AnyAlg
  trait Group[T] extends AnyGroup { type Raw = T }
  // signature
  trait AnyGroupOps extends AnyAlgOps {

    // refine the algebra type: a group
    type Alg <: AnyGroup

    def unit: Alg#Raw Denotes Alg
    def mu(arg1: Alg#Raw Denotes Alg, arg2: Alg#Raw Denotes Alg): Alg#Raw Denotes Alg
    def inverse(arg: Alg#Raw Denotes Alg): Alg#Raw Denotes Alg
  }
  trait GroupOps[G <: AnyGroup] extends AnyGroupOps { type Alg = G }

  /* Nice! now what about syntax/extensions for this? */
  trait AnyGroupSyntax extends Any with AnyDenotation {

    type Tpe <: AnyGroup
    type Value = Tpe#Raw
  }

  /* the companion object provides syntax */
  object StdGroupSyntax {

    implicit def syntax[R <: G#Raw, G <: AnyGroup, V <% R Denotes G](g: V)(implicit ops: AnyGroupOps { type Alg = G }): StdGroupSyntax[G] = new StdGroupSyntax[G](g.value)
  }

  /* define your syntax here, through a value class */
  final class StdGroupSyntax[G <: AnyGroup](val value: G#Raw) extends AnyVal with AnyGroupSyntax {

    type Tpe = G
    // in theory we should get the type from the value
    final def plus(other: G#Raw Denotes G)(implicit 
      group: G,
      groupOps: GroupOps[G]
    )
    : G#Raw Denotes G = groupOps.mu(group := value, other)
  }

  trait AnyGroupModule { module =>

    type Alg <: AnyGroup
    type Ops <: AnyGroupOps { type Alg = module.Alg }

  }

  object IntPlusModule {

    type Alg = IntPlus.type
    type Ops = IntPlusOps.type
    implicit def addSyntax[V <% (IntPlus.type#Raw Denotes IntPlus.type)](v: V): StdGroupSyntax[IntPlus.type] = new StdGroupSyntax[IntPlus.type](v.value)
  }
  object IntPlus extends Group[Int] { 

    val label = "IntPlus" 

    implicit val intPlusOps: IntPlusOps.type = IntPlusOps
  }
  object IntPlusOps extends GroupOps[IntPlus.type] {

    final def unit: Alg := Int = IntPlus := 0
    final def mu(arg1: Alg#Raw Denotes Alg, arg2: Alg#Raw Denotes Alg): Alg#Raw Denotes Alg = IntPlus := arg1.value + arg2.value
    final def inverse(arg: Alg#Raw Denotes Alg): Alg#Raw Denotes Alg = IntPlus := - arg.value
  }

  object IntMultModule {
    type Alg = IntMult.type
    type Ops = IntMultOps.type
    implicit val ops: Ops = IntMultOps
  }
  object IntMult extends Group[Int] { val label = "IntMult" }
  object IntMultOps extends GroupOps[IntMult.type] {

    final def unit: Alg := Int = IntMult := 1
    final def mu(arg1: Alg#Raw Denotes Alg, arg2: Alg#Raw Denotes Alg): Alg#Raw Denotes Alg = IntMult := arg1.value * arg2.value
    final def inverse(arg: Alg#Raw Denotes Alg): Alg#Raw Denotes Alg = IntMult :=  1 / arg.value
  }

  /* define your syntax here, through a value class */
  final class StdMultGroupSyntax[G <: AnyGroup](val value: G#Raw) extends AnyVal with AnyGroupSyntax {

    type Tpe = G
    // in theory we should get the type from the value
    final def *(other:G#Raw Denotes G)(implicit 
      group: G,
      groupOps: GroupOps[G]
    )
    : G#Raw Denotes G = groupOps.mu(group := value, other)
  }

  object StdMultGroupSyntax {

    implicit def fromIntMult[V <% (IntMult.type#Raw Denotes IntMult.type)](v: V): StdMultGroupSyntax[IntMult.type] = new StdMultGroupSyntax(v.value)
  }






  // TODO: the Field example reusing ops etc
  trait AnyField extends AnyAlg { field =>

    type AddGroup <: AnyGroup { type Raw = field.Raw }
    val addGroup: AddGroup
    type MultGroup <: AnyGroup { type Raw = field.Raw }
    val multGroup: MultGroup

    // this is a bit weird, but works
    implicit def toAddGroup[R <% Raw Denotes field.type](f: R): Raw Denotes AddGroup = addGroup := f.value
    implicit def toMultGroup[R <% Raw Denotes field.type](f: R): Raw Denotes MultGroup = multGroup := f.value

    implicit def toMeFromAdd(ag: Raw Denotes AddGroup): Raw Denotes field.type = new Denotes(ag.value)
    implicit def toMeFromMult(mg: Raw Denotes MultGroup): Raw Denotes field.type = new Denotes(mg.value)
  }

  class Field[ 
    R,
    AG <: AnyGroup { type Raw = R },
    MG <: AnyGroup { type Raw = R }
  ]
  (val addGroup: AG, val multGroup: MG, val label: String) extends AnyField {

    type Raw = R
    type AddGroup = AG
    type MultGroup = MG
  }

  object AnyField {

    // coercions
    
  }

  object IntField extends Field[Int, IntPlus.type, IntMult.type](IntPlus, IntMult, "IntField") {

    implicit val me: IntField.type = IntField
  }




  trait AnyList extends AnyType
  case class List[L]() extends AnyList { type Raw = L; val label = "List[L]" }

  trait AnyListSignature extends Any {

    type L <: AnyList

    def fold[Z](nil: Z)(op: (L#Raw, Z) => Z): Z 
  }

  // a particular impl
  import scala.annotation.tailrec
  trait LinkedListImpl[X] extends Any with AnyListSignature {

    type L <: AnyList { type Raw = X }

    def head: X
    def tail: LinkedListImpl[X]

    @tailrec final def fold[Z](nil: Z)(op: (L#Raw, Z) => Z): Z = tail.fold( op(head, nil) )( op )
  }

  // add an object nil with a method cons that would return this nil with the right type. Say no to `Nothing`!
  final class nilOps[X] extends LinkedListImpl[X] {

    type L = List[X]

    final def tail = this
    final def head = ???
  }
  case class consOps[X](val head: X, val tail: LinkedListImpl[X]) extends LinkedListImpl[X] {

    type L = List[X]
  }
}