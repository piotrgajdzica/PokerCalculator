import calculations.EmptyTableCalculator
import org.scalatest.FlatSpec

import scala.collection.mutable.ListBuffer

/**
  * Created by piotrek on 26.06.2017.
  */
class EmptyTableCalculatorTest  extends FlatSpec {

  "Too much players" should "cause an exception" in {
    assertThrows[IllegalArgumentException] {
      new EmptyTableCalculator(Set(), 30).winningCombinations
    }
  }


}