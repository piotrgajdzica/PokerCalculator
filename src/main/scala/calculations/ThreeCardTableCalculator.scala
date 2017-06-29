package calculations


class ThreeCardTableCalculator(val hand: Set[Card], val table: Set[Card]) extends Calculator {
  val remainingSet: Set[Card] = Card.fullSet.diff(hand ++ table)
  private val setSize : Double = remainingSet.size

  override def winningCombinations: (Double, Double, Double) =
    remainingSet.toList.map(card => new FourCardTableCalculator(hand, table + card).winningCombinations)
      .map(e => (e._1 / setSize, e._2 / setSize, e._3 / setSize))
      .reduce((e, acc) => (e._1 + acc._1, e._2 + acc._2, e._3 + acc._3))
}