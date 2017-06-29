package gui

import scala.swing.{MainFrame, Orientation, SimpleSwingApplication, SplitPane}


object PokerCalcGui extends SimpleSwingApplication {

  val WIDTH = 80
  val HEIGHT = 120

  def top = new MainFrame {
    title = "Poker calculations.Calculator"



    val cardSupplies = new CardSupplies

    val opponents = new Opponents

    val deck = new Deck(cardSupplies.deck)
    val calculateBoard = new CalculateBoard(deck, opponents, cardSupplies)
    val hand: Hand = calculateBoard.hand
    val table: Table = calculateBoard.table
    val resetAndBack = new ResetAndBack(deck, hand, table, calculateBoard)
    hand.flopButton = table.flopButton

    val middlePanel = new SplitPane(Orientation.Vertical, opponents.infoPrzeciwnik, calculateBoard.infoResult ) {
      // oneTouchExpandable = false
      // dividerLocation = 150
    }


    val playingCardsPanel = new SplitPane(Orientation.Vertical, hand.allHand, table.allTable) {
     // oneTouchExpandable = false
     // dividerLocation = 150
    }

    val almostAllboardInfoPanel = new SplitPane(Orientation.Horizontal, playingCardsPanel, middlePanel) {
      // oneTouchExpandable = false
      // dividerLocation = 150
    }

    val boardInfoPanel = new SplitPane(Orientation.Horizontal, resetAndBack.resetAndBackPanel, almostAllboardInfoPanel )

    val allCardsPanel = new SplitPane(Orientation.Horizontal, boardInfoPanel, deck.deckPanel) {
     // oneTouchExpandable = false
     // dividerLocation = 150
    }
    contents = allCardsPanel

  }
}


