package ohnosequences.scalaguide.errors

/*
## Error handling

Adapted and expanded from the [typelevel blog](http://typelevel.org/blog/2014/02/21/error-handling.html).

#### refs

Just for myself for now

- http://eed3si9n.com/learning-scalaz/Either.html
- http://www.slideshare.net/normation/nrm-scala-iocorrectlymanagingerrorsinscalav13
- https://stackoverflow.com/questions/20065853/validation-versus-disjunction
- https://stackoverflow.com/questions/21170322/scala-silently-catch-all-exceptions
- http://www.sumologic.com/blog/company/why-you-should-never-catch-throwable-in-scala
- https://stackoverflow.com/questions/12307965/method-parameters-validation-in-scala-with-for-comprehension-and-monads/12309023#12309023

*/

import scalaz._

trait Error[T]
case class MustHaveLengthFive(s: String) extends Error[Wonky]
case class MustBePalindromic(s: String) extends Error[Wonky]

final class Wonky private(val five: String, val palindrome: String)

object Wonky extends Validate[(String, String), Wonky]{

  val validate: Input => Out = input => {

    // ugly I know. See also https://issues.scala-lang.org/browse/SI-5898
    val (five: String, palindrome: String) = input

    if (five.size != 5) -\/(MustHaveLengthFive(five))
    else if (palindrome != palindrome.reverse) -\/(MustBePalindromic(palindrome))
    else \/-(new Wonky(five, palindrome))
  }
    
}

trait AnyValidate {

  type Input
  type Out
  val validate: Input => Out

  def apply(input: Input): Out
}

trait Validate[X,T] extends AnyValidate {

  type Input = X
  type Out = Error[T] \/ T

  def apply(input: X): Error[T] \/ T = validate(input)
}

object useIt {

  val w = Wonky("6", "aaa")
}