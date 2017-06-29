package calculations


object Rank extends Enumeration {
  type Rank = Value
  val  NONE, PAIR, DOUBLEPAIR, TRIPLE, STRAIGHT, FLUSH, FULLHOUSE, FOURKIND, POKER = Value
}
