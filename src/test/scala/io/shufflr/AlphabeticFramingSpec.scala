/*
 * Copyright 2016 codecentric AG
 */

package io.shufflr

import org.scalatest.{ Matchers, WordSpec }

final class AlphabeticFramingSpec extends WordSpec with Matchers {
  import AlphabeticFraming._

  "buildFrames" should {
    "correctly build frames" in {
      buildFrames("", None) shouldBe 'empty
      buildFrames("", Some(Frame("z", isAlphabetic = true))) shouldBe Vector(
        Frame("z", isAlphabetic = true)
      )

      buildFrames("abc", None) shouldBe Vector(Frame("abc", isAlphabetic = true))
      buildFrames("abc", Some(Frame("z", isAlphabetic = true))) shouldBe Vector(
        Frame("zabc", isAlphabetic = true)
      )
      buildFrames(" ", Some(Frame("z", isAlphabetic = true))) shouldBe Vector(
        Frame("z", isAlphabetic = true),
        Frame(" ", isAlphabetic = false)
      )

      buildFrames(" a b", Some(Frame("z", isAlphabetic = true))) shouldBe Vector(
        Frame("z", isAlphabetic = true),
        Frame(" ", isAlphabetic = false),
        Frame("a", isAlphabetic = true),
        Frame(" ", isAlphabetic = false),
        Frame("b", isAlphabetic = true)
      )

      buildFrames("a b ", Some(Frame("z", isAlphabetic = true))) shouldBe Vector(
        Frame("za", isAlphabetic = true),
        Frame(" ", isAlphabetic = false),
        Frame("b", isAlphabetic = true),
        Frame(" ", isAlphabetic = false)
      )
    }
  }
}
