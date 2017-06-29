package calculations


import calculations.Result.Result

import scala.collection.mutable
import scala.collection.mutable.ListBuffer


class EmptyTableCalculator(val hand: Set[Card], val opponentNumber: Int) extends Calculator {

  val remainingSet: Set[Card] = Card.fullSet.diff(hand)

  override def winningCombinations: (Double, Double, Double) = {

    var win = 0
    var draw = 0
    val repeatNumber = 100000.0


    for(_ <- 1 to repeatNumber.toInt){
      val tmpSet = mutable.Set.empty[Card]
      tmpSet ++= remainingSet

      compareHands(fillList(opponentNumber, getRandomSet(2, tmpSet)), getRandomSet(5, tmpSet)) match{
        case Result.WIN => win += 1
        case Result.DRAW => draw += 1
        case _ =>
      }
    }

    (win / repeatNumber, draw / repeatNumber, (repeatNumber - win - draw) / repeatNumber)
  }

  def compareHands(otherHands: List[mutable.Set[Card]], table: mutable.Set[Card]): Result ={

    val myRank = new HandDetector((hand ++ table).toList).detect()
    val handComparator = new HandComparator()
    var result = Result.WIN

    for(otherHand <- otherHands){
      val otherRank = new HandDetector((otherHand ++ table).toList).detect()

      handComparator(hand.toList, otherHand.toList, table.toList, myRank, otherRank) match{
        case Result.LOSS => return Result.LOSS
        case Result.DRAW => result = Result.DRAW
        case _ =>
      }
    }
    result
  }

  def fillList(number: Int, func : => mutable.Set[Card]): List[mutable.Set[Card]] ={
    val table = new ListBuffer[mutable.Set[Card]]()
    for(_ <- 1 to number)
      table += func

    table.toList
  }


  def getRandomSet(number: Int, availableCards: mutable.Set[Card]): mutable.Set[Card] ={
    var table = mutable.Set.empty[Card]

    while(table.size < number) {
      val card = randomCard(availableCards)
      table += card
      availableCards -= card
    }
    table
  }


  def randomCard(availableCards: mutable.Set[Card]): Card = {

    val n = util.Random.nextInt(availableCards.size)
    availableCards.iterator.drop(n).next
  }

}
