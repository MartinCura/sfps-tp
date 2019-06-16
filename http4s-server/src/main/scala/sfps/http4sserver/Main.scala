package sfps.http4sserver

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._

object Main extends IOApp {

  def run(args: List[String]) =
    Http4sserverServer.stream[IO].compile.drain.as(ExitCode.Success)

}
