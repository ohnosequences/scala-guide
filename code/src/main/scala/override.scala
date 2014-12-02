package ohnosequences.scalaguide.nooverride

class X
class Y

object withOverride {

  object otherCompilationUnit {

      trait ImprovT {

      def soAbstract(x: X, y: Y): Int = 3
    }
    trait T extends ImprovT {

      def soAbstract(x: X, y: Y): Int
    }
  }

  /* In this scope we only know about `T` */
  import otherCompilationUnit.T

  abstract class C extends T {
      
    /*
    And we happily modify what was deemed to be 3 at a more abstract level!
    */
    override def soAbstract(x: X, y: Y): Int = 2
  }
}

object withoutOverride {

  object otherCompilationUnit {

      trait ImprovT {

      def soAbstract(x: X, y: Y): Int = 3
    }
    trait T extends ImprovT {

      def soAbstract(x: X, y: Y): Int
    }
  }

  /* In this scope we only know about `T` */
  import otherCompilationUnit.T

  abstract class C extends T {
      
    /*
    In this case we get a compiler error: you need override if you want to override
    */
    // def soAbstract(x: X, y: Y): Int = 2
  }

}
