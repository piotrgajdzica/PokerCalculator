package calculations

/**
  * Created by piotrek on 25.06.2017.
  */
class FullTableCalculator(val hand: Set[Card], val table: Set[Card]) extends Calculator {
  val remainingSet: Set[Card] = Card.fullSet.filterNot((hand ++ table).contains(_))
  private val remainingList: List[Card] = remainingSet.toList
  private val tableList = table.toList
  private val handList = hand.toList
  private val totalCombinations = remainingSet.size * (remainingSet.size - 1) / 2 // 990

  override def winningCombinations: (Double, Double, Double) = {

    var win = 0
    var draw = 0
    val myRank = new HandDetector((hand ++ table).toList.sortBy(_.number)).detect()
    val handComparator = new HandComparator()

    for (i <- remainingList.indices)
      for (j <- i + 1 until remainingList.length) {

        val comp = handComparator(handList, remainingList(i) :: remainingList(j) :: Nil, tableList, myRank,
          new HandDetector((remainingList(i) :: remainingList(j) :: tableList).sortBy(_.number)).detect())

        if (comp == Result.WIN)
          win += 1
        else if (comp == Result.DRAW)
          draw += 1
      }

    (win * 1.0 / totalCombinations, draw * 1.0 / totalCombinations, (totalCombinations - win - draw) * 1.0 / totalCombinations)
  }
}

