package sfps.types

import shapeless.labelled

object Schema {

  type ReducedRow = (
    MaiScore, DeviceMatch, FactorCodes, FirstEncounter,
    IcAddress, IcInternet, IcSuspicious, IcVelocity, IcIdentity,
    IpRoutingMethod, ReasonCode, TimeOnPage, BillingCountryCode, Cancelled,
    CardCountryCode, Pp1, Pp30, Pp60, Pp90, CaseDate, CaseMinutesDistance,
  )

  val reducedColumns = """
    mai_score, DeviceMatch, FactorCodes, FirstEncounter,
    IcAddress, IcInternet, IcSuspicious, IcVelocity, IcIdentity,
    IpRoutingMethod, ReasonCode, TimeOnPage, billingCountryCode, cancelled,
    cardCountryCode, pp_1, pp_30, pp_60, pp_90, caseDate, case_minutes_distance
  """


  /// OLD schema

  type DataRow = (RowId, ActivityFields, TripFields, MaiFields1, MaiFields2, OnlineFields, PaymentFields, OtherFields, Label)

  case class RowId(
    id:               Int,
  )
  case class ActivityFields(
    deviceMatch:      Option[Int],
    factorCodes:      Option[Int],
    firstEncounter:   Option[Int],
    icAddress:        Option[Int],
    icInternet:       Option[Int],
    icSuspicious:     Option[Int],
    icVelocity:       Option[Int],
    icidentity:       Option[Int],
    ipRoutingMethod:  Option[Int],
    reasonCode:       Option[Int],
    timeOnPage:       Option[Int],
    pp_1:             Option[Int],
    pp_30:            Option[Int],
    pp_60:            Option[Int],
    pp_90:            Option[Int],
  )
  case class TripFields(
    countryCode:      Option[String],
    countryFrom:      Option[String],
    countryTo:        Option[String],
    distance_to_arrival: Option[Double],
    distance_to_departure: Option[Double],
    iataFrom:         String,
    iataTo:           String,
    ip_city:          Option[String],
    lagTimeHours:     Int,
    speed_to_departure: Option[Double],
    triangulation_height: Option[Double],
    triangulation_height_speed: Option[Double],
    trip_distance:    Option[Double]
  )
  case class MaiFields1(
    mai_score:        Option[Int],
    mai_advice:       Option[Int],
    mai_verification: Option[String],
    mai_reason:       Option[Int],
    mai_risk:         Option[Int],
    mai_status:       Option[Int],
    mai_unique:       Option[Int],
    mai_avg_secs:     Option[Int],
    mai_buys:         Option[Int],
    mai_searches:     Option[Int],
    mai_pax:          Option[String],
    mai_type:         Option[String],
    mai_rels:         Option[String],
    mai_app:          Option[Int],
  )
  case class MaiFields2(
    mai_urgency:      Option[String],
    mai_network:      Option[String],
    mai_all_pax:      Option[Int],
    mai_last_secs:    Option[Int],
    mai_language:     Option[String],
    mai_negative:     Option[Int],
    mai_suspect:      Option[Int],
    mai_os:           Option[String],
    mai_policy_score: Option[Int],
    mai_pulevel:      Option[Int],
    mai_city:         Option[String],
    mai_region:       Option[String],
    maibis_score:     Option[Int],
    maibis_reason:    Option[String],
    maitris_score:    Option[Int],
  )
  case class OnlineFields(
    online_airport_state: Int,
    online_billing_address_state: Int,
    online_cep_number_bond: Int,
    online_city_bond: Int,
    online_ddd:       Int,
    online_ddd_bond:  Int,
    online_death:     Int,
    online_email:     Int,
    online_family_bond: Int,
    online_ip_state:  Int,
    online_name:      Int,
    online_phone:     Int,
    online_queries:   Int,
    online_state_bond: Int,
  )
  case class PaymentFields(
    billingCountryCode: Option[String],
    cancelled:        Option[Int],
    cardCountryCode:  Option[String],
    totalUsdAmount:   Double,
    paymentsCardType: Option[String],
    paymentsInstallments: Int,
  )
  case class OtherFields(
    caseDate:         Option[String],
    case_minutes_distance: Option[Int],
    cases_count:      Option[Int],
    channel:          Option[String],
    correl_id:        Option[Int],
    count_different_cards: Option[String],
    count_different_installments: Option[Int],
    domain_proc:      Option[Double],
    eulerBadge:       Option[String],
    friendly:         Int,
    hours_since_first_verification: Option[Int],
    many_holders_for_card: Option[Double],
    many_names_for_document: Option[Double],
    same_field_features: Option[String],
  )
  case class Label(
    apocrypha:        Int
  )

  val columns = """
    id,

    deviceMatch,
    factorCodes,
    firstEncounter,
    icAddress,
    icInternet,
    icSuspicious,
    icVelocity,
    icidentity,
    ipRoutingMethod,
    reasonCode,
    timeOnPage,
    pp_1,
    pp_30,
    pp_60,
    pp_90,

    countryCode,
    countryFrom,
    countryTo,
    distance_to_arrival,
    distance_to_departure,
    iataFrom,
    iataTo,
    ip_city,
    lagTimeHours,
    speed_to_departure,
    triangulation_height,
    triangulation_height_speed,
    trip_distance,

    mai_score,
    mai_advice,
    mai_verification,
    mai_reason,
    mai_risk,
    mai_status,
    mai_unique,
    mai_avg_secs,
    mai_buys,
    mai_searches,
    mai_pax,
    mai_type,
    mai_rels,
    mai_app,

    mai_urgency,
    mai_network,
    mai_all_pax,
    mai_last_secs,
    mai_language,
    mai_negative,
    mai_suspect,
    mai_os,
    mai_policy_score,
    mai_pulevel,
    mai_city,
    mai_region,
    maibis_score,
    maibis_reason,
    maitris_score,

    online_airport_state,
    online_billing_address_state,
    online_cep_number_bond,
    online_city_bond,
    online_ddd,
    online_ddd_bond,
    online_death,
    online_email,
    online_family_bond,
    online_ip_state,
    online_name,
    online_phone,
    online_queries,
    online_state_bond,

    billingCountryCode,
    cancelled,
    cardCountryCode,
    totalUsdAmount,
    paymentsCardType,
    paymentsInstallments,

    caseDate,
    case_minutes_distance,
    cases_count,
    channel,
    correl_id,
    count_different_cards,
    count_different_installments,
    domain_proc,
    eulerBadge,
    friendly,
    hours_since_first_verification,
    many_holders_for_card,
    many_names_for_document,
    same_field_features,

    apocrypha
  """

}
