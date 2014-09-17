package ohnosequences.scalaguide.projections

object types {

  trait C

  trait A {

    type B <: C
    val b: B
  }

  trait UsesA {

    type MyA <: A
  }

}

object demo {

  import types._

  import scala.language.existentials

  /*
    # Projections vs existentials

    - https://groups.google.com/forum/#!msg/scala-internals/lJ47m3j8lGc/JCbfrTx8_UcJ
    - https://groups.google.com/forum/#!msg/scala-internals/lJ47m3j8lGc/JCbfrTx8_UcJ

  */

  /*
    According to the spec, `A#B` should be a supertype of the existential `a.B forSome { val a: A }`. This works nicely:
  */
  val exSubProj = implicitly[ (a.B forSome { val a: A }) <:< A#B ]

  /*
    This should **not** compile, with error

    ``` scala
      Cannot prove that ohnosequences.scalaguide.projections.types.A#B <:< ohnosequences.scalaguide.projections.types.A#B.
    ```
  */
  // val projSubEx                 = implicitly[ A#B <:< a.B forSome { val a: A } ]
  // val projSubExWithDot          = implicitly[ A#B <:< a.type#B forSome { val a: A } ]
  // val projSubExSpecStyle        = implicitly[ A#B <:< x_type#B forSome { type x_type <: A with Singleton } ]

  /*
    and then this works!!
  */
  val projSubExT                = implicitly[ A#B <:< A0#B forSome { type A0 <: A } ]

  /*
    most likely due to the crappy implementation of existentials scope. Let's introduce an alias:
  */
  type Existential_A_hash_B = a.B forSome { val a: A }

  /*
    Nope. Doesn't work:
  */
  // val projSubExWithAlias = implicitly[ A#B <:< Existential_A_hash_B ]


  trait AbstractBounds {

    type MyA <: A
    val a: MyA

    type MyB <: A#B
  }

  trait AbstractBoundsImpl extends AbstractBounds {

    type MyB = MyA#B
    val b: MyB = a.b
  }

  trait HasUsesAandB {

    type MyUsesA <: UsesA
    val usesA: MyUsesA

    type MyB <: C
  } 

  trait Impl extends HasUsesAandB {

    type MyB = MyUsesA#MyA#B
  }


}


