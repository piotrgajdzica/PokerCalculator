/**
  * Created by piotrek on 25.06.2017.
  */
import calculations.{Card, Color, FullTableCalculator}
import org.scalatest._

import scala.collection.mutable.ListBuffer

class FullTableCalculatorTest  extends FlatSpec {

  "The number of remaining cards" should "be lower than 52" in {
    assert(new FullTableCalculator(Set(Card(3, Color.DIAMONDS), Card(3, Color.SPADES)), Set(Card(3, Color.HEARTS))).remainingSet.size == 49)
  }

  "Weak hand" should "have zero winning combinations" in {
    val table = new ListBuffer[Card]()
    table += Card(12, Color.HEARTS)
    table += Card(11, Color.HEARTS)
    table += Card(4, Color.CLUBS)
    table += Card(7, Color.DIAMONDS)
    table += Card(8, Color.DIAMONDS)

    val hand = new ListBuffer[Card]()
    hand += Card(2, Color.DIAMONDS)
    hand += Card(3, Color.DIAMONDS)

    assert(new FullTableCalculator(hand.toSet, table.toSet).winningCombinations._1 == 0.0)
  }

  "Strong hand" should "have all winning combinations" in {
    val table = new ListBuffer[Card]()
    table += Card(12, Color.HEARTS)
    table += Card(11, Color.HEARTS)
    table += Card(6, Color.DIAMONDS)
    table += Card(7, Color.DIAMONDS)
    table += Card(8, Color.DIAMONDS)

    val hand = new ListBuffer[Card]()
    hand += Card(9, Color.DIAMONDS)
    hand += Card(10, Color.DIAMONDS)

    assert(new FullTableCalculator(hand.toSet, table.toSet).winningCombinations._1 == 1.0)
  }
}