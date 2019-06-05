import java.util.Locale
import java.time.LocalDateTime

abstract class MyRowElement extends Serializable with Product {
    def toDouble() : Option[Double]
    override def toString(): String = this.getClass.getName 
}

abstract class CountryCode(code: Option[String]) extends MyRowElement {
    //TODO martín
    val countryDictionary = Map("AR" -> 54.0D, "BR" -> 78.0D)
    override def toDouble(): Option[Double] = {
        code.flatMap(countryDictionary.get(_))
    }   
}
 
abstract class MyInteger(number: Option[Int]) extends MyRowElement {
    override def toDouble(): Option[Double] = {
        number.flatMap(x => Option(x.toDouble))
    }
}

case class MaiScore(number: Option[Int]) extends MyInteger(number) 
case class DeviceMatch(number: Option[Int]) extends MyInteger(number) 
case class FactorCodes(number: Option[Int]) extends MyInteger(number) 
case class IcAddress(number: Option[Int]) extends MyInteger(number) 
case class IcInternet(number: Option[Int]) extends MyInteger(number) 
case class IcSuspicious(number: Option[Int]) extends MyInteger(number)
case class IcVelocity(number: Option[Int]) extends MyInteger(number) 
case class Icidentity(number: Option[Int]) extends MyInteger(number)
case class IpRoutingMethod(number: Option[Int]) extends MyInteger(number) 
case class ReasonCode(number: Option[Int]) extends MyInteger(number)
case class TimeOnPage(number: Option[Int]) extends MyInteger(number) 

case class BillingCountryCode(code: Option[String]) extends CountryCode(code) {
    override def toString(): String = "billingCountryCode" 
}

//BOOLEAN
case class cancelled(number: Option[Int]) extends MyInteger(number)

case class CardCountryCode(code: Option[String]) extends CountryCode(code)

case class pp1(number: Option[Int]) extends MyInteger(number)
case class pp30(number: Option[Int]) extends MyInteger(number)
case class pp60(number: Option[Int]) extends MyInteger(number)
case class pp90(number: Option[Int]) extends MyInteger(number)

case class caseDate(date: Option[String]) extends MyRowElement {
    def transform(dateString: String) = {
        LocalDateTime.parse(dateString)
    }
    override def toDouble(): Option[Double] = date.flatMap(d => Option(transform(d).toEpochSecond(java.time.ZoneOffset.UTC)))
}

case class caseMinutesDistance(n: Option[Int]) extends MyInteger(n)


