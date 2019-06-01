package sfps.etl

import doobie._, doobie.implicits._, doobie.util.ExecutionContexts
import cats._, cats.data._, cats.effect.IO, cats.implicits._

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

  val program3: ConnectionIO[(Int, Double)] =
    for {
      a <- sql"select 42".query[Int].unique
      b <- sql"select random()".query[Double].unique
    } yield (a, b)

  val result = program3.transact(xa).unsafeRunSync
  println(result)

}

// trait Greeting {
//   lazy val greeting: String = "hello"
// }
