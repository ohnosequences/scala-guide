package ohnosequences.scalaguide.nooverride

trait T[X,Y] {
  def soAbstract(x: X, y: Y)
}

abstract class C[X,Y] extends T[X,Y] {
  //I think that I implement soAbsract
  def soAbstract(y: Y, x: X) {
    //but I do not
  }
}