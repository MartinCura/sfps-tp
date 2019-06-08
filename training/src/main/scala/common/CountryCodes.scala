package sfps.common
import com.github.tototoshi.csv.CSVReader

object CountryCodes {

  lazy val shortNameColumn = "alpha-2"
  lazy val codeColumn = "code-country"

  val reader = CSVReader.open("./src/main/resources/country_codes_2char.csv")
  val cc: Map[String, Int] = reader.all().drop(1).map(c => (c(1), c(2).toInt)).toMap

  // Not too pretty (tho we don't actually use this one)
  def getShortName(id: Int): String =
    cc.find(_._2 == id).getOrElse(("", 0))._1

  def getCode(shortName: Option[String]): Option[Int] = shortName match {
    case None => None
    case _ => cc.get(shortName.get)
  }

}
