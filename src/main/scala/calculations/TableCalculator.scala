package calculations

/**
  * Created by piotrek on 25.06.2017.
  */
class TableCalculator extends ((Set[Card], Set[Card], Int) => (Double, Double, Double)) {
  override def apply(hand: Set[Card], table: Set[Card], opponentNumber: Int) : (Double, Double, Double) = {
    table.size match{
      case 5 => manyPlayersScore(new FullTableCalculator(hand, table).winningCombinations, opponentNumber)
      case 4 => manyPlayersScore(new FourCardTableCalculator(hand, table).winningCombinations, opponentNumber)
      case 3 => manyPlayersScore(new ThreeCardTableCalculator(hand, table).winningCombinations, opponentNumber)
      case 0 => new EmptyTableCalculator(hand, opponentNumber).winningCombinations
      case _ => throw new IllegalArgumentException("There cannot be" + table.size + "cards on a table")
    }
  }

  def manyPlayersScore(chances: (Double, Double, Double), opponentNumber: Int): (Double, Double, Double) ={

    val win = Math.pow(chances._1, opponentNumber)
    val draw =  Math.pow(chances._1 + chances._2, opponentNumber)

    (win, draw - win, 1 - draw)
  }


}

