package gui

import scala.swing.event.ButtonClicked
import scala.swing.{Button, Label, Orientation, SplitPane, Swing}

/**
  * Created by Kevin on 25.06.2017.
  */
class CalculateBoard(deck: Deck, opponents: Opponents, cardSupplies: CardSupplies) {


  val hand = new Hand(cardSupplies.hand, deck, this)
  val table = new Table(cardSupplies.table, deck, hand, this)

  val countButton = new Button{
    text = "Calculate"
    border = Swing.EmptyBorder(10, 10, 10, 10)
    enabled = false

  }
  val resultLabel = new Label {

    text = ""
    border = Swing.EmptyBorder(5, 5, 5, 5)
    listenTo(countButton)
    var t = 1
    def convert() {

      text = "calculating..."
      val nrOfOpp : Int = opponents.opponentComboBox.item

      var handCards: Set[calculations.Card] = Set()
      for(i <- 0 until 2) {
        handCards += new calculations.Card(cardSupplies.deck(hand.card(i))._3, cardSupplies.deck(hand.card(i))._2)
      }

      var tableCards: Set[calculations.Card] = Set()
      for(i <- 0 until 5) {
        if(table.card(i) != -1)
        tableCards += new calculations.Card(cardSupplies.deck(table.card(i))._3,cardSupplies.deck(table.card(i))._2)
      }



      val calculate = new calculations.TableCalculator()
      val result: (Double, Double, Double) = calculate(handCards, tableCards, nrOfOpp)
      text = "Chance to win: " + (result._1 * 100).toInt + "% Chance for tie: " +
        (result._2 * 100).toInt + "%"
    }

    reactions += {
      case ButtonClicked(`countButton`)  => convert()
    }

  }

  val infoResult = new SplitPane(Orientation.Vertical, countButton, resultLabel) {
    // oneTouchExpandable = false
    // dividerLocation = 150
  }
}
