package ohnosequences.scalaguide.projections

object WithOrderUnsound {

    trait A { type E }

    trait C extends A { type E = Int }
    trait D extends A { type E = String }

    type EE[X <: A] = X#E
   
    def fail(): Unit = {

      val x1: EE[C] = 5
      val x2: EE[C with D] = ""

      // val x3: EE[D with C] = ""
    }

    def cd[A0 <: C with D]: A0#E = "string"

    def dc[A0 <: D with C]: A0#E = 1

    trait UnsoundessIsComing {

      type X <: D with C

      val uh = cd[D with C]
    }
}