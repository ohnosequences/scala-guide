## Error handling

<!-- toc -->

> **Info** You can skip all this and look at the code; but please do mind the chasm between can and should

Intuitively, handling errors is control flow and thus it should linked/make use of colimits. A simple but effective model is

``` scala
val computation: Input => Error + Success
```

which we want, of course, to be able compose. 

### Fail fast

Normally we want to fix the type of Errors, so we get a functor

$$
C_E \colon \mathbf{Type} \to \mathbf{Type}
$$

Letting our errors vary we can see this as a lax functor

$$
C \colon (\mathbf{Type}, +) \to (\mathbf{Type} \Rightarrow \mathbf{Type}, \circ)
$$

How? the unit is just $$C_0 = Id$$ and the the lax (actually strong) structure comes from associativity: $$C_E \circ C_F \to C_{E + F}$$. Note that we are **not** saying anything about _each_ $$C_E$$, it is $$C$$ who's a lax monoidal functor. If you remember the characterization of cocartesian monoidal categories among monoidal ones, it wouldn't be a surprise to know that every $$E$$ in $$(\mathbf{Type}, +)$$ carries a unique **monoid** structure

$$
\nabla \colon E + E \to E
$$

defined as $$\nabla = [1_E,1_E]$$. Nice, so as lax monoidal functors preserve monoids there you have your error monad $$C_E$$. just for clarity, we get

- the functor map $$F(f) \colon A + E \to B + E$$ is $$f + 1_E$$
- the unit $$i\colon A \to A + E$$ is just `success(a)`
- and the multiplication $$\mu \colon (A + E) + E \to A + E$$ is just `A` or else any `E`

Composition in the kleisli category gives you what is normally called "fail-fast" semantics; given

- $$f \colon A \to E + B$$
- $$g \colon B \to E + C$$

Its composition $$g\circ f$$ is $$\mu T(g) f = \mu (g + 1_E) f$$ which if you follow the arrows means: evaluate $$f$$, if it fails return $$E$$; if not evaluate $$g$$ on the result of $$f$$. As expected, short-circuiting is implemented in terms of colimit derived operations.

### Accumulate

Again let's fix $$E$$. Compared with the "fail-fast" case, we need to assume **two** important things:

1. $$\mathbf{Type}$$ is distributive
2. $$E$$ is a monoid

With this ingredients it's fairly easy to make $$E + \_$$ a lax monoidal functor; the lax structure map needs to go

$$
\alpha \colon (E + A) \times (E + B) \to E + (A \times B)
$$

Making use of distributivity (could actually be lax)

$$
\alpha \colon E^2 + (E \times A) + (E \times B) + (A \times B) \to E + (A \times B)
$$

and our map is $$\alpha_{A,B} = [m_E, \pi_E, \pi_E] + 1_{A \times B}$$. The lax monoidal structure derived from the fail-fast monad would be just return $$E$$ if present anywhere, else $$(A \times B)$$.

This is `Validation[E,X]`.
