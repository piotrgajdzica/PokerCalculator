package calculations

import calculations.Color.Color

/**
  * Created by piotrek on 25.06.2017.
  */
case class Card(number: Int, color: Color) {}

object Card{
  def fullSet: Set[Card] = {
    val list = for(color <- Color.values; number <- 2 to 14)
        yield Card(number, color)
    list
  }
}