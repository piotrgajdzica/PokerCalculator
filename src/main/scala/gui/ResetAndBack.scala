package gui

import scala.swing.event.ButtonClicked
import scala.swing.{Button, GridPanel, Swing}

/**
  * Created by Kevin on 26.06.2017.
  */
class ResetAndBack(val deck: Deck, val hand: Hand, val table: Table,val calculateBoard: CalculateBoard) {

  val backButton = new Button{
    text = "Back"
    enabled = true
  }
  val resetButton = new Button{
    text = "Reset"
    enabled = true
  }

  resetButton. listenTo(resetButton)
  resetButton.reactions += {case ButtonClicked(`resetButton`) => reset()}
  backButton. listenTo(backButton)
  backButton.reactions += {case ButtonClicked(`backButton`) => back()}

  def reset(): Unit ={
    calculateBoard.resultLabel.text = ""
    table.flopButton.enabled = false
    table.riverButton.enabled = false
    table.turnButton.enabled = false
    table.activeFlop(0) = false
    table.activeFlop(1) = false
    table.activeFlop(2) = false
    table.activeRiver = false
    table.activeTurn = false
    table.riverReady = false
    table.turnReady = false
    table.flopReady = false
    for(i <- 0 until 5)
      table.card(i) = -1

    calculateBoard.countButton.enabled = false

    for(i <- 0 until 5) {
      table.tableLabels(i).icon = table.unactiveCard
    }
    hand.handLabels(0).icon = hand.unactiveCard
    hand.handLabels(1).icon = hand.unactiveCard

    hand.handButton.enabled = true
    hand.active(0) = false
    hand.active(1) = false
    hand.handReady = false
    hand.card(0) = -1
    hand.card(1) = -1

    deck.emptyUsedCards()
    deck.disactivateAll()

  }

  def back(): Unit ={
    calculateBoard.resultLabel.text = ""

    if(table.riverReady || table.activeRiver){
      table.riverReady = false
      table.riverButton.enabled = true
      table.activeRiver = false

      table.tableLabels(4).icon = table.unactiveCard

      if(table.card(4) != -1){
        deck.removeUsedCard(table.card(4))
        table.card(4) = -1
      }

      calculateBoard.countButton.enabled = true
      deck.disactivateAll()

    }
    else if(table.turnReady || table.activeTurn){
      table.turnReady = false
      table.riverButton.enabled = false
      table.turnButton.enabled = true
      table.activeTurn = false

      table.tableLabels(3).icon = table.unactiveCard

      if(table.card(3) != -1){
        deck.removeUsedCard(table.card(3))
        table.card(3) = -1
      }

      calculateBoard.countButton.enabled = true
      deck.disactivateAll()

    }
    else if(table.flopReady || table.activeFlop(2)){
      table.flopReady = false
      table.flopButton.enabled = true
      table.turnButton.enabled = false

      for(i <- 0 until 3) {
        if(table.card(i) != -1) {
          deck.removeUsedCard(table.card(i))
          table.card(i) = -1
        }
        table.activeFlop(i) = false
        table.tableLabels(i).icon = table.unactiveCard
      }

      deck.disactivateAll()
      calculateBoard.countButton.enabled = true
    }
    else if(hand.handReady || hand.active(1)){
      hand.handReady = false
      hand.handButton.enabled = true
      table.flopButton.enabled = false

      for(i <- 0 until 2) {
        if(hand.card(i) != -1 ) {
          deck.removeUsedCard(hand.card(i))
          hand.card(i) = -1
        }
        hand.active(i) = false
        hand.handLabels(i).icon = hand.unactiveCard
      }

      calculateBoard.countButton.enabled = false
      deck.disactivateAll()

    }

  }

  val resetAndBackPanel = new GridPanel(1,2){

    contents.append(resetButton,backButton)
    border = Swing.EmptyBorder(10, 10, 10, 10)
  }


}
