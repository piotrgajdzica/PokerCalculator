package gui

import java.awt.Dimension
import javax.swing.ImageIcon

import scala.collection.mutable.ListBuffer
import scala.swing.{Button, GridPanel, Swing}

/**
  * Created by Kevin on 25.06.2017.
  */
class Deck (val deck: Array[(String, calculations.Color.Value, Int)]){

  val WIDTH = 65
  val HEIGHT = 90
  val rec = new Dimension(WIDTH,HEIGHT)
  var i = 0
  var cardButtons = new Array[(Button,Int,String)](52)
  var usedCards = new ListBuffer[Button]()


  for ((imageURL, _, _) <- deck){
    //println(imageURL + " " +   color + " " + value)
    cardButtons(i) = (createNewDeckButton(imageURL, WIDTH, HEIGHT, 10, rec),i,imageURL)
    i += 1
  }
  val deckPanel = new GridPanel(4,13){
    for(j <- 0 until 52) contents += cardButtons(j)._1

    border = Swing.EmptyBorder(10, 10, 10, 10)
  }

  def changeSizeOfCard(path: String, width: Int, height: Int): ImageIcon = {
    var imageIcon = new ImageIcon(getClass().getResource(path).getPath) // load the image to a imageIcon
    val image = imageIcon.getImage() // transform it
    val  newimg = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH) // scale it the smooth way
    imageIcon = new ImageIcon(newimg)
    return imageIcon
  }

  def createNewDeckButton(imageURL: String, WIDTH: Int, HEIGHT: Int, borderSize: Int, rec: Dimension): Button = {
    val button = new Button{
      val myImage: ImageIcon = changeSizeOfCard(imageURL, WIDTH, HEIGHT)
      icon = myImage

      minimumSize = rec
      maximumSize = rec
      preferredSize = rec
      enabled = false

      val myValue = 1
      border = Swing.EmptyBorder(borderSize, borderSize, borderSize, borderSize)
    }
    return button
  }


  def cardIsUsed(num: Int) = {
    usedCards += cardButtons(num)._1
    cardButtons(num)._1.enabled = false

  }

  def activateAll(): Unit ={
    for(i <- 0 until 52)
      cardButtons(i)._1.enabled = true
    for(card <- usedCards)
      card.enabled = false
  }

  def disactivateAll(): Unit ={
    for(i <- 0 until 52)
      cardButtons(i)._1.enabled = false
  }

  def emptyUsedCards(): Unit ={
    usedCards.clear()
  }

  def removeUsedCard(num: Int): Unit = {
    usedCards -= cardButtons(num)._1
  }

}
