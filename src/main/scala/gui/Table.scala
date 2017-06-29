package gui

import java.awt.Dimension
import javax.swing.ImageIcon

import scala.swing.event.ButtonClicked
import scala.swing.{Button, GridPanel, Label, Orientation, SplitPane, Swing}

/**
  * Created by Kevin on 25.06.2017.
  */
class Table (val table: Array[String], deck: Deck, hand: Hand, calculateBoard: CalculateBoard) {

  val WIDTH = 80
  val HEIGHT = 120
  val rec = new Dimension(WIDTH,HEIGHT)

  val activeCardURL: String = "images/card_back_green.png"
  val unactiveCardURL: String = "images/card_back_black.png"
  val activeCard: ImageIcon = getImage(activeCardURL, WIDTH, HEIGHT)
  val unactiveCard: ImageIcon = getImage(unactiveCardURL, WIDTH, HEIGHT)

  var activeFlop : Array[Boolean]  = Array(false, false, false)
  var activeTurn = false
  var activeRiver = false

  var flopReady = false
  var turnReady = false
  var riverReady = false

  var card: Array[Int] = Array(-1,-1,-1,-1,-1)


  val flopButton = new Button{
    text = "Flop"
    enabled = false
  }
  val turnButton = new Button{
    text = "turn"
    enabled = false
  }
  val riverButton = new Button{
    text = "River"
    enabled = false
  }

  var tableLabels = new Array[Label](5)
  for(i <- 0 until 5)
    tableLabels(i) = createNewTableLabel(activeCard, unactiveCard, i, WIDTH, HEIGHT, 10, rec)


  val flopPanel = new GridPanel(1,3){
    for(i <- 0 until 3)
      contents +=  tableLabels(i)
    border = Swing.EmptyBorder(10, 10, 10, 10)
  }
  val allFlop = new SplitPane(Orientation.Horizontal, flopButton, flopPanel)



  val turnPanel = new GridPanel(1,1){

    contents +=  tableLabels(3)
    border = Swing.EmptyBorder(10, 10, 10, 10)
  }
  val allTurn = new SplitPane(Orientation.Horizontal, turnButton, turnPanel)




  val riverPanel = new GridPanel(1,1){
    contents +=  tableLabels(4)
    border = Swing.EmptyBorder(10, 10, 10, 10)
  }
  val allRiver = new SplitPane(Orientation.Horizontal, riverButton, riverPanel)

  val almostAllTable = new SplitPane(Orientation.Vertical, allFlop, allTurn)
  val allTable = new SplitPane(Orientation.Vertical, almostAllTable, allRiver){
    oneTouchExpandable = false
  }

  def getImage(path: String, width: Int, height: Int): ImageIcon = {
    var imageIcon = new ImageIcon(getClass().getResource(path).getPath) // load the image to a imageIcon
    val image = imageIcon.getImage() // transform it
    val newimg = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH) // scale it the smooth way
    imageIcon = new ImageIcon(newimg)
    imageIcon
  }

  def createNewTableLabel(activeCard: ImageIcon, unactiveCard: ImageIcon, cardNumber: Int, WIDTH: Int, HEIGHT: Int, borderSize: Int, rec: Dimension): Label = {
    val label = new Label{



      icon = unactiveCard

      minimumSize = rec
      maximumSize = rec
      preferredSize = rec

      if(cardNumber < 3) listenTo(flopButton)
      if(cardNumber == 3)listenTo(turnButton)
      if(cardNumber == 4)listenTo(riverButton)
      for(button <- deck.cardButtons)
        listenTo(button._1)


      def activateFlop() {
        if(hand.handReady && !flopReady && !activeFlop(cardNumber)  && cardNumber < 3) {
          activeFlop(cardNumber) = true
          icon = activeCard
          deck.activateAll()
          calculateBoard.countButton.enabled = false
        }

      }
      def activateTurn() {
        if (hand.handReady && flopReady && !turnReady && !activeTurn && cardNumber == 3) {
          activeTurn = true
          deck.activateAll()
          icon = activeCard
          calculateBoard.countButton.enabled = false
        }

      }
      def activateRiver() {
        if (hand.handReady && flopReady && turnReady && !riverReady && cardNumber == 4) {
          activeRiver = true
          deck.activateAll()
          icon = activeCard
          calculateBoard.countButton.enabled = false
        }

      }


      def chosenCard(num:Int, image: String): Unit ={
        if(activeFlop(0) && card(0) == -1 && cardNumber == 0){
          icon = getImage(image, WIDTH, HEIGHT)
          deck.cardIsUsed(num)
          activeFlop(0) = false
          card(cardNumber) = num

        }
        else if(activeFlop(1) && card(0) != -1 && card(1) == -1 && cardNumber == 1 && card(0) != num) {
          icon = getImage(image, WIDTH, HEIGHT)
          deck.cardIsUsed(num)
          card(cardNumber) = num
        }
        else if(activeFlop(2) && card(0) != -1 && card(1) != -1 && card(2) == -1 && cardNumber == 2 && card(1) != num){
          icon = getImage(image, WIDTH, HEIGHT)
          deck.cardIsUsed(num)
          card(cardNumber) = num
          deck.disactivateAll()
          activeFlop(0) = false
          activeFlop(1) = false
          activeFlop(2) = false
          flopReady = true
          flopButton.enabled = false
          turnButton.enabled = true
          calculateBoard.countButton.enabled = true
        }
        else if(activeTurn && card(3) == -1 && cardNumber == 3){
          icon = getImage(image, WIDTH, HEIGHT)
          deck.cardIsUsed(num)
          card(cardNumber) = num
          deck.disactivateAll()
          activeTurn = false
          turnReady = true
          turnButton.enabled = false
          riverButton.enabled = true
          calculateBoard.countButton.enabled = true
        }
        else if(activeRiver && card(4) == -1 && cardNumber == 4){
          icon = getImage(image, WIDTH, HEIGHT)
          deck.cardIsUsed(num)
          card(cardNumber) = num
          deck.disactivateAll()
          activeRiver = false
          riverReady = true
          riverButton.enabled = false
          calculateBoard.countButton.enabled = true
        }


      }

      for(button <- deck.cardButtons)
        reactions += {case ButtonClicked(button._1) => chosenCard(button._2,button._3)}
      reactions += {case ButtonClicked(`flopButton`) => activateFlop() }
      reactions += {case ButtonClicked(`turnButton`) => activateTurn()}
      reactions += {case ButtonClicked(`riverButton`) => activateRiver()}



      border = Swing.EmptyBorder(borderSize, borderSize, borderSize, borderSize)
    }
    label
  }

}
