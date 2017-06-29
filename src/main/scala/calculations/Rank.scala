package calculations

/**
  * Created by piotrek on 25.06.2017.
  */
object Rank extends Enumeration {
  type Rank = Value
  val  NONE, PAIR, DOUBLEPAIR, TRIPLE, STRAIGHT, FLUSH, FULLHOUSE, FOURKIND, POKER = Value
}
