package ohnosequences.scalaguide.functors

object functors {

  import ohnosequences.cosas.types._

  type V[X <: AnyType] = ValueOf[X]

  trait AnyTypeFunction {

    type of[X] <: AnyType
  }

  trait AnyFunctorSignature {

    type TF <: AnyTypeFunction
    type F[X] = V[TF#of[X]]

    def map[X, Y](f: X => Y): F[X] => F[Y]
  }

  /*
  Examples of type functions
  */
  type Id[X] = Wrap[X]
  type SList[X] = Wrap[List[X]]

  object slist extends AnyTypeFunction { type of[X] = SList[X] }
  object id extends AnyTypeFunction { type of[X] = Id[X] }

  /*
  `Id` as a functor
  */
  object idFunctor extends AnyFunctorSignature {

    type TF = id.type

    final def map[X, Y](f: X => Y): F[X] => F[Y] = {

      x => new F[Y]( f(x.value) )
    }
  }

  trait AnyMonadSignature {

    type F <: AnyTypeFunction

    type M[X] = V[F#of[X]]

    def flatMap[X,Y](f: X => M[Y]): M[X] => M[Y]

    def flatten[X](ffx: M[M[X]]): M[X]
  }

  object listFunctor extends AnyFunctorSignature {

    type TF = slist.type

    final def map[X, Y](f: X => Y): F[X] => F[Y] = {

      x => new F[Y](
        (x: List[X]) map f
      )

    }
  }

  object listMonad extends AnyMonadSignature {

    type F = slist.type

    final def flatMap[X,Y](f: X => M[Y]): M[X] => M[Y] = {

        x => new M[Y] (
          (x: List[X]) flatMap { x => (f(x): List[Y]) }
        )
      }

    final def flatten[X](llx: M[M[X]]): M[X] = { val f = flatMap { x:M[X] => x }; f(llx) }
  }
}