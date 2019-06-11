package sfps.types

import shapeless.labelled

/** Schema of data */
object Schema {

  /** Reduced rows for testing */

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


  /** Micro rows for testing */

  val MicroRowFeatures = List(
    MaiScore, DeviceMatch, FactorCodes, FirstEncounter,
    Apocrypha
  )

  // type MicroRow = (
  //   MaiScore, DeviceMatch, FactorCodes, FirstEncounter,
  //   Apocrypha
  // )
  case class MicroRow (
    mai_score: MaiScore,
    deviceMatch: DeviceMatch,
    factorCodes: FactorCodes,
    firstEncounter: FirstEncounter,
    apocrypha: Apocrypha
  )

  val microColumns = """
    mai_score, DeviceMatch, FactorCodes, FirstEncounter,
    APOCRYPHA
  """



  /** The order should be exactly the same between the composition and the columns string!! **/

  type DataRow = (RowId, ActivityFields, TripFields, MaiFields1, MaiFields2, OnlineFields, PaymentFields, OtherFields, Label)

  case class RowId(
    id:               Int,
  )
  case class ActivityFields(
    deviceMatch:      DeviceMatch,
    factorCodes:      FactorCodes,
    firstEncounter:   FirstEncounter,
    icAddress:        IcAddress,
    icInternet:       IcInternet,
    icSuspicious:     IcSuspicious,
    icVelocity:       IcVelocity,
    icidentity:       IcIdentity,
    ipRoutingMethod:  IpRoutingMethod,
    reasonCode:       ReasonCode,
    timeOnPage:       TimeOnPage,
    pp_1:             Pp1,
    pp_30:            Pp30,
    pp_60:            Pp60,
    pp_90:            Pp90,
  )
  case class TripFields(
    countryCode:      CountryCode,
    countryFrom:      CountryFrom,
    countryTo:        CountryTo,
    distance_to_arrival: DistanceToArrival,
    distance_to_departure: DistanceToDeparture,
    iataFrom:         IataFrom,
    iataTo:           IataTo,
    ip_city:          IpCity,
    lagTimeHours:     LagTimeHours,
    speed_to_departure: SpeedToDeparture,
    triangulation_height: TriangulationHeight,
    triangulation_height_speed: TriangulationHeightSpeed,
    trip_distance:    TripDistance,
  )
  case class MaiFields1(
    mai_score:        MaiScore,
    mai_advice:       MaiAdvice,
    mai_verification: MaiVerification,
    mai_reason:       MaiReason,
    mai_risk:         MaiRisk,
    mai_status:       MaiStatus,
    mai_unique:       MaiUnique,
    mai_avg_secs:     MaiAvgSecs,
    mai_buys:         MaiBuys,
    mai_searches:     MaiSearches,
    mai_pax:          MaiPax,
    mai_type:         MaiType,
    mai_rels:         MaiRels,
    mai_app:          MaiApp,
  )
  case class MaiFields2(
    mai_urgency:      MaiUrgency,
    mai_network:      MaiNetwork,
    mai_all_pax:      MaiAllPax,
    mai_last_secs:    MaiLastSecs,
    mai_language:     MaiLanguage,
    mai_negative:     MaiNegative,
    mai_suspect:      MaiSuspect,
    mai_os:           MaiOs,
    mai_policy_score: MaiPolicyScore,
    mai_pulevel:      MaiPulevel,
    mai_city:         MaiCity,
    mai_region:       MaiRegion,
    maibis_score:     MaibisScore,
    maibis_reason:    MaibisReason,
    maitris_score:    MaitrisScore,
  )
  case class OnlineFields(
    online_airport_state: OnlineAirportState,
    online_billing_address_state: OnlineBillingAddressState,
    online_cep_number_bond: OnlineCepNumberBond,
    online_city_bond: OnlineCityBond,
    online_ddd:       OnlineDdd,
    online_ddd_bond:  OnlineDddBond,
    online_death:     OnlineDeath,
    online_email:     OnlineEmail,
    online_family_bond: OnlineFamilyBond,
    online_ip_state:  OnlineIpState,
    online_name:      OnlineName,
    online_phone:     OnlinePhone,
    online_queries:   OnlineQueries,
    online_state_bond: OnlineStateBond,
  )
  case class PaymentFields(
    billingCountryCode: BillingCountryCode,
    cancelled:        Cancelled,
    cardCountryCode:  CardCountryCode,
    totalUsdAmount:   TotalUsdAmount,
    paymentsCardType: PaymentsCardType,
    paymentsInstallments: PaymentsInstallments,
  )
  case class OtherFields(
    caseDate:         CaseDate,
    case_minutes_distance: CaseMinutesDistance,
    cases_count:      CasesCount,
    channel:          Channel,
    correl_id:        CorrelId,
    count_different_cards: CountDifferentCards,
    count_different_installments: CountDifferentInstallments,
    domain_proc:      DomainProc,
    eulerBadge:       EulerBadge,
    friendly:         Friendly,
    hours_since_first_verification: HoursSinceFirstVerification,
    many_holders_for_card: ManyHoldersForCard,
    many_names_for_document: ManyNamesForDocument,
    same_field_features: SameFieldFeatures,
  )
  case class Label(
    apocrypha:        Apocrypha
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
