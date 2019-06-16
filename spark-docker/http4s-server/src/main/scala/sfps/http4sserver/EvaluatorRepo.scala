package sfps.http4sserver

import cats.Applicative
import cats.implicits._
import evaluator.Eval
import io.circe.{Encoder, Json}
import org.http4s.EntityEncoder
import org.http4s.circe.jsonEncoderOf
import sfps.db.DbLookup
import sfps.etl.ETL
import sfps.schema._
import sfps.types._
import sfps.db.SqlCommands
import sfps.db.DbLoader

trait EvaluatorRepo[F[_]]{
  def eval(f: RowDTO): F[EvaluatorRepo.Result]
}

object EvaluatorRepo {

  implicit def apply[F[_]](implicit ev: EvaluatorRepo[F]): EvaluatorRepo[F] = ev

  //TODO leer de archivo de configuracion.
  val MODEL_FILE_LOCATION = "xgboostModel.pmml"

  def predict(myRow: RowDTO) : Either[String, Int] = {
    //Gather data.
    val doubles = Mappers.asFeatureList(Mappers.doubleTypedMapper(RowDTO.mapToDataRow(myRow)))

    //open file and eval model.
    val evaluationResult : Either[String, Int] =
      Eval.openFile(MODEL_FILE_LOCATION)
      .map(file => Eval.evaluateSetOfFeatures("apocrypha", doubles, Eval.evaluator(file)))
      .map {
        case Left(msg) => Left(s"Error evaluating model: $msg")
        case Right(evaluation) => Right(evaluation)
      }.getOrElse(Left("An error has ocurred"))


    evaluationResult

  }


  final case class Result(results: String) extends AnyVal
  object Result {
    implicit val resultEnconder: Encoder[Result] = new Encoder[Result] {
      final def apply(a: Result): Json = Json.obj(
        ("result", Json.fromString(a.results)),
      )
    }
    implicit def resultEntityEncoder[F[_]: Applicative]: EntityEncoder[F, Result] =
      jsonEncoderOf[F, Result]
  }

  def impl[F[_]: Applicative]: EvaluatorRepo[F] = new EvaluatorRepo[F]{
    def eval(rowToPredict: RowDTO): F[Result] = {

      //Chequeo base de datos
      val result : String = DbLookup.getRowInTrain(RowDTO.mapToDataRow(rowToPredict)) match {
        case Some(row) => row._9.apocrypha.toString()
        case None => predict(rowToPredict) match {
          case Left(error) => s"An error ocurred: $error"
          case Right(prediction) => {
            val withPredictedRow = rowToPredict.copy(apocrypha = Option(prediction))
            val stringRows = RowDTO.mapToStringList(withPredictedRow)

            DbLoader.addLineToDB("train", SqlCommands.allColumns, stringRows)

            prediction.toString
          }
        }
      }

      Result(result).pure[F]
    }

  }

}
