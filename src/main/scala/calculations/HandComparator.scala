package calculations

import calculations.Rank.Rank
import calculations.Result.Result


class HandComparator extends ((List[Card], List[Card], List[Card], Rank, Rank) => Result){
  override def apply(myHand : List[Card], otherHand: List[Card], table: List[Card], myRank: Rank, otherRank: Rank) : Result = {

    if (otherRank > myRank) {
      Result.LOSS
    }
    else if(otherRank == myRank) {
      val myMax = Math.max(myHand(0).number, myHand(1).number)
      val myMin = Math.min(myHand(0).number, myHand(1).number)
      val otherMax = Math.max(otherHand(0).number, otherHand(1).number)
      val otherMin =  Math.min(otherHand(0).number, otherHand(1).number)
      if (myRank == Rank.STRAIGHT || myRank == Rank.NONE || myRank == Rank.FLUSH || myRank == Rank.POKER){
        if(myMax > otherMax || myMax == otherMax && myMin > otherMin) Result.WIN
        else if (myMax == otherMax && myMin == otherMin) Result.DRAW
        else Result.LOSS
      }
      else {
        var myPaired = myMin
        var otherPaired = otherMin
        for (card <- table) {
          if (card.number == myHand(0).number || card.number == myHand(1).number)
            myPaired = Math.max(card.number, myPaired)
          if (card.number == otherHand(0).number || card.number == otherHand(1).number)
            otherPaired = Math.max(card.number, otherPaired)
        }
        if (otherPaired > myPaired) Result.LOSS
        else if (myPaired > otherPaired) Result.WIN
        else if(myMax > otherMax || myMax == otherMax && myMin > otherMin) Result.WIN
        else if (myMax == otherMax && myMin == otherMin) Result.DRAW
        else Result.LOSS
      }
    }
    else Result.WIN
  }
}
