
```scala
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

    assert{ notOk == Failure(MustHaveLengthFive(notFiveChars)) }

    info(s"trying to create a Wonky with an error in both params yields the first one: ${notOk}")
  }

  test("accumulate errors examples") {

    val notOk = AccumulativeWonky(
      fiveChars = notFiveChars,
      palindrome = notPalindromic
    )

    assert{ notOk == Failure(NonEmptyList(MustHaveLengthFive("012345"), MustBePalindromic("aaaca"))) }

    info(s"trying to create a Wonky with an error in both params yields both errors: ${notOk}")
  } 
}


```


------

### Index

+ src
  + main
    + scala
      + [errors.scala][main/scala/errors.scala]
      + [existentials.scala][main/scala/existentials.scala]
      + [refinementsAndWith.scala][main/scala/refinementsAndWith.scala]
      + [taggedTypes.scala][main/scala/taggedTypes.scala]
      + [typeMembers.scala][main/scala/typeMembers.scala]
  + test
    + scala
      + [errors.scala][test/scala/errors.scala]

[main/scala/errors.scala]: ../../main/scala/errors.scala.md
[main/scala/existentials.scala]: ../../main/scala/existentials.scala.md
[main/scala/refinementsAndWith.scala]: ../../main/scala/refinementsAndWith.scala.md
[main/scala/taggedTypes.scala]: ../../main/scala/taggedTypes.scala.md
[main/scala/typeMembers.scala]: ../../main/scala/typeMembers.scala.md
[test/scala/errors.scala]: errors.scala.md