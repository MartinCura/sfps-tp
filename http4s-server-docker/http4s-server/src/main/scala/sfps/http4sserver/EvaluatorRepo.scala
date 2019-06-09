package sfps.http4sserver

import cats.Applicative
import cats.effect.Sync
import cats.implicits._
import evaluator.Eval
import io.circe.generic.semiauto.deriveDecoder
import io.circe.{Decoder, Encoder, Json}
import org.http4s.circe.{jsonEncoderOf, jsonOf}
import org.http4s.{EntityDecoder, EntityEncoder}


trait EvaluatorRepo[F[_]]{
  def eval(f: EvaluatorRepo.Flower): F[EvaluatorRepo.Result]
}

object EvaluatorRepo {

  implicit def apply[F[_]](implicit ev: EvaluatorRepo[F]): EvaluatorRepo[F] = ev

  def modelEvaluation(flower: Flower) : Option[String] = {
    Eval.openFile("xgboostModel.pmml").map(file => Eval.evaluateSetOfFeatures(List(flower.sepalLength,
      flower.sepalWidth, flower.petalLength, flower.petalWidth), Eval.evaluator(file)))
      .map {
        case Left(msg) => "there was an error evaluating " + flower
        case Right(evaluation) => evaluation.head.toString()
      }
  }

  case class Flower(sepalLength: Double, sepalWidth: Double, petalLength: Double, petalWidth: Double)
  object Flower {

    implicit val flowerEnconder: Encoder[Flower] = new Encoder[Flower] {
      final def apply(a: Flower): Json = Json.obj(
        ("sepal_length", Json.fromDoubleOrString(a.sepalLength)),
        ("sepal_width", Json.fromDoubleOrString(a.sepalWidth)),
        ("petal_length", Json.fromDoubleOrString(a.petalLength)),
        ("petal_width", Json.fromDoubleOrString(a.petalWidth))
      )
    }

    implicit val flowerDecoder: Decoder[Flower] = deriveDecoder[Flower]

    implicit def flowerEntityDecoder[F[_]: Sync]: EntityDecoder[F, Flower] =
      jsonOf

    implicit def flowerEntityEncoder[F[_]: Applicative]: EntityEncoder[F, Flower] =
      jsonEncoderOf[F, Flower]

  }


  final case class Result(results: String) extends AnyVal
  object Result {
    implicit val resultEnconder: Encoder[Result] = new Encoder[Result] {
      final def apply(a: Result): Json = Json.obj(
        ("message", Json.fromString(a.results)),
      )
    }
    implicit def resultEntityEncoder[F[_]: Applicative]: EntityEncoder[F, Result] =
      jsonEncoderOf[F, Result]
  }

  def impl[F[_]: Applicative]: EvaluatorRepo[F] = new EvaluatorRepo[F]{
    def eval(f: Flower): F[Result] = {
      val evalValue = modelEvaluation(f)
      Result(evalValue.getOrElse("There was an error in evaluation")).pure[F]
    }

  }

}






