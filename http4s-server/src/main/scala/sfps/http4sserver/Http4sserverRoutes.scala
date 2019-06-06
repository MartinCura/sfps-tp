package sfps.http4sserver

import cats.effect.Sync
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.circe.CirceEntityDecoder._
import sfps.http4sserver.EvaluatorRepo.Flower

object Http4sserverRoutes {


  def modelEvaluationRoutes[F[_]: Sync](M: EvaluatorRepo[F]): HttpRoutes[F] = {

    val dsl = new Http4sDsl[F]{}
    import dsl._
    HttpRoutes.of[F] {
      case req @ POST -> Root / "eval" =>
        for {
          // Decode a User request
          flower <- req.as[Flower]
          result <- M.eval(flower)
          resp <- Ok(result)
        } yield resp
    }

  }

}