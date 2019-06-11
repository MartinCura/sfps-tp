package sfps.dbloader

import doobie._, doobie.implicits._, doobie.util.ExecutionContexts
import cats._, cats.data._, cats.effect.IO, cats.implicits._
import com.github.tototoshi.csv.CSVReader
import java.util.NoSuchElementException
import sfps.types.Schema

object DBLoader {

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

  // Numbers go plain, empty values are NULL, and any other strings is surrounded by single quotes ('')
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

  def addAndGetBack(tablename: String, columnNames: String, line: Seq[String]) = {
    val values = line.map(formatStringForSql(_)).reduce(_ + ',' + _)
    insert1(tablename, columnNames, values).withUniqueGeneratedKeys[Schema.MicroRow](columnNames.split(','):_*)
      .transact(xa).unsafeRunSync
 }

  def addLineToDB(tablename: String, columnNames: String, line: Seq[String]) = {
    // Format values into SQL-friendly format
    val values = line.map(formatStringForSql(_)).reduce(_ + ',' + _)

    insert1(tablename, columnNames, values).run
      .transact(xa).unsafeRunSync
  }

  def main() {
    println("Deleting and creating train table")
    (SqlCommands.dropTrain, SqlCommands.createTrain).mapN(_ + _).transact(xa).unsafeRunSync

    val reader = CSVReader.open(train_filename)
    val it = reader.iterator
    val columnNames: String = it.next.reduce(_ + ',' + _)
    println(columnNames)
    try {
      while (true) {
        addLineToDB("train", columnNames, it.next)
        print('.')
      }
    } catch {
      case e: java.util.NoSuchElementException => println("EOF train")
    }
    reader.close()


    // TODO: refactor repeated code?
    println("Deleting and creating test table")
    (SqlCommands.dropTest, SqlCommands.createTest).mapN(_ + _).transact(xa).unsafeRunSync

    val readerTest = CSVReader.open(test_filename)
    val itTest = readerTest.iterator
    val columnNamesTest: String = itTest.next.reduce(_ + ',' + _)
    println(columnNamesTest)
    try {
      while (true) {
        addLineToDB("test", columnNames, itTest.next)
        print('.')
      }
    } catch {
      case e: java.util.NoSuchElementException => println("EOF test")
    }
    readerTest.close()
  }

}
