package patmat

import patmat.Huffman._

object Worksheet {
  Huffman.times(List('a', 'b', 'c', 'a'))         //> a
                                                  //| b
                                                  //| c
                                                  //| a
                                                  //| no match found for a
                                                  //| no match found for a
                                                  //| no match found for c
                                                  //| no match found for c
                                                  //| found match for a
                                                  //| res0: List[(Char, Int)] = List((a,2), (c,1), (a,1))
  Huffman.times(List('a', 'b', 'a', 'd', 'c', 'e', 'b'))
                                                  //> a
                                                  //| b
                                                  //| a
                                                  //| d
                                                  //| c
                                                  //| e
                                                  //| b
                                                  //| no match found for b
                                                  //| no match found for b
                                                  //| no match found for e
                                                  //| no match found for e
                                                  //| no match found for b
                                                  //| no match found for c
                                                  //| no match found for c
                                                  //| no match found for b
                                                  //| no match found for e
                                                  //| no match found for d
                                                  //| no match found for d
                                                  //| no match found for e
                                                  //| found match for b
                                                  //| no match found for b
                                                  //| no match found for e
                                                  //| no match found for d
                                                  //| no match found for b
                                                  //| res1: List[(Char, Int)] = List((b,1), (d,1), (e,1), (b,2), (a,1))
  
}