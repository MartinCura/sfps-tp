package sfps.etl

object DataSchema {

  // case class Label(apocrypha: Int)

  case class DataRow(
    id:               Int,
    mai_score:        Option[Int],
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
    billingCountryCode: Option[String],
    cancelled:        Option[Int],
    cardCountryCode:  Option[String],
    pp_1:             Option[Int],
    pp_30:            Option[Int],
    pp_60:            Option[Int],
    pp_90:            Option[Int],
  )
  case class DataRow2(
    caseDate:         Option[String],
    case_minutes_distance: Option[Int],
    cases_count:      Option[Int],
    channel:          Option[String],
    correl_id:        Option[Int],
    count_different_cards: Option[String],
    count_different_installments: Option[Int],
    countryCode:      Option[String],
    countryFrom:      Option[String],
    countryTo:        Option[String],
    distance_to_arrival: Option[Double],
    distance_to_departure: Option[Double],
    domain_proc:      Option[Double],
    mai_advice:       Option[Int],
    mai_verification: Option[String],
    mai_reason:       Option[Int],
    mai_risk:         Option[Int],
    maibis_score:     Option[Int],
    mai_status:       Option[Int],
    mai_unique:       Option[Int],
    mai_avg_secs:     Option[Int],
    mai_buys:         Option[Int],
    mai_searches:     Option[Int],
    eulerBadge:       Option[String],
    mai_pax:          Option[String],
    mai_type:         Option[String],
    mai_rels:         Option[String],
    mai_app:          Option[Int],
    mai_urgency:      Option[String],
    mai_network:      Option[String],
    mai_all_pax:      Option[Int],
    mai_last_secs:    Option[Int],
  )
  case class Label(
    apocrypha:        Int,  // TODO: Should be Option[Int] for test table, or just delete it?
  )
  case class DataRow3(
    friendly:         Int,
    hours_since_first_verification: Option[Int],
    iataFrom:         String,
    iataTo:           String,
    ip_city:          Option[String],
    mai_language:     Option[String],
    mai_negative:     Option[Int],
    mai_suspect:      Option[Int],
    mai_os:           Option[String],
    mai_policy_score: Option[Int],
    mai_pulevel:      Option[Int],
    maibis_reason:    Option[String],
    mai_city:         Option[String],
    mai_region:       Option[String],
    maitris_score:    Option[Int],
    lagTimeHours:     Int,
    many_holders_for_card: Option[Double],
    many_names_for_document: Option[Double],
    online_airport_state: Int,
    online_billing_address_state: Int,
    online_cep_number_bond: Int,
  )
  case class DataRow4(
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
    paymentsCardType: Option[String],
    paymentsInstallments: Int,
    same_field_features: Option[String],
    speed_to_departure: Option[Double],
    totalUsdAmount:   Double,
    triangulation_height: Option[Double],
    triangulation_height_speed: Option[Double],
    trip_distance:    Option[Double]
  )

}
