package ohnosequences.scalaguide.test.errors

import org.scalatest.FunSuite

import ohnosequences.scalaguide.errors._
import scalaz._

class ErrorHandlingExamples extends FunSuite {

  val notPalindromic = "aaaca"
  val notFiveChars = "012345"

  test("fail-fast examples") {

    val notOk = FailFastWonky(
      fiveChars = notFiveChars,
      palindrome = notPalindromic
    )

    assert( 
      notOk == -\/(MustHaveLengthFive(notFiveChars)) 
    )

    info(s"trying to create a Wonky with an error in both params yields the first one: ${notOk}")
  }

  test("accumulate errors examples") {

    val notOk = ValidWonky(
      fiveChars = notFiveChars,
      palindrome = notPalindromic
    )

    info(s"trying to create a Wonky with an error in both params yields both errors: ${notOk}")


  } 
}

