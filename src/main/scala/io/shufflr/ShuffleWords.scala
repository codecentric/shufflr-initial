/*
 * Copyright 2016 codecentric AG
 */

package io.shufflr

import scala.util.Random

object ShuffleWords {

  def shuffleWord(word: String): String =
    if (word.length < 4)
      word
    else
      word.head +
      Random.shuffle(word.substring(1, word.length - 1): Seq[Char]).mkString +
      word.last
}
