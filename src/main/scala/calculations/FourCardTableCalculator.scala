package calculations


class FourCardTableCalculator(val hand: Set[Card], val table: Set[Card]) extends Calculator {
  val remainingSet: Set[Card] = Card.fullSet.diff(hand ++ table)


  override def winningCombinations: (Double, Double, Double) = {
    var resWin = 0.0
    var resDraw = 0.0


    for(card <- remainingSet) {
      val chances = new FullTableCalculator(hand, table + card).winningCombinations
      resWin += chances._1
      resDraw += chances._2
    }
    (resWin / remainingSet.size, resDraw / remainingSet.size, (remainingSet.size - resWin) / remainingSet.size)
  }
}
