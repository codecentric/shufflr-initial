/*
 * Copyright 2016 codecentric AG
 */

package io.shufflr

import java.util.concurrent.{ Executors, ScheduledExecutorService => Scheduler }
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.SECONDS
import scala.concurrent.{ Future, Promise }
import scala.math.Pi
import scala.util.{ Failure, Success, Try }

object BackToFutures {

  def main(args: Array[String]): Unit = {
    implicit val scheduler = Executors.newSingleThreadScheduledExecutor() // Not needed for Akka which has its own `Scheduler`!
    areaDiffs(Vector(1.0, 2.0, 5.0, 10.0, 50.0))
      .andThen {
        case Success(values) => println(s"Differences: ${values.mkString(", ")}")
        case Failure(cause)  => println(s"Failure: $cause")
      }
      .andThen {
        case _ => scheduler.shutdown()
      }
  }

  def areaDiffs(sizes: Vector[Double])(implicit scheduler: Scheduler): Future[Vector[Double]] =
    // TODO Call `squareArea` and `circleArea` to get the areas for the given `sizes`
    // TODO Transform the results to calculate the differences of the corresponding arae values: square - circle
    // TODO Further transform the results into the expected return type
    ???

  private def squareArea(length: Double)(implicit scheduler: Scheduler) =
    afterOneSecond(length * length)

  private def circleArea(diameter: Double)(implicit scheduler: Scheduler) =
    afterOneSecond(diameter * diameter / 4 * Pi)

  private def afterOneSecond[A](a: => A)(implicit scheduler: Scheduler) = {
    val promise = Promise[A]()
    scheduler.schedule(() => promise.complete(Try(a)), 1, SECONDS)
    promise.future
  }
}
