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
  type U[T <: AnyAlg] = T#Raw Denotes T

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

    def unit: U[Alg]
    def mu(arg1: U[Alg], arg2: U[Alg]): U[Alg]
    def inverse(arg: U[Alg]): U[Alg]
  }
  trait GroupOps[G <: AnyGroup] extends AnyGroupOps { type Alg = G }

  /* Nice! now what about syntax/extensions for this? */
  trait AnyGroupSyntax extends Any with AnyDenotation {

    type Tpe <: AnyGroup
    type Value = Tpe#Raw
  }

  /* the companion object provides syntax */
  object StdGroupSyntax {

    implicit def syntax[R <: G#Raw, G <: AnyGroup](g: R Denotes G)(implicit ops: AnyGroupOps { type Alg = G }): StdGroupSyntax[G] = new StdGroupSyntax[G](g.value)
  }

  /* define your syntax here, through a value class */
  final class StdGroupSyntax[G <: AnyGroup](val value: G#Raw) extends AnyVal with AnyGroupSyntax {

    type Tpe = G
    // in theory we should get the type from the value
    final def +(other: U[G])(implicit 
      group: G,
      groupOps: GroupOps[G]
    )
    : U[G] = groupOps.mu(group := value, other)
  }

  trait AnyGroupModule { module =>

    type Alg <: AnyGroup
    type Ops <: AnyGroupOps { type Alg = module.Alg }

    implicit val ops: Ops
  }

  object IntPlusModule extends AnyGroupModule {

    type Alg = IntPlus.type
    type Ops = IntPlusOps.type

    implicit val ops = IntPlusOps
  }

  object IntPlus extends Group[Int] {

    val label = "IntPlus"
  }

  object IntPlusOps extends GroupOps[IntPlus.type] {

    final def unit: Alg := Int = IntPlus := 0
    final def mu(arg1: U[Alg], arg2: U[Alg]): U[Alg] = IntPlus := arg1.value + arg2.value
    final def inverse(arg: U[Alg]): U[Alg] = IntPlus := - arg.value
  }

  // TODO: the Field example reusing ops etc
  trait AnyField extends AnyAlg {

    type SumGroup <: AnyGroup
    type MultGroup <: AnyGroup
  }

  trait AnyFieldOps extends AnyAlgOps

}



