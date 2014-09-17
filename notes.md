
Right now some random stuff on different topics with linked issues.

## tagged types and value classes

- https://groups.google.com/forum/#!topic/shapeless-dev/_UDrpQEV22w
- https://stackoverflow.com/questions/6795623/why-do-these-type-arguments-not-conform-to-a-type-refinement
- https://stackoverflow.com/questions/6631155/how-to-specialize-on-a-type-projection-in-scala
- https://stackoverflow.com/questions/10343244/why-doesnt-type-inference-work-here/10347307#10347307
- on value classes https://stackoverflow.com/questions/14861862/how-do-you-enrich-value-classes-without-overhead/

## type members versus type parameters

#### related issues

- [SI-8493](https://issues.scala-lang.org/browse/SI-8493) _type parameter shadows type member_

## nested traits/classes

This causes a lot of problems, and you don't need it in general. _TODO explain the general pattern_

#### related issues

- [SI-8784](https://issues.scala-lang.org/browse/SI-8784) _self anotation shadows member_
- [SI-8777](https://issues.scala-lang.org/browse/SI-8777) _some types not expressible!_
- [SI-8419](https://issues.scala-lang.org/browse/SI-8419) _some types not expressible! weird existentials stuff_
- [SI-7255](https://issues.scala-lang.org/browse/SI-7255) _type members of self bounds shadow yours_
- [SI-6522](https://issues.scala-lang.org/browse/SI-6522) _lalala required a found a_

## path-dependent types

#### related issues

- [SI-8635](https://issues.scala-lang.org/browse/SI-8635) _you cannot use type aliases_

This looks like a minor issue, but think about its implications on separate compilation.

## variance

don't use variance.

- [SI-8563](https://issues.scala-lang.org/browse/SI-8563) _unsound pattern matching over variant GADTs_
- [SI-4889](https://issues.scala-lang.org/browse/SI-4889)

## Singleton types

## value classes

- https://issues.scala-lang.org/browse/SI-6260 _cannot declare functions between value classes_

## Pattern Matching

- When matching GADTs à la sealed trait, things basically doesn't work when the type is not uniform. 
- There are also issues with variant GADTs: see [Open GADTs and Declaration-site Variance: A Problem Statement](http://lampwww.epfl.ch/~hmiller/scala2013/resources/pdfs/paper5.pdf)

Part of it is not directly related with variance though; see 7714 below

#### related issues

- [SI-8599](https://issues.scala-lang.org/browse/SI-8599)
- [SI-7714](https://issues.scala-lang.org/browse/SI-7714) _what to say?_
- [SI-5900](https://issues.scala-lang.org/browse/SI-5900) 

## Dependent method types

A lot of weird stuff here.

#### related issues

- [SI-5712](https://issues.scala-lang.org/browse/SI-5712) _not allowed in constructors for the foreseeable future_
- [SI-8564](https://issues.scala-lang.org/browse/SI-8564) _funny stuff when `def f(x: A):x.type`_
- [SI-8493](https://issues.scala-lang.org/browse/SI-8493) _use `this.type` makes the compiler forget stuff about subtypes_
- [SI-5643](https://issues.scala-lang.org/browse/SI-5643) _won't find implicits when returning path-dep type; works without it_
- [SI-7784](https://issues.scala-lang.org/browse/SI-7784) _final val crashes ref to `.type`_
- [SI-7642](https://issues.scala-lang.org/browse/SI-7642) _mutually rec path-dep types no no_ 
- [SI-7612](https://issues.scala-lang.org/browse/SI-7612) _nested `.type` and SO_
- [SI-4751](https://issues.scala-lang.org/browse/SI-4751) _the kind-of correspondence between functions amd methods breaks if you use dep method types_

## existentials

Just don't do it.

#### related issues

- [SI-8544](https://issues.scala-lang.org/browse/SI-8544)
- [SI-8419](https://issues.scala-lang.org/browse/SI-8419) _some types not expressible! weird existentials stuff_
- [SI-7730](https://issues.scala-lang.org/browse/SI-7730) _the scope for existentials is just broken_
- [SI-8267](https://issues.scala-lang.org/browse/SI-8267) _implicits and stuff fail, most likely scope_
- [SI-8252](https://issues.scala-lang.org/browse/SI-8252) _nested existentials in the form `F[_]` oh no_
- [SI-8217](https://issues.scala-lang.org/browse/SI-8217) _undefined type members allowed due to existentials; but just sometimes_
- [SI-4721](https://issues.scala-lang.org/browse/SI-4721) _kinds don't play well with existentials_

## type projections

Always define them in the companion object. I think they are implemented as existentials now, which means that [SI-4867](https://issues.scala-lang.org/browse/SI-4867) could apply here.

- [SI-4867](https://issues.scala-lang.org/browse/SI-4867) _existentials work with alias, not so if naked_
- [SI-4377](https://issues.scala-lang.org/browse/SI-4377) _uh oh you cannot project and then extract_

## refinements and with and bounds

Do not use naked refinements. Create aliases in objects. But then, not always! _TODO add our current guide_

- [SI-8757](https://issues.scala-lang.org/browse/SI-8757)
- [SI-7647](https://issues.scala-lang.org/browse/SI-7647) _returning `A with B` doesn't work_
- [SI-8709](https://issues.scala-lang.org/browse/SI-8709) _type inference does not work with aliases_
- [SI-4447](https://issues.scala-lang.org/browse/SI-4447) _<: is not transitive_

One of the problems here is that `with` is commutative at the level of bounds, but it is not at with respect to type members: `(A with B)#C` is who knows what if _both_ `A` and `B` have a type member `C`; in general `(A with B)#C` will be different from `(B with A)#C`, _but_ `X <: (A with B)` iff `X <: B with A` and then in theory the same should happen for their projections.

## implicits

The return types guide inference now [SI-3346](https://issues.scala-lang.org/browse/SI-3346), so this means that in most cases where you'd need dependent method types now you don't: just add the output and let it be inferred.

- [SI-7609](https://issues.scala-lang.org/browse/SI-7609)

## imports

import always at the scope where you need something. This is not bad in itself, and it could help with implicits.

- [SI-5639](https://issues.scala-lang.org/browse/SI-5639)
- naked refinements work in general, but not for when you want something to be infer structurally; in that case you need type aliases: `type AnyA.withX[X0] = AnyA { type X = X0 }`
- *but* if every time you need that you have _a stable type_ (a trait/class whatever) which makes that refinement satisfied then they work
- they (either refinements or aliases) *don't* work as return types; so you need those traits anyway
- but you need those aliases where you want in some other scope something like `type MyA <: AnyA.with[Z]`

### guides

_WIP_

- use naked refinements for bounds
- use traits satisfying all the bounds you want for return types
