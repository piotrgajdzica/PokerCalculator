package gui

import java.awt.Dimension
import javax.swing.ImageIcon

import scala.swing.event.ButtonClicked
import scala.swing.{Button, GridPanel, Label, Orientation, SplitPane, Swing}

/**
  * Created by Kevin on 25.06.2017.
  */
class Hand(val hand: Array[String], val deck: Deck, calculateBoard: CalculateBoard) {

  val WIDTH = 80
  val HEIGHT = 120

  var active: Array[Boolean] = Array(false,false)
  var card : Array[Int] = Array(-1, -1)

  var handReady = false

  var flopButton: Button = new Button


  val activeCardURL: String = "../card_back_green.png"
  val unactiveCardURL: String = "../card_back_black.png"
  val activeCard: ImageIcon = getImage(activeCardURL, WIDTH, HEIGHT)
  val unactiveCard: ImageIcon = getImage(unactiveCardURL, WIDTH, HEIGHT)

  val rec = new Dimension(WIDTH, HEIGHT)
  val handButton = new Button {
    text = "Hand"
  }
  var handLabels = new Array[Label](2)
  handLabels(0) = createNewHandLabel(activeCard, unactiveCard,0, WIDTH, HEIGHT, 10, rec)
  handLabels(1) = createNewHandLabel(activeCard, unactiveCard,1, WIDTH, HEIGHT, 10, rec)



  val handPanel = new GridPanel(1,2){
    contents.append(handLabels(0), handLabels(1))
    border = Swing.EmptyBorder(10, 10, 10, 10)
    listenTo()
  }

  val allHand = new SplitPane(Orientation.Horizontal, handButton, handPanel)






  def getImage(path: String, width: Int, height: Int): ImageIcon = {
    var imageIcon = new ImageIcon(getClass.getResource(path).getPath); // load the image to a imageIcon
    var image = imageIcon.getImage; // transform it
    var newimg = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
    imageIcon = new ImageIcon(newimg)
    imageIcon
  }

  def createNewHandLabel(activeCard: ImageIcon, unactiveCard: ImageIcon, cardNumber: Int,  WIDTH: Int, HEIGHT: Int, borderSize: Int, rec: Dimension): Label = {
    val label = new Label{

      icon = unactiveCard

      minimumSize = rec
      maximumSize = rec
      preferredSize = rec
      border = Swing.EmptyBorder(borderSize, borderSize, borderSize, borderSize)

      listenTo(handButton)
      for(button <- deck.cardButtons)
        listenTo(button._1)



      def activate() {
        if(!handReady && cardNumber==0 ){
          icon = activeCard
          active(0) = true
          deck.activateAll()
        }
        else if(!handReady && cardNumber==1 ){
          icon = activeCard
          active(1) = true
          deck.activateAll()
        }
      }

      def chosenCard(num:Int, image: String): Unit ={
        if(active(0) && card(0) == -1 && cardNumber == 0 ){
          icon = getImage(image, WIDTH, HEIGHT)
          deck.cardIsUsed(num)
          card(0) = num

        }
        else if(active(1) && card(0) != -1 && card(1) == -1 && cardNumber == 1 && card(0) != num){
          icon = getImage(image, WIDTH, HEIGHT)
          deck.cardIsUsed(num)
          card(1) = num
          deck.disactivateAll()
          active(0) = false
          active(1) = false
          handReady = true
          handButton.enabled = false
          flopButton.enabled = true
          calculateBoard.policzButton.enabled = true
        }


      }

      for(button <- deck.cardButtons)
        reactions += {case ButtonClicked(button._1) => chosenCard(button._2,button._3)}
      reactions += { case ButtonClicked(`handButton`) => activate()}


    }
    label
  }


}
