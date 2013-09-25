package recfun
import common._
import scala.collection.mutable

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   *
   * Pascal's triangle looks like this:
   *  1
   *  11
   *  121
   *  1331
   *  14641
   *  
   */
  def pascal(c: Int, r: Int): Int = {
    if (c > r) 
      throw new Exception("Column out of bounds!")
    else if (c == 0) 
      1  
    else if (c == r)  
      1
    else 
      pascal(c-1, r-1) + pascal(c, r-1)
  }
  
  def pascalCrap(c: Int, r: Int): Int = {
    def findNextRowValues(previousRow: List[Int], finalRow: Int): List[Int] = {
      val nextRow = List()
      for (x <- 0 to previousRow.length + 1) {
        println("x is "+x)
        if (x == 0) {
          println("figuring out col "+(x+1)); 
          nextRow :+ 1
        }
        else if (x == previousRow.length) {
          println("figuring out col "+(x+1));
          // val nextRow: scala.mutable.Seq = nextRow :+ 1
          nextRow :+ 1
        }
        else {
          println("figuring out col "+(x+1)); ; 
          nextRow :+ (previousRow.apply(x-1) + previousRow.apply(x))
        }
      }
      // take advantage that length of row corresponds with row number
      if (nextRow.length == finalRow+1) {
        println("returning nextRow. It's "+ nextRow)
        return nextRow
      } else {
        println("nextRow is "+ nextRow)
        findNextRowValues(nextRow, finalRow)
      }
    }
    println("figuring out row "+(r+1));   
    if (r == 0) { // first row is simply 1
      if (c > 0) {
        // TODO: Throw error
      }
      return 1;
    }
    else {
      val finalRow = findNextRowValues(List(1),r);
      println("finalRow is:"+finalRow)
      
      
      return finalRow.apply(c);
    }
  }

  /**
   * Exercise 2
   */
  //def balance(chars: List[Char]): Boolean = ???

  /**
   * Exercise 3
   */
  //def countChange(money: Int, coins: List[Int]): Int = ???
}
