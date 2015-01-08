package ohnosequences.scalaguide.nooverride

/*
## Say no to override

It could be convenient (maybe, I didn't need this ever) to have something like `implements` in Scala, signaling the compiler that you intend to implement a method; providing explicit signatures for all your methods (that you should do anyway) helps in that case. It also considered a good practice to make _everything_ `final` if it is *not* abstract; that way you are safe from the overriding mobs. 

And about overriding when _actually changing something_: my point is that more abstract should always win; if at a more generic level a method is supposed to do one thing, this should never change. This makes using override for signaling implements a bad idea: when at a more abstract level things are changed so that `funnyMethod` needs to return `3`, you won't get a compiler error. And this is really bad.

##### references/links

- http://stackoverflow.com/a/5643284/614394
- https://groups.google.com/forum/#!topic/scala-debate/AUytq1y58GQ
- http://eed3si9n.com/curious-case-of-putting-override-modifier
*/


/* This two types are here just for demonstration purposes. */
class X
class Y

object withOverride {

  /* Just imagine that this comes from a different compilation unit (a library you're using, whatever).*/
  object otherCompilationUnit {

    trait ImprovT {

      def notOverrideSafe(x: X, y: Y): Int = 3

      final def overrideSafe(x: X, y: Y): Int = 3
    }
    trait T extends ImprovT {

      /* I can happily override it here, even if it is already implemented.*/
      override def notOverrideSafe(x: X, y: Y): Int

      /* This won't work: */
      // override def overrideSafe(x: X, y: Y): Int = 3
      /* You can signal that you do have this method, if you need that (docs, whatever) */
      def overrideSafe(x: X, y: Y): Int
      /* But you cannot change it: */
      // def overrideSafe(x: X, y: Y): Int = 3
    }
  }

  /* In this scope we only know about `T` */
  import otherCompilationUnit.T

  abstract class C extends T {
      
    /* And we happily modify what was deemed to be 3 at a more abstract level! */
    override def notOverrideSafe(x: X, y: Y): Int = 2
    /* Reassuringly, this does not compile: */
    // override def overrideSafe(x: X, y: Y): Int = 2
  }
}