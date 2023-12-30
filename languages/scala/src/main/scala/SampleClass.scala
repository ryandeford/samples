import java.time.Instant
import scala.concurrent.Future
import concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

class SampleClass {

  private def sampleMethod(): Unit = println("Sample method executed")

  private def sampleTimedExecutor(f: () => Unit, interval: Long = 1000, iterations: Long = 3): Unit = {
    if (interval < 0) throw IllegalArgumentException("The provided interval time must be greater than or equal to zero")
    if (iterations < 1) throw IllegalArgumentException("The provided iterations count must be great than or equal to one")

    var count = 0
    while count < iterations do {
      f()
      Thread.sleep(interval)
      count += 1
    }
  }

  def sampleFutures(): Unit = {
    val c = SampleClass()
    c.sampleMethod()

    val f1 = Future(
      c.sampleTimedExecutor(() => println("> Testing a timed execution on default interval: " + Instant.now()))
    )
    f1.onComplete {
      case Success(v) => println("Future f1 has successfully completed its work")
      case Failure(e) => println(s"Future f1 has failed to complete its work: $e")
    }

    val f2 = Future(
      c.sampleTimedExecutor(
        f = () => println("> Testing a timed execution on slower 3s interval: " + Instant.now()),
        interval = 3000
      )
    )
    f2.onComplete {
      case Success(v) => println("Future f2 has successfully completed its work")
      case Failure(e) => println(s"Future f2 has failed to complete its work: $e")
    }

    val result =
      for
        r1 <- f1
        r2 <- f2
      yield
        println(s"Both futures f1 and f2 are now finished")
        Success

    result.onComplete {
      case Success(s) =>
        println(s"Success Result = $s")
      case Failure(e) =>
        println(s"Error Result = $e")
    }

    while (!result.isCompleted) {}

    println("Finished...")
  }

  def fibonacciRecursive(n: Int): Int = {
    n match {
      case x if x < 0 => throw IllegalArgumentException("The input must be an integer greater than or equal to zero")
      case 0 | 1 => n
      case _ => fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2)
    }
  }

  def fibonacciIterative(n: Int): Int = {
    if (n < 0) throw IllegalArgumentException("The input must be an integer greater than or equal to zero")
    if (n == 0) return 0
    if (n == 1) return 1

    var prev1 = 1
    var prev2 = 0
    var sum = 0
    var i = 2

    while (i <= n) {
      sum = prev1 + prev2
      prev2 = prev1
      prev1 = sum
      i += 1
    }

    sum
  }
}
