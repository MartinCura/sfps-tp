package sfps.etl

import doobie._, doobie.implicits._, doobie.util.ExecutionContexts
import cats._, cats.data._, cats.effect.IO, cats.implicits._
import fs2.Stream
import sfps.types._

/**
 * Sample usage:
 *
 * val data = getData(None)
 * val fiveRows = getNextRows(data, 5)
 **/
object ETL {
  // TODO: move some things to initialization with parameter table name

  // TODO: read DB_NAME from some constants / env file
  lazy val DB_NAME = "sfps_db"

  private def transactor(): Transactor.Aux[IO,Unit] = {
    // Create db transactor
    implicit val cs = IO.contextShift(ExecutionContexts.synchronous)
    val xa = Transactor.fromDriverManager[IO](
      "org.postgresql.Driver",
      s"jdbc:postgresql://localhost:5442/$DB_NAME",
      "postgres",
      "",
      ExecutionContexts.synchronous
    )
    return xa

    // YOLO mode, easier for debugging
    // val y = xa.yolo
    // import y._
  }

  private def selectStatement =
    sql"SELECT " ++ Fragment.const0(Schema.columns) ++ sql" FROM train"

  private def reducedSelectStatement = sql"SELECT " ++ Fragment.const0(Schema.reducedColumns) ++ sql" FROM train"

  def getStream(): Stream[ConnectionIO, Schema.DataRow] =
    selectStatement.query[Schema.DataRow].stream

  def getData(n: Option[Int]) = n match {
    case None => getStream().transact(transactor())             // Get a stream for all of it
    case Some(n) => getStream().take(n).transact(transactor())  // Stop after n elements
  }

  def getNextRows(data: Stream[IO, Schema.DataRow], n: Int) = data.take(n).compile.toList.unsafeRunSync

  def getReducedData(n: Int): List[Schema.ReducedRow] = {
    val xa = transactor()
    // println(BillingCountryCode(Some("ARG")).toString())
    return reducedSelectStatement.query[Schema.ReducedRow].stream.take(n).compile.toList.transact(xa).unsafeRunSync
  }

  def getMicroData(n: Int): List[Schema.MicroRow] =
    reducedSelectStatement.query[Schema.MicroRow].stream.take(n).compile.toList.transact(transactor()).unsafeRunSync

}
