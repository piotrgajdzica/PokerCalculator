package calculations


import calculations.Result.Result

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * Created by piotrek on 26.06.2017.
  */
class EmptyTableCalculator(val hand: Set[Card], val opponentNumber: Int) extends Calculator {

  val remainingSet: Set[Card] = Card.fullSet.filterNot(hand.contains(_))

  override def winningCombinations: (Double, Double, Double) = {

    var win = 0
    var draw = 0
    val repeatNumber = 100000.0


    for(i <- 1 to repeatNumber.toInt){
      val tmpSet = mutable.Set.empty[Card]
      tmpSet ++= remainingSet

      val table = getRandomSet(5, tmpSet)
      val hands = fillList(opponentNumber, getRandomSet(2, tmpSet))

      val comp = compareHands(hands, table)

      if(comp == Result.WIN)
        win += 1
      else if(comp == Result.DRAW)
        draw += 1

//      println(tmpSet.size)
    }


    (win / repeatNumber, draw / repeatNumber, (repeatNumber - win - draw) / repeatNumber)
  }

  def compareHands(otherHands: List[mutable.Set[Card]], table: mutable.Set[Card]): Result ={

    val myRank = new HandDetector((hand ++ table).toList).detect()
    val handComparator = new HandComparator()
    var draw = false

    for(otherHand <- otherHands){
      val otherRank = new HandDetector((otherHand ++ table).toList).detect()

      val comp = handComparator(hand.toList, otherHand.toList, table.toList, myRank, otherRank)

      if(comp == Result.LOSS) return Result.LOSS
      if(comp == Result.DRAW) draw = true
    }
    if(draw) Result.DRAW
    else Result.WIN
  }

  def fillList(number: Int, func : => mutable.Set[Card]): List[mutable.Set[Card]] ={
    val table = new ListBuffer[mutable.Set[Card]]()
    for(i <- 1 to number)
      table += func

    table.toList
  }


  def getRandomSet(number: Int, availableCards: mutable.Set[Card]): mutable.Set[Card] ={
    var counter = 0
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
