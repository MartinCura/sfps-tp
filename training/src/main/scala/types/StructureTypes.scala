package sfps.types

import java.util.Locale
import java.time.LocalDateTime
import sfps.common.CountryCodes

abstract class MyRowElement extends Serializable with Product {
  def toDouble() : Option[Double]
  override def toString(): String = this.getClass.getName
}

abstract class MyInteger(number: Option[Int]) extends MyRowElement {
  override def toDouble(): Option[Double] = {
    number.flatMap(x => Option(x.toDouble))
  }
}

abstract class GenericCountryCode(shortName: Option[String]) extends MyRowElement {
  def toDouble(): Option[Double] =
    CountryCodes.getCode(shortName) match {
      case None => None
      case code => Some(code.get.toDouble)
    }
    /// Another version:
    // shortName.flatMap(CountryCodes.getCode2(_)).flatMap(c => Some(c.toDouble))
}

case class MaiScore(number: Option[Int]) extends MyInteger(number)
case class DeviceMatch(number: Option[Int]) extends MyInteger(number)
case class FactorCodes(number: Option[Int]) extends MyInteger(number)
case class FirstEncounter(number: Option[Int]) extends MyInteger(number)
case class IcAddress(number: Option[Int]) extends MyInteger(number)
case class IcInternet(number: Option[Int]) extends MyInteger(number)
case class IcSuspicious(number: Option[Int]) extends MyInteger(number)
case class IcVelocity(number: Option[Int]) extends MyInteger(number)
case class IcIdentity(number: Option[Int]) extends MyInteger(number)
case class IpRoutingMethod(number: Option[Int]) extends MyInteger(number)
case class ReasonCode(number: Option[Int]) extends MyInteger(number)
case class TimeOnPage(number: Option[Int]) extends MyInteger(number)

//BOOLEAN
case class Cancelled(number: Option[Int]) extends MyInteger(number)

case class BillingCountryCode(code: Option[String]) extends GenericCountryCode(code) {
  override def toString(): String = "billingCountryCode"
}

case class CardCountryCode(code: Option[String]) extends GenericCountryCode(code) {
  override def toString(): String = "cardCountryCode"
}

case class Pp1(number: Option[Int]) extends MyInteger(number)
case class Pp30(number: Option[Int]) extends MyInteger(number)
case class Pp60(number: Option[Int]) extends MyInteger(number)
case class Pp90(number: Option[Int]) extends MyInteger(number)

case class CaseDate(date: Option[String]) extends MyRowElement {
  def transform(dateString: String) = {
    LocalDateTime.parse(dateString)
  }
  override def toDouble(): Option[Double] = date.flatMap(d => Option(transform(d).toEpochSecond(java.time.ZoneOffset.UTC)))
}

case class CaseMinutesDistance(n: Option[Int]) extends MyInteger(n)

case class Apocrypha(n: Option[Int]) extends MyInteger(n)

case class MyRow(
  maiScore: MaiScore,
  deviceMatch: DeviceMatch,
  factorCodes: FactorCodes,
  firstEncounter: FirstEncounter,
  apocrypha: Apocrypha
  // icAddress: IcAddress,
  // icInternet: IcInternet,
  // icSuspicious: IcSuspicious,
  // icVelocity: IcVelocity,
  // icIdentity: Icidentity,
  // ipRoutingMethod: IpRoutingMethod,
  // reasonCode: ReasonCode,
  // timeOnPage: TimeOnPage,
  // billingCountryCode: BillingCountryCode,
  // cancelled: Cancelled,
  // cardCountryCode: CardCountryCode,
  // pp1: Pp1,
  // pp30: Pp30,
  // pp60: Pp60,
  // pp90: Pp90,
  // caseDate: CaseDate,
  // caseMinutesDistance: CaseMinutesDistance
)
