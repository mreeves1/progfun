package funsets

object Main extends App {
  import FunSets._
  println("contains(singletonSet(1), 1) " +contains(singletonSet(1), 1))
  
  // test forall
  // remember that within our function we are constrained to +/- 1000 
  def pred1(x:Int): Boolean = (x >= (-1 * 1000) && x <= 1000)
  def pred2(x:Int): Boolean = (x > 2)
  def pred3(x:Int): Boolean = (x >= (-1 * 1001) && x <= 1001)
  def pred4(x:Int): Boolean = (x == 16)
  // println("test x = +/- 1000 not included in x > 2, should be false: "+forall(pred1, pred2))
  // println("test x = +/- 1000 included in x = +/- 1001, should be true: "+forall(pred1, pred3))
  // println("test x = +/- 1000 not included in x = 16, should be false: "+forall(pred1, pred4))
  
  
  
}
