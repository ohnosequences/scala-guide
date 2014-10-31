package ohnosequences.scalaguide.errors

/*
## Error handling

Adapted and expanded from the [typelevel blog](http://typelevel.org/blog/2014/02/21/error-handling.html).
*/

import scalaz._

trait Error[T]
case class MustHaveLengthFive(s: String) extends Error[Wonky]
case class MustBePalindromic(s: String) extends Error[Wonky]

final class Wonky private(val five: String, val palindrome: String)

object Wonky extends Validate[(String, String), Wonky]{

  val validate: Input => Out = input => {

    // ugly I know
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