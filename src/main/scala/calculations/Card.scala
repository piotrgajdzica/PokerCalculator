package calculations

import calculations.Color.Color

case class Card(number: Int, color: Color) {}

object Card{
  def fullSet: Set[Card] = {
    for(color <- Color.values; number <- 2 to 14)
        yield Card(number, color)
  }
}