package sfps.http4sserver

import cats.Applicative
import cats.effect.Sync
import cats.implicits._
import evaluator.Eval
import io.circe.generic.semiauto.deriveDecoder
import io.circe.{Decoder, Encoder, Json}
import org.http4s.circe.{jsonEncoderOf, jsonOf}
import org.http4s.{EntityDecoder, EntityEncoder}
import sfps.db.DbLookup
import sfps.types._
import sfps.schema
import sfps.schema._


trait EvaluatorRepo[F[_]]{
  def eval(f: RowDTO): F[EvaluatorRepo.Result]
}

object EvaluatorRepo {

  implicit def apply[F[_]](implicit ev: EvaluatorRepo[F]): EvaluatorRepo[F] = ev

  val MODEL_FILE_LOCATION = "xgboostModel.pmml"
  def modelEvaluation(myRow: RowDTO) : Option[String] = {
    val doubles = Mappers.asFeatureList(Mappers.doubleTypedMapper(RowDTO.mapToDataRow(myRow)))
    Eval.openFile(MODEL_FILE_LOCATION).map(file => Eval.evaluateSetOfFeatures(doubles, Eval.evaluator(file)))
      .map {
        case Left(msg) => "There was an error evaluating " + myRow + ". Error was " + msg
        case Right(evaluation) => evaluation.head.toString()
      }
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
    def eval(f: RowDTO): F[Result] = {
      // TODO chequear si esta en la base de datos y si esta devolver apocrypha
      val evalValue = modelEvaluation(f)
      Result(evalValue.getOrElse("There was an error in evaluation")).pure[F]
    }

  }

}






