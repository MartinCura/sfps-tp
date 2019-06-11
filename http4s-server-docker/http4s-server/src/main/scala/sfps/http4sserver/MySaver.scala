package sfps.http4sserver

import cats.effect.{ExitCode, IO, Resource}
import cats.implicits._
import fs2.{Stream, io, text}
import java.nio.file.Paths
import java.util.concurrent.Executors

import cats.Applicative

import scala.concurrent.ExecutionContext

object MySaver {

  private val blockingExecutionContext =
    Resource.make(IO(ExecutionContext.fromExecutorService(Executors.newCachedThreadPool())))(ec => IO(ec.shutdown()))

  def save[G[_]: Applicative](body: Stream[G, Byte]): Unit = {

    val save = Stream.resource(blockingExecutionContext).flatMap { blockingEC =>

      body.through(text.utf8Decode)
        .through(text.lines)
        .filter(s => !s.trim.isEmpty)
        .through(text.utf8Encode)
        .through(
          io.file.writeAll(Paths.get("testdata/test.csv"), blockingEC))
    }

    save.compile.drain.as(ExitCode.Success)

  }


}
