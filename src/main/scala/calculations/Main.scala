package calculations

import scala.collection.mutable.ListBuffer

/**
  * Created by piotrek on 25.06.2017.
  */
object Main {
  var table = new ListBuffer[Card]()
  table += Card(2, Color.HEARTS)
  table += Card(6, Color.HEARTS)
  table += Card(6, Color.DIAMONDS)
//  table += Card(7, Color.DIAMONDS)
//  table += Card(8, Color.SPADES)


  def main(args: Array[String]) {


    val start = System.currentTimeMillis()
    var hand = new ListBuffer[Card]()
    hand += Card(12, Color.HEARTS)
    hand += Card(6, Color.CLUBS)

    println(new TableCalculator()(hand.toSet, table.toSet, 7))
    println(new TableCalculator()(hand.toSet, Set(), 3))

    println((System.currentTimeMillis() - start) / 1000.0)

  }
}
