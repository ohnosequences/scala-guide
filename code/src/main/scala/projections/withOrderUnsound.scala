package ohnosequences.scalaguide.projections

object WithOrderUnsound {

    trait A { type E }

    trait C extends A { final type E = Int }
    trait D extends A { final type E = String }

    trait Caution

    type Use = Any with Caution

    type EE[X <: A] = X#E
   
    def fail(): Unit = {

      val x1: EE[C] = 5
      val x2: EE[C with D] = ""

      // val x3: EE[D with C] = ""
    }

    def cd[A0 <: C with D]: A0#E = "string"

    def dc[A0 <: D with C]: A0#E = 1

    def argh[C0 <: C, D0 <: D, E0 <: C0#E with D0#E]: E0 = ???

    trait UnsoundessIsComing {

      type X <: D with C

      val uh = cd[D with C]

      val argadfad = argh[C,D,String with Int]
    }
}