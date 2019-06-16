package sfps.http4sserver

import cats.effect.Sync
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.circe.CirceEntityDecoder._
import sfps.schema._

object Http4sserverRoutes {

  def modelEvaluationRoutes[F[_]: Sync](M: EvaluatorRepo[F]): HttpRoutes[F] = {

    val dsl = new Http4sDsl[F]{}
    import dsl._
    HttpRoutes.of[F] {
      case req @ POST -> Root / "eval" =>
        for {
          // Decode a User request
          theRow <- req.as[RowDTO]
          result <- M.eval(theRow)
          resp <- Ok(result)
        } yield resp
    }

  }

}
