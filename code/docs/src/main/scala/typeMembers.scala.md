
```scala
package ohnosequences.scalaguide


trait DataSetup {

  type Memory <: AnyRef with Serializable

  def run(): Memory
}

// fails with weird error about an annotation or something (in 2.10)
object Use {

  val dataSetup = new DataSetup {

    case class Mem(ids: List[Int])

    type Memory = Mem

    def run(): Memory = {
      val ids = List(1,2,3)
      Mem(ids)
    }
  }
}

// works, by just moving `Mem` out
object Use2 {

  case class Mem(ids: List[Int])

  val dataSetup = new DataSetup {

    type Memory = Mem

    def run(): Memory = {
      val ids = List(1,2,3)
      Mem(ids)
    }
  }
}


trait Op { type Cap }

object Op {

  type CapOf[O <: Op] = O#Cap
  type WithCap[C] = Op { type Cap = C }

  // O should <: OLike[O]
  type OLike[O <: Op] = WithCap[CapOf[O]]
  // C should <: CLike[C]
  type CLike[C] = CapOf[WithCap[C]]
}

object UseOp {

  import Op._

  // def f[O <: Op] = implicitly[O <:< Op { type Cap = O#Cap }]
  // def f_aliased[O <: Op] = implicitly[O <:< OLike[O]]
  // def f0[O <: Op] = implicitly[O <:< WithCap[CapOf[O]]]
  // def f1[O <: Op] = implicitly[O <:< WithCap[O#Cap]]

  // def ugh[O <: Op] = implicitly[ O <:< OLike[O] ]

  def argh[O <: Op] = implicitly[ CapOf[O] <:< CLike[CapOf[O]] ]
  def argh_withAlias[O <: Op] = implicitly[ CapOf[O] <:< CapOf[OLike[O]] ]

  def h[C <: O#Cap, O <: Op]: Unit = {}
  def h_withBounds[C <: Op.CapOf[O], O <: Op]: Unit = {}

  def g[O <: Op]: Unit = h[O#Cap, O]
  def g_withBounds[O <: Op]: Unit = h_withBounds[O#Cap, O]
  def g0[O <: Op]: Unit = h_withBounds[CapOf[O], O]


  def l[C, O <: WithCap[C]]: Unit = {}
  def l0[O <: WithCap[C], C]: Unit = {}

  // def k_withBounds[O <: Op]: Unit = l[CapOf[O],O]
  // def k[O <: Op]: Unit = l[O#Cap, O]
  // def k0_withBounds[O <: Op]: Unit = l0[O,CapOf[O]]
  // def k0[O <: Op]: Unit = l0[O,O#Cap]

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