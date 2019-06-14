package sfps.db

import doobie._, doobie.implicits._, doobie.util.ExecutionContexts
import Fragments.{ whereAndOpt }
import cats._, cats.data._, cats.effect.IO, cats.implicits._

import sfps.types.MyRowElement
// import sfps.db.Schema

object DbLookup {

    // TODO: read DB_NAME from .env
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

    /** Nice to have: rows hashed on input, and here fetch those rows with same hash and if any compare */
    def findRows(tablename: String, comparators: Map[String, String]): List[Schema.DataRow] = {
      val whereFragments = comparators.map(t => Some(fr"${t._1} = ${t._2}")).toList  // ToDo: rechequear este Some
      val statement = sql"SELECT " ++ Fragment.const0(Schema.columns) ++
                      sql" FROM " ++ Fragment.const(tablename) ++
                      whereAndOpt(whereFragments:_*)
      return statement.query[Schema.DataRow].stream.compile.toList.transact(xa).unsafeRunSync
    }

    // def findRow(tablename: String, columnNames: Seq[String], columnValues: Seq[String]): Boolean = {
    //   val whereFragments = (columnNames zip columnValues).map(t => Some(fr"${t._1} = ${t._2}"))  // ToDo: rechequear este Some
    //   val statement = sql"SELECT id FROM " ++ Fragment.const(tablename) ++
    //     whereAndOpt(whereFragments:_*)
    //   return statement.query[Int].stream.compile.toList.transact(xa).unsafeRunSync.isEmpty
    // }

    def isRowInTrain(row: Schema.DataRow): Boolean = {
      val comparator: MyRowElement = row._2.pp_30
      val comps = Map(comparator.toString() -> (comparator.toDouble match {
        case None => ""
        case x => x.get.toString
      }))
      val found: List[Schema.DataRow] = findRows("train", comps)
      return !found.isEmpty && found.exists(_ == row)
    }

}
