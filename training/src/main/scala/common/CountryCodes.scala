package sfps.common
import com.github.tototoshi.csv.CSVReader

object CountryCodes {

  lazy val shortNameColumn = "alpha-2"
  lazy val codeColumn = "code-country"

  lazy val defaultReturn = ("", 0)

  val reader = CSVReader.open("./src/main/resources/country_codes_2char.csv")
  val cc: Map[String, Int] = reader.all().drop(1).map(c => (c(1), c(2).toInt)).toMap

  def getShortName(id: Int): String =
    cc.find(_._2 == id).getOrElse(defaultReturn)._1

  def getCode(shortName: String): Int =
    cc.get(shortName).getOrElse(defaultReturn._2)

}
