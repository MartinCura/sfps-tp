package sfps.etl

import doobie._, doobie.implicits._, doobie.util.ExecutionContexts
import cats._, cats.data._, cats.effect.IO, cats.implicits._
import fs2.Stream

object ETL extends App {

  // TODO: read DB_NAME from some constants / env file
  lazy val DB_NAME = "sfps_db"

  // Create db transactor
  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)
  val xa = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver",
    s"jdbc:postgresql://localhost:5442/$DB_NAME",
    "postgres",
    "",
    ExecutionContexts.synchronous
  )

  // YOLO mode, easier for debugging
  // val y = xa.yolo
  // import y._

  val data = (sql"SELECT " ++ Fragment.const0(DataSchema.columns) ++ sql" FROM train")
    .query[(DataSchema.RowId, DataSchema.ActivityFields, DataSchema.TripFields, DataSchema.MaiFields1, DataSchema.MaiFields2, DataSchema.OnlineFields, DataSchema.PaymentFields, DataSchema.OtherFields, DataSchema.Label)]
    .stream   // fs2.Stream
    .take(1)
    .compile.toList
    .transact(xa)
    .unsafeRunSync  // List of data row tuples

  // I'm not sure this serves as a stream, if it isn't it will probably break

  data.map(_.productIterator.foreach(println))

}
