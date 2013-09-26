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
    
    println()
    println("Balance Parens")
    println("(just an) example 1 "+ balance("(just an) example 1".toList))
    println("just an) example 2 "+ balance("just an) example 2".toList))
    println("(just an example 3 "+ balance("(just an example 3".toList))
    println("just an example 4 "+ balance("just an example 4".toList))
    println(")just an( example 5 "+ balance(")just an( example 5".toList))
  
    
    println()
    println("Count Change")
    println("countChange(10, List(1,2,3)) "+ countChange(10, List(1,2,3)))
    println("countChange(2, List(1,2)) "+ countChange(2, List(1,2)))
    println("countChange(4, List(1,2)) "+ countChange(4, List(1,2)))
    println("countChange(1, List(2)) "+ countChange(1, List(2)))
    println("countChange(0, List(1,2,3)) "+ countChange(0, List(1,2,3)))
    println("countChange(4, List()) "+ countChange(4, List()))
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
  
  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    
    def countParens(lCnt: Int, rCnt: Int, charsTail: List[Char]): Boolean = {
      if (charsTail.isEmpty) {
        if (lCnt == rCnt)
          // Unknown case: If we have 0 parens is that still balanced?
          // if (lCnt == 0 && rCnt == 0) // yes, "&& rCnt == 0" is redundant
          //  false
          true   
        else
          false
      } else {
        if (rCnt > lCnt) // unbalanced case
          false
        else if (charsTail.head.toString == "(") 
          countParens(lCnt + 1, rCnt, charsTail.tail)
        else if (charsTail.head.toString == ")") 
          countParens(lCnt, rCnt + 1, charsTail.tail)
        else 
          countParens(lCnt, rCnt, charsTail.tail)
      }
    }
    countParens(0, 0, chars)
  }
 
  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    def countChangeAccum(money: Int, coins: List[Int], total: Int): Int = {
	    if (money == 0) // we ran out of money
	      total+1
	    else if (!coins.isEmpty && money > 0)
	      countChangeAccum(money, coins.tail, total) + countChangeAccum(money - coins.head, coins, total)
	    else
	      0
    }
    countChangeAccum(money, coins, 0)
  }
}
