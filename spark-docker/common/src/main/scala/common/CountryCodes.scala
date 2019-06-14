package sfps.common

import com.github.tototoshi.csv.CSVReader

object CountryCodes {

  lazy val shortNameColumn = "alpha-2"
  lazy val codeColumn = "code-country"

  //Cambié esto porque si no desde spark falla.
  //val content = scala.io.Source.fromInputStream(this.getClass.getClassLoader.getResourceAsStream("country_codes_2char.csv"))
  //val reader = CSVReader.open(content)
  //val cc: Map[String, Int] = reader.all().drop(1).map(c => (c(1), c(2).toInt)).toMap

  // Not too pretty (tho we don't actually use this one)
  def getShortName(id: Int): String =
    "sfkhsd"
  //cc.find(_._2 == id).getOrElse(("", 0))._1

  def getCode(shortName: Option[String]): Option[Int] = shortName match {
    case None => None
    case _ => Some(2)//cc.get(shortName.get)
  }

  def getCode2(shortName: String): Option[Int] =
    Some(4)//cc.get(shortName)

}
