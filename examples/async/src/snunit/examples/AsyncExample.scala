package snunit.examples

import snunit._
import scala.scalanative.loop.Timer
import scala.concurrent.ExecutionContext.Implicits.global

object AsyncExample {
  def main(args: Array[String]): Unit = {
    val server =
      AsyncServerBuilder
        .withRequestHandler(req => {
          req.method match {
            case Method.GET =>
              var t: Timer = null.asInstanceOf[Timer]
              t = Timer.timeout(5000) { () =>
                req.send(
                  statusCode = 200,
                  content = s"Hello world!\n",
                  headers = Seq("Content-Type" -> "text/plain")
                )
                t.clear()
              }
            case _ =>
              req.send(
                statusCode = 404,
                content = s"Not found\n",
                headers = Seq("Content-Type" -> "text/plain")
              )
          }
        })
        .build()
  }
}