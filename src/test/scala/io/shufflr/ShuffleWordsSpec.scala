/*
 * Copyright 2016 codecentric AG
 */

package io.shufflr

import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{ Matchers, WordSpec }

final class ShuffleWordsSpec extends WordSpec with Matchers with GeneratorDrivenPropertyChecks {
  import ShuffleWords._

  "shuffleWord" should {
    "keep the first and last character and somehow shuffle the others" in {
      forAll { (word: String) =>
        if (word.length < 4)
          shuffleWord(word) shouldBe word
        else {
          val shuffledWord = shuffleWord(word)
          shuffledWord.head shouldBe word.head
          shuffledWord.last shouldBe word.last
          shuffledWord.init.tail.toList.sorted shouldBe word.init.tail.toList.sorted
        }
      }
    }
  }
}
