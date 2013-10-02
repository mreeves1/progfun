package greeter

object Scratch {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

	def sum(a: Int, b: Int): Int = {
		a + b
	}                                         //> sum: (a: Int, b: Int)Int
	
	sum(1000, 500)                            //> res0: Int = 1500

}