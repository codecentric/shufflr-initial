/*
 * Copyright 2016 codecentric AG
 */

package io.shufflr

object AlphabeticFraming {

  final case class Frame(value: String, isAlphabetic: Boolean)

  private final val alphabet = 'A'.to('Z').to[Set]

  def buildFrames(s: String, previous: Option[Frame]): Vector[Frame] = {
    def isAlphabetic(c: Char) = alphabet.contains(c.toUpper)
    s.foldLeft(previous.toVector) {
      case (frames @ _ :+ last, c) if last.isAlphabetic ^ isAlphabetic(c) =>
        frames :+ Frame(c.toString, isAlphabetic(c))
      case (init :+ last, c) =>
        init :+ last.copy(last.value + c)
      case (_, c) =>
        Vector(Frame(c.toString, isAlphabetic(c)))
    }
  }
}
