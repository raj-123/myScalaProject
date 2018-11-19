package com.test.spark
import scala.io.Source

object test extends App {
  val filename = "/home/raj/Desktop/test_file.txt"
for (line <- Source.fromFile(filename).getLines) {
    println(line)
}
  
}