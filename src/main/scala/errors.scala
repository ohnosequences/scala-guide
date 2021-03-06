package ohnosequences.scalaguide.errors

/*
### good practices

Only some tips for now

1. write primitive computations with `\/` as a return type; you need to choose, and `\/` is a more natural type.
2. `NonEmptyList[E]` can be ok for simple cases, but in general it is way better to write your own monoid instance for errors
3. For Scalaz stuff you only need a semigroup (I hate this name). I don't see the point. If building your own monoid for errors, it is much better in practice to use as a unit something like `UnknownError` which when combined with any other error should yield the other, more specific one.

*/

import scalaz._
import syntax.validation._
import syntax.apply._

trait Error[T]
case class MustHaveLengthFive(s: String) extends Error[Wonky]
case class MustBePalindromic(s: String) extends Error[Wonky]

case class Wonky protected[errors](val five: String, val palindrome: String)

object FailFastChecks {

  type ErrorsOr[A] = \/[Error[Wonky], A]

  def lengthFive(s: String): ErrorsOr[String] = if (s.size != 5) -\/(MustHaveLengthFive(s)) else \/-(s)
  def palindromic(s: String): ErrorsOr[String] = if (s != s.reverse) -\/(MustBePalindromic(s)) else \/-(s)

  // TODO: add this to Scalaz
  implicit class ErrorstoV[A](val v: ErrorsOr[A]) {

    def validationNel: ValidationNel[Error[Wonky], A] = v match {
      case -\/(e) => e.failureNel
      case \/-(a) => a.success
    }
  }
}

/* Two constructors for `Wonky`. See difference in the tests */
object AccumulativeWonky {
  import FailFastChecks._ 
    
  def apply(fiveChars: String, palindrome: String): ValidationNel[Error[Wonky], Wonky] =
    (lengthFive(fiveChars).validationNel |@| 
     palindromic(palindrome).validationNel
    )(Wonky.apply _)
}

object FailFastWonky {
  import FailFastChecks._ 
    
  def apply(fiveChars: String, palindrome: String): Validation[Error[Wonky], Wonky] = 
    (lengthFive(fiveChars) |@|
     palindromic(palindrome)
    )(Wonky.apply _).validation 
}
