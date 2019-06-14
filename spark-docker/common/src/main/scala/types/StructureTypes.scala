package sfps.types

import java.util.Locale
import java.time.LocalDateTime
import sfps.common.CountryCodes
import java.time.format.DateTimeFormatter


/** General types **/

abstract class MyRowElement(name: Option[String]) extends Serializable with Product {
  override def toString(): String = name match {
    case None => this.getClass.getName
    case someName => someName.get
  }
  def toDouble() : Option[Double]
}

abstract class MyInteger(number: Option[Int], name: Option[String]) extends MyRowElement(name) {
  override def toDouble(): Option[Double] = {
    number.flatMap(x => Option(x.toDouble))
  }
}
abstract class MyDouble(number: Option[Double], name: Option[String]) extends MyRowElement(name) {
  override def toDouble(): Option[Double] =
    number
}
abstract class MyString(text: Option[String], name: Option[String]) extends MyRowElement(name) {
  override def toDouble(): Option[Double] =
    text.flatMap(c => Option(c.hashCode))
}
abstract class MyDate(date: Option[String], name: Option[String]) extends MyRowElement(name) {
  def transform(dateString: String) = {
    import java.util.Locale
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
    LocalDateTime.parse(dateString, inputFormatter)
  }
  override def toDouble(): Option[Double] =
    date.flatMap(d => Option(transform(d).toEpochSecond(java.time.ZoneOffset.UTC)))
}


/** Generic types **/

abstract class GenericCountryCode(shortName: Option[String], columnName: Option[String]) extends MyString(shortName, columnName) {
  override def toDouble(): Option[Double] =
    CountryCodes.getCode(shortName) match {
      case None => None
      case code => Some(code.get.toDouble)
    }
    /// another version:
    // shortName.flatMap(CountryCodes.getCode2(_)).flatMap(c => Some(c.toDouble))
}
abstract class GenericIata(code: Option[String], columnName: Option[String]) extends MyString(code, columnName)


/** Label type **/
case class Apocrypha(n: Option[Int]) extends MyInteger(n, Some("apocrypha"))


/** Column types **/

case class DeviceMatch(number: Option[Int]) extends MyInteger(number, Some("deviceMatch"))
case class FactorCodes(number: Option[Int]) extends MyInteger(number, Some("factorCodes"))
case class FirstEncounter(number: Option[Int]) extends MyInteger(number, Some("firstEncounter"))
case class IcAddress(number: Option[Int]) extends MyInteger(number, Some("icAddress"))
case class IcInternet(number: Option[Int]) extends MyInteger(number, Some("icInternet"))
case class IcSuspicious(number: Option[Int]) extends MyInteger(number, Some("icSuspicious"))
case class IcVelocity(number: Option[Int]) extends MyInteger(number, Some("icVelocity"))
case class IcIdentity(number: Option[Int]) extends MyInteger(number, Some("icIdentity"))
case class IpRoutingMethod(number: Option[Int]) extends MyInteger(number, Some("ipRoutingMethod"))
case class ReasonCode(number: Option[Int]) extends MyInteger(number, Some("reasonCode"))
case class TimeOnPage(number: Option[Int]) extends MyInteger(number, Some("timeOnPage"))
case class Pp1(number: Option[Int]) extends MyInteger(number, Some("pp_1"))
case class Pp30(number: Option[Int]) extends MyInteger(number, Some("pp_30"))
case class Pp60(number: Option[Int]) extends MyInteger(number, Some("pp_60"))
case class Pp90(number: Option[Int]) extends MyInteger(number, Some("pp_90"))

case class CountryCode(code: Option[String]) extends GenericCountryCode(code, Some("countryCode"))
case class CountryFrom(code: Option[String]) extends GenericCountryCode(code, Some("countryFrom"))
case class CountryTo(code: Option[String]) extends GenericCountryCode(code, Some("countryTo"))

case class DistanceToArrival(number: Option[Double]) extends MyDouble(number, Some("distance_to_arrival"))
case class DistanceToDeparture(number: Option[Double]) extends MyDouble(number, Some("distance_to_departure"))

case class IataFrom(code: Option[String]) extends GenericIata(code, Some("iataFrom"))
case class IataTo(code: Option[String]) extends GenericIata(code, Some("iataTo"))

case class IpCity(code: Option[String]) extends MyRowElement(Some("ip_city")) {
  def toDouble(): Option[Double] =
    code.flatMap(c => Option(c.hashCode))
}

case class LagTimeHours(hours: Option[Int]) extends MyInteger(hours, None)

case class SpeedToDeparture(speed: Option[Double]) extends MyDouble(speed, Some("speed_to_departure"))
case class TriangulationHeight(height: Option[Double]) extends MyDouble(height, Some("triangulation_height"))
case class TriangulationHeightSpeed(speed: Option[Double]) extends MyDouble(speed, Some("triangulation_height_speed"))
case class TripDistance(distance: Option[Double]) extends MyDouble(distance, Some("trip_distance"))

case class MaiScore(number: Option[Int]) extends MyInteger(number, Some("mai_score"))
case class MaiAdvice(number: Option[Int]) extends MyInteger(number, Some("mai_advice"))
case class MaiReason(number: Option[Int]) extends MyInteger(number, Some("mai_reason"))
case class MaiRisk(number: Option[Int]) extends MyInteger(number, Some("mai_risk"))
case class MaiStatus(number: Option[Int]) extends MyInteger(number, Some("mai_status"))
case class MaiUnique(number: Option[Int]) extends MyInteger(number, Some("mai_unique"))
case class MaiAvgSecs(number: Option[Int]) extends MyInteger(number, Some("mai_avg_secs"))
case class MaiBuys(number: Option[Int]) extends MyInteger(number, Some("mai_buys"))
case class MaiSearches(number: Option[Int]) extends MyInteger(number, Some("mai_searches"))
case class MaiApp(number: Option[Int]) extends MyInteger(number, Some("mai_app"))
case class MaiVerification(text: Option[String]) extends MyString(text, Some("mai_verification"))
case class MaiPax(text: Option[String]) extends MyString(text, Some("mai_pax"))
case class MaiType(text: Option[String]) extends MyString(text, Some("mai_type"))
case class MaiRels(text: Option[String]) extends MyString(text, Some("mai_rels"))

case class MaiUrgency(text: Option[String]) extends MyString(text, Some("mai_urgency"))
case class MaiNetwork(text: Option[String]) extends MyString(text, Some("mai_network"))
case class MaiLanguage(text: Option[String]) extends MyString(text, Some("mai_language"))
case class MaiOs(text: Option[String]) extends MyString(text, Some("mai_os"))
case class MaiCity(text: Option[String]) extends MyString(text, Some("mai_city"))
case class MaiRegion(text: Option[String]) extends MyString(text, Some("mai_region"))
case class MaiAllPax(number: Option[Int]) extends MyInteger(number, Some("mai_all_pax"))
case class MaiLastSecs(number: Option[Int]) extends MyInteger(number, Some("mai_last_secs"))
case class MaiNegative(number: Option[Int]) extends MyInteger(number, Some("mai_negative"))
case class MaiSuspect(number: Option[Int]) extends MyInteger(number, Some("mai_suspect"))
case class MaiPolicyScore(number: Option[Int]) extends MyInteger(number, Some("mai_policy_score"))
case class MaiPulevel(number: Option[Int]) extends MyInteger(number, Some("mai_pulevel"))

case class MaibisReason(text: Option[String]) extends MyString(text, Some("maibis_reason"))
case class MaibisScore(number: Option[Int]) extends MyInteger(number, Some("maibis_score"))
case class MaitrisScore(number: Option[Int]) extends MyInteger(number, Some("maitris_score"))

case class OnlineAirportState(number: Option[Int]) extends MyInteger(number, Some("online_airport_state"))
case class OnlineBillingAddressState(number: Option[Int]) extends MyInteger(number, Some("online_billing_address_state"))
case class OnlineCepNumberBond(number: Option[Int]) extends MyInteger(number, Some("online_cep_number_bond"))
case class OnlineCityBond(number: Option[Int]) extends MyInteger(number, Some("online_city_bond"))
case class OnlineDdd(number: Option[Int]) extends MyInteger(number, Some("online_ddd"))
case class OnlineDddBond(number: Option[Int]) extends MyInteger(number, Some("online_ddd_bond"))
case class OnlineDeath(number: Option[Int]) extends MyInteger(number, Some("online_death"))
case class OnlineEmail(number: Option[Int]) extends MyInteger(number, Some("online_email"))
case class OnlineFamilyBond(number: Option[Int]) extends MyInteger(number, Some("online_family_bond"))
case class OnlineIpState(number: Option[Int]) extends MyInteger(number, Some("online_ip_state"))
case class OnlineName(number: Option[Int]) extends MyInteger(number, Some("online_name"))
case class OnlinePhone(number: Option[Int]) extends MyInteger(number, Some("online_phone"))
case class OnlineQueries(number: Option[Int]) extends MyInteger(number, Some("online_queries"))
case class OnlineStateBond(number: Option[Int]) extends MyInteger(number, Some("online_state_bond"))

case class BillingCountryCode(code: Option[String]) extends GenericCountryCode(code, Some("billingCountryCode"))
case class CardCountryCode(code: Option[String]) extends GenericCountryCode(code, Some("cardCountryCode"))
case class Cancelled(number: Option[Int]) extends MyInteger(number, Some("cancelled"))         //BOOLEAN ?
case class TotalUsdAmount(amount: Option[Double]) extends MyDouble(amount, Some("someUsdAmount"))
case class PaymentsCardType(cardType: Option[String]) extends MyString(cardType, Some("paymentsCardType"))
case class PaymentsInstallments(amount: Option[Int]) extends MyInteger(amount, Some("paymentsInstallments"))

case class CaseDate(date: Option[String]) extends MyDate(date, Some("caseDate"))
case class CaseMinutesDistance(n: Option[Int]) extends MyInteger(n, Some("case_minutes_distance"))
case class CasesCount(n: Option[Int]) extends MyInteger(n, Some("cases_count"))
case class Channel(ch: Option[String]) extends MyString(ch, Some("channel"))
case class CorrelId(id: Option[Int]) extends MyInteger(id, Some("correl_id"))
case class CountDifferentCards(s: Option[String]) extends MyString(s, Some("count_different_cards"))
case class CountDifferentInstallments(amount: Option[Int]) extends MyInteger(amount, Some("count_different_installments"))
case class DomainProc(d: Option[Double]) extends MyDouble(d, Some("domain_proc"))
case class EulerBadge(s: Option[String]) extends MyString(s, Some("eulerBadge"))
case class Friendly(n: Option[Int]) extends MyInteger(n, Some("friendly"))
case class HoursSinceFirstVerification(hours: Option[Int]) extends MyInteger(hours, Some("hours_since_first_verification"))
case class ManyHoldersForCard(d: Option[Double]) extends MyDouble(d, Some("many_holders_for_card"))
case class ManyNamesForDocument(d: Option[Double]) extends MyDouble(d, Some("many_names_for_document"))

// TODO !!
case class SameFieldFeatures(json: Option[String]) extends MyRowElement(Some("same_field_features")) {
  // temp
  def toDouble(): Option[Double] = Some(0.0)
}



// Test composition  /// The rest are in Schema

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
