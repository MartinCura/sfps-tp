package sfps.dbloader

import doobie._, doobie.implicits._, doobie.util.ExecutionContexts
import cats._, cats.data._, cats.effect.IO, cats.implicits._
import scala.io.Source
import com.github.tototoshi.csv.CSVReader
import java.util.NoSuchElementException

object DBLoader extends App {

  // TODO: read DB_NAME from .env
  lazy val DB_NAME = "sfps_db"
  val train_filename = "../../train.csv"
  val test_filename = "../../test.csv"

  // Create db transactor
  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)
  val xa = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver",
    s"jdbc:postgresql://localhost:5442/$DB_NAME",
    "postgres",
    "",
    ExecutionContexts.synchronous
  )

  // Insert 1 row into specified table
  def insert1(tablename: String, keys: String, row: String) : Update0 =
    (
      sql"INSERT INTO " ++ Fragment.const(tablename) ++ Fragments.parentheses(Fragment.const0(keys)) ++ sql"""
          VALUES """ ++ Fragments.parentheses(Fragment.const0(row))
    ).update

  // Numbers go plain, empty values are NULL, and any other strings is surrounded by ''
  def formatStringForSql(s: String): String =
    try {
      s.toDouble
      return s
    } catch {
      case ex: NumberFormatException => {
        s match {
          case "" => "NULL"
          case "\"\"" => "''"
          case _ => s"'${s.replace("'", "\"")}'"
        }
      }
    }

  def addLineToDB(tablename: String, columnNames: String, line: Seq[String]) = {
    // Format values into SQL-friendly format
    val values = line.map(formatStringForSql(_)).reduce(_ + "," + _)

    insert1(tablename, columnNames, values).run.transact(xa).unsafeRunSync
    print('.')
  }


  // Delete and create train table
  (SqlCommands.dropTrain, SqlCommands.createTrain).mapN(_ + _).transact(xa).unsafeRunSync

  val reader = CSVReader.open(train_filename)
  val it = reader.iterator
  val columnNames: String = it.next.reduce(_ + ',' + _)
  println(columnNames)
  try {
    while (true) {
      addLineToDB("train", columnNames, it.next)
    }
  } catch {
    case e: java.util.NoSuchElementException => println("EOF")
  }
  reader.close()


  // TODO: refactor repeated code?
  // Delete and create test table
  (SqlCommands.dropTest, SqlCommands.createTest).mapN(_ + _).transact(xa).unsafeRunSync

  val readerTest = CSVReader.open(test_filename)
  val itTest = readerTest.iterator
  val columnNamesTest: String = itTest.next.reduce(_ + ',' + _)
  println(columnNamesTest)
  try {
    while (true) {
      addLineToDB("test", columnNames, itTest.next)
    }
  } catch {
    case e: java.util.NoSuchElementException => println("EOF")
  }
  readerTest.close()

}
