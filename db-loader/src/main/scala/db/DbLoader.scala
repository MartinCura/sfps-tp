package sfps.db

import doobie._, doobie.implicits._, doobie.util.ExecutionContexts
import cats._, cats.data._, cats.effect.IO, cats.implicits._
import com.github.tototoshi.csv.CSVReader
import java.util.NoSuchElementException
import java.nio.file.{Paths, Files}

object DbLoader {

  // TODO: read DB_NAME from .env
  lazy val DB_NAME = "sfps_db"
  lazy val HOST = "db"    // outside of docker: "localhost", inside: "db"
  lazy val PORT = 5432    // outside: 5442, inside: 5432
  val train_filename = "data/train.csv"
  val  test_filename = "data/test.csv"

  // Create db transactor
  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)
  val xa = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver",
    s"jdbc:postgresql://$HOST:$PORT/$DB_NAME",
    "postgres",
    "",
    ExecutionContexts.synchronous
  )

  // Use this to check a table exists before doing anything
  def doesTableExist(tablename: String): Boolean = {
    val statement = sql"""SELECT * FROM INFORMATION_SCHEMA.TABLES
                          WHERE TABLE_NAME = '""" ++ Fragment.const0(tablename) ++ sql"'"
    return (!statement.query.stream.compile.toList.transact(xa).unsafeRunSync.isEmpty)
  }

  // Insert 1 row into specified table
  def insert1(tablename: String, keys: String, row: String) : Update0 =
    (
      sql"INSERT INTO " ++ Fragment.const(tablename) ++ Fragments.parentheses(Fragment.const0(keys)) ++ sql"""
          VALUES """ ++ Fragments.parentheses(Fragment.const0(row))
    ).update

  // I think this works but i'm not quite sure, have to test order is not messing everything up (always the same problem)
  def addAndGetBack(tablename: String, columnNames: String, line: Seq[String]): Schema.DataRow = {
    val values = line.map(SqlCommands.formatStringForSql(_)).reduce(_ + ',' + _)
    return insert1(tablename, columnNames, values).withUniqueGeneratedKeys[Schema.DataRow](columnNames.split(','):_*)
      .transact(xa).unsafeRunSync
 }

  def addLineToDB(tablename: String, columnNames: String, line: Seq[String]) = {
    // Format values into SQL-friendly format
    val values = line.map(SqlCommands.formatStringForSql(_)).reduce(_ + ',' + _)

    insert1(tablename, columnNames, values).run
      .transact(xa).unsafeRunSync
  }

  def main(args: Array[String]) {
    assert(Files.exists(Paths.get(train_filename)))

    // Check table exists before anything
    if (DbLoader.doesTableExist("train")) {
      println(" *** Table train already exists, exiting. *** ")
      sys.exit
    }

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


    assert(Files.exists(Paths.get(test_filename)))

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
