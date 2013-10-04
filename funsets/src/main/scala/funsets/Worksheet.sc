package funsets

object Worksheet {
  import FunSets._
  val set1 = singletonSet(1)                      //> set1  : Int => Boolean = <function1>
  val set2 = singletonSet(2)                      //> set2  : Int => Boolean = <function1>
  val set3 = singletonSet(3)                      //> set3  : Int => Boolean = <function1>
  val set4 = union(set1,set2)                     //> set4  : Int => Boolean = <function1>
  val set5 = union(set4, set3)                    //> set5  : Int => Boolean = <function1>
  val set6 = (x: Int) => (x > -2 && x < 6)        //> set6  : Int => Boolean = <function1>
  val set7 = intersect(set5, set6)                //> set7  : Int => Boolean = <function1>
  val set8 = union(set5, set6)                    //> set8  : Int => Boolean = <function1>
  val set9 = diff(set6, set5)                     //> set9  : Int => Boolean = <function1>
  val set10 = filter(set6, set5)                  //> set10  : Int => Boolean = <function1>
  printSet(set4)                                  //> {1,2}
  printSet(set5)                                  //> {1,2,3}
  printSet(set6)                                  //> {-1,0,1,2,3,4,5}
  printSet(set7)                                  //> {1,2,3}
  printSet(set8)                                  //> {-1,0,1,2,3,4,5}
  printSet(set9)                                  //> {-1,0,4,5}
  printSet(set10)                                 //> {1,2,3}
  println(forall(set5, set6))                     //> true
  println(exists(set4, set8))                     //> true
  println(exists(set4, set8))                     //> true
  println(exists(set1, set2))                     //> false
  printSet(map(set6, (x: Int) => x*x))            //> {0,1,4,9,16,25}
  printSet(map(set10, (x: Int) => x*x))           //> {1,4,9}
  printSet(map(set10, (x: Int) => -1*x))          //> {-3,-2,-1}
  
 
}