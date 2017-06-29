package calculations

import calculations.Rank.Rank
import calculations.Result.Result

/**
  * Created by piotrek on 26.06.2017.
  */
class HandComparator extends ((List[Card], List[Card], List[Card], Rank, Rank) => Result){
  override def apply(myHand : List[Card], otherHand: List[Card], table: List[Card], myRank: Rank, otherRank: Rank) : Result = {
//    return 1

    if (otherRank > myRank) {
      Result.LOSS
    }
    else if(otherRank == myRank) {
      val myMax = Math.max(myHand(0).number, myHand(1).number)
      val myMin = Math.min(myHand(0).number, myHand(1).number)
      val otherMax = Math.max(otherHand(0).number, otherHand(1).number)
      val otherMin =  Math.min(otherHand(0).number, otherHand(1).number)
      if (myRank == Rank.STRAIGHT || myRank == Rank.NONE || myRank == Rank.FLUSH || myRank == Rank.POKER){
        if(myMax == otherMax)
          if(otherMin > myMin) Result.LOSS
          else if (otherMin < myMin)Result.WIN
          else Result.DRAW
        else if(myMax > otherMax) Result.WIN
        else Result.LOSS
      }

      else {
        var myHigher = myMin
        var otherHigher = otherMin
        for (card <- table) {
          if (card.number == myHand(0).number || card.number == myHand(1).number)
            myHigher = Math.max(card.number, myHigher)
          if (card.number == otherHand(0).number || card.number == otherHand(1).number)
            otherHigher = Math.max(card.number, otherHigher)
        }
        if (otherHigher > myHigher) Result.LOSS
        else if (myHigher > otherHigher) Result.WIN
        else if(myMax == otherMax)
        if(otherMin > myMin) Result.LOSS
        else if (otherMin < myMin)Result.WIN
        else Result.DRAW
        else if(myMax > otherMax) Result.WIN
        else Result.LOSS
      }
    }
    else Result.WIN
  }
}
