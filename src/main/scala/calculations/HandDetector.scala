package calculations

import calculations.Rank.Rank


class HandDetector(val cards: List[Card]) {
  def detect(): Rank = {

    if(detectFlush) {
      if(detectPoker)
        Rank.POKER
      else Rank.FLUSH
    }
    else if(detectStraight) Rank.STRAIGHT

    else if(detectPair){

      if(detectTriple) {

        if(detectFourKind)
          Rank.FOURKIND
        else if (detectFullHouse)
          Rank.FULLHOUSE
        else Rank.TRIPLE

      }
      else if(detectDoublePair)
        Rank.DOUBLEPAIR
      else Rank.PAIR
    }
    else Rank.NONE
  }


  def detectPair: Boolean = {
    for (i <- 0 until cards.size - 1)
      if(cards(i).number == cards(i + 1).number)
        return true
    false
  }


  def detectTriple: Boolean = {
    for (i <- 0 until cards.size - 2)
      if(cards(i).number == cards(i + 1).number && cards(i).number == cards(i + 2).number)
        return true
    false
  }


  def detectFourKind: Boolean = {
    for (i <- 0 until cards.size - 3)
      if(cards(i).number == cards(i + 1).number && cards(i).number == cards(i + 2).number && cards(i).number == cards(i + 3).number)
        return true
    false
  }

  def detectDoublePair: Boolean = {
    var counter = 0
    for (i <- 0 until cards.size - 1)
      if(cards(i).number == cards(i + 1).number)
        counter+=1

    counter == 2
  }


  def detectStraight: Boolean = {
    var lastNumber = 0
    var counter = 0

    for(card <- cards) {
      if (card.number == lastNumber + 1)
        counter += 1
      else if(card.number > lastNumber + 1) counter = 0

      lastNumber = card.number
      if(counter == 4)
        return true
    }
    false
  }


  def detectPoker: Boolean = {

    val sortedCards = cards sortBy (_.number) sortBy(_.color)

    var lastNumber = sortedCards.head.number
    var lastColor = sortedCards.head.color
    var counter = 0

    for(card <- sortedCards.tail) {
      if (card.number == lastNumber + 1 && lastColor == card.color) {
        counter += 1
        lastNumber = card.number
      }
      else if(card.number > lastNumber + 1 || lastColor != card.color) counter = 0

      if(counter == 0) {
        lastNumber = card.number
        lastColor = card.color
      }
      if(counter == 4)
        return true
    }
    false
  }

  def detectFlush: Boolean = {
    var lastColor = (cards minBy (_.color)).color
    var counter = 0

    for(card <- (cards sortBy (_.color)).tail) {
      if (lastColor == card.color)
        counter += 1
      else counter = 0

      lastColor = card.color

      if (counter == 4)
        return true
    }
    false
  }


  def detectFullHouse: Boolean = {
    var triple = 0
    var pair = 0

    for (i <- 0 until cards.size - 2)
      if (cards(i).number == cards(i + 1).number && cards(i).number == cards(i + 2).number) {
        if (triple == 0)
          triple = cards(i).number
      }
      else if (cards(i).number == cards(i + 1).number && cards(i).number != triple)
        pair = cards(i).number

    pair != 0 && triple != 0
  }

}
