package gui

import scala.swing.{ComboBox, Label, Orientation, SplitPane, Swing}

/**
  * Created by Kevin on 25.06.2017.
  */
class Opponents {

  val opponentComboBox = new ComboBox(List(1,2,3,4,5,6,7,8))
  val opponentLabel = new Label {
    text = "Number of opponents: "
    border = Swing.EmptyBorder(5, 5, 5, 5)
  }
  val infoPrzeciwnik = new SplitPane(Orientation.Vertical, opponentLabel, opponentComboBox) {
    // oneTouchExpandable = false
    // dividerLocation = 150
  }

}
