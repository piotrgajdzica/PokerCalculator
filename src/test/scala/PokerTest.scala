/**
  * Created by piotrek on 25.06.2017.
  */
import calculations.{Card, Color, HandDetector, Rank}
import org.scalatest._

import scala.collection.mutable.ListBuffer

class HandTest extends FlatSpec {

  "A hand of cards" should "evaluate those values" in {
    var cards = new ListBuffer[Card]()
    cards += Card(2, Color.CLUBS)
    cards += Card(3, Color.DIAMONDS)
    cards += Card(4, Color.CLUBS)
    cards += Card(7, Color.DIAMONDS)
    cards += Card(8, Color.CLUBS)
    cards += Card(9, Color.SPADES)
    cards += Card(10, Color.SPADES)

    assert(new HandDetector(cards.toList sortBy (_.number)).detect() == Rank.NONE)

    cards -= Card(9, Color.SPADES)
    cards += Card(10, Color.DIAMONDS)

    assert(new HandDetector(cards.toList sortBy (_.number)).detect() == Rank.PAIR)

    cards -= Card(8, Color.CLUBS)
    cards += Card(10, Color.HEARTS)

    assert(new HandDetector(cards.toList sortBy (_.number)).detect() == Rank.TRIPLE)

    cards -= Card(2, Color.CLUBS)
    cards += Card(3, Color.HEARTS)

    assert(new HandDetector(cards.toList sortBy (_.number)).detect() == Rank.FULLHOUSE)

    cards -= Card(7, Color.DIAMONDS)
    cards += Card(10, Color.CLUBS)

    assert(new HandDetector(cards.toList sortBy (_.number)).detect() == Rank.FOURKIND)
  }

  "A second hand of cards" should "evaluate those values" in {
    var cards = new ListBuffer[Card]()
    cards += Card(2, Color.HEARTS)
    cards += Card(3, Color.HEARTS)
    cards += Card(4, Color.CLUBS)
    cards += Card(7, Color.DIAMONDS)
    cards += Card(8, Color.DIAMONDS)
    cards += Card(9, Color.DIAMONDS)
    cards += Card(10, Color.DIAMONDS)

    assert(new HandDetector(cards.toList sortBy (_.number)).detect() == Rank.NONE)

    cards += Card(2, Color.HEARTS)
    cards += Card(2, Color.DIAMONDS)

    assert(new HandDetector(cards.toList sortBy (_.number)).detect() == Rank.FLUSH)

    cards += Card(2, Color.DIAMONDS)
    cards += Card(6, Color.DIAMONDS)

    assert(new HandDetector(cards.toList sortBy (_.number)).detect() == Rank.POKER)
  }
}