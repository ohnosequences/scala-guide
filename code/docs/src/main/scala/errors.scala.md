
```scala
package ohnosequences.scalaguide.errors
```


## Error handling

_You can skip all this and look at the code; but please do mind the chasm between can and should_

Intuitively, handling errors is control flow and thus it should linked/make use of colimits. A simple but effective model is

``` scala
val computation: Input => Error + Success
```

which we want, of course, to be able compose. 

### fail fast

Normally we want to fix the type of Errors, so we get a functor

$$
C_E \colon \mathbf{Type} \to \mathbf{Type}
$$

Letting our errors vary we can see this as a lax functor

$$
C \colon (\mathbf{Type}, +) \to (\mathbf{Type} \Rightarrow \mathbf{Type}, \circ)
$$

How? the unit is just $C_0 = Id$ and the the lax (actually strong) structure comes from associativity: $C_E \circ C_F \to C_{E + F}$. Note that we are **not** saying anything about _each_ $C_E$, it is $C$ who's a lax monoidal functor. If you remember the characterization of cocartesian monoidal categories among monoidal ones, it wouldn't be a surprise to know that every $E$ in $(\mathbf{Type}, +)$ carries a unique **monoid** structure

$$
\nabla \colon E + E \to E
$$

defined as $\nabla = [1_E,1_E]$. Nice, so as lax monoidal functors preserve monoids there you have your error monad $C_E$. just for clarity, we get

- the functor map $F(f) \colon A + E \to B + E$ is $f + 1_E$
- the unit $i\colon A \to A + E$ is just `success(a)`
- and the multiplication $\mu \colon (A + E) + E \to A + E$ is just `A` or else any `E`

Composition in the kleisli category gives you what is normally called "fail-fast" semantics; given

- $f \colon A \to E + B$
- $g \colon B \to E + C$

Its composition $g\circ f$ is $\mu T(g) f = \mu (g + 1_E) f$ which if you follow the arrows means: evaluate $f$, if it fails return $E$; if not evaluate $g$ on the result of $f$. As expected, short-circuiting is implemented in terms of colimit derived operations.

### accumulate

Again let's fix $E$. Compared with the "fail-fast" case, we need to assume **two** important things:

1. $\mathbf{Type}$ is distributive
2. $E$ is a monoid

With this ingredients it's fairly easy to make $E + \_$ a lax monoidal functor; the lax structure map needs to go

$$
\alpha \colon (E + A) \times (E + B) \to E + (A \times B)
$$

Making use of distributivity (could actually be lax)

$$
\alpha \colon E^2 + (E \times A) + (E \times B) + (A \times B) \to E + (A \times B)
$$

and our map is $\alpha_{A,B} = [m_E, \pi_E, \pi_E] + 1_{A \times B}$. The lax monoidal structure derived from the fail-fast monad would be just return $E$ if present anywhere, else $(A \times B)$.

This is `Validation[E,X]`.

-----

_TODO complete this_

Adapted and expanded from the [typelevel blog](http://typelevel.org/blog/2014/02/21/error-handling.html).

#### refs

Just for myself for now

- http://eed3si9n.com/learning-scalaz/Either.html
- http://www.slideshare.net/normation/nrm-scala-iocorrectlymanagingerrorsinscalav13
- https://stackoverflow.com/questions/20065853/validation-versus-disjunction
- https://stackoverflow.com/questions/21170322/scala-silently-catch-all-exceptions
- http://www.sumologic.com/blog/company/why-you-should-never-catch-throwable-in-scala
- https://stackoverflow.com/questions/12307965/method-parameters-validation-in-scala-with-for-comprehension-and-monads/12309023#12309023


### good practices

Only some tips for now

1. write primitive computations with `\/` as a return type; you need to choose, and `\/` is a more natural type.
2. `NonEmptyList[E]` can be ok for simple cases, but in general it is way better to write your own monoid instance for errors
3. For Scalaz stuff you only need a semigroup (I hate this name). I don't see the point. If building your own monoid for errors, it is much better in practice to use as a unit something like `UnknownError` which when combined with any other error should yield the other, more specific one.



```scala
import scalaz._
import syntax.validation._
import syntax.apply._

trait Error[T]
case class MustHaveLengthFive(s: String) extends Error[Wonky]
case class MustBePalindromic(s: String) extends Error[Wonky]

object checks {

  // type ErrorsOr[A] = ValidationNel[Error[Wonky], A]

  // def lengthFive(s: String): ErrorsOr[String] = if (s.size != 5) MustHaveLengthFive(s).failureNel else s.success 
  // def palindromic(s: String): ErrorsOr[String] = if (s != s.reverse) MustBePalindromic(s).failureNel else s.success 
}

case class Wonky protected[errors] (val five: String, val palindrome: String)

object ValidWonky {

  import FailFastChecks._ 
    
    def apply(a: String, b: String) = ( lengthFive(a).validationNel |@| palindromic(b).validationNel )(Wonky.apply _)
}

object FailFastChecks {

  type ErrorsOr[A] = \/[Error[Wonky], A]

  def lengthFive(s: String): ErrorsOr[String] = if (s.size != 5) -\/(MustHaveLengthFive(s)) else \/-(s)
  def palindromic(s: String): ErrorsOr[String] = if (s != s.reverse) -\/(MustBePalindromic(s)) else \/-(s)

  // TODO use Scalaz
  implicit class ErrorstoV[A](val v: ErrorsOr[A]) {

    def validationNel: ValidationNel[Error[Wonky], A] = v match {

      case -\/(e) => e.failureNel
      case \/-(a) => a.success
    }
  }
  
}

object FailFastWonky {

  import FailFastChecks._ 
    
  def apply(a: String, b: String) = ( lengthFive(a) |@| palindromic(b) )(Wonky.apply _)
}

// write tests if you want to see the difference

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