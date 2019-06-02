package sfps.etl

import doobie._, doobie.implicits._, doobie.util.ExecutionContexts
import cats._, cats.data._, cats.effect.IO, cats.implicits._

object ETL extends App {

  // TODO: read DB_NAME from some constants / env file
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

  // Debugging only, YOLO mode
  val y = xa.yolo
  import y._

  val columns = """
id,
mai_score,
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
billingCountryCode,
cancelled,
cardCountryCode,
pp_1,
pp_30,
pp_60,
pp_90,
caseDate,
case_minutes_distance,
cases_count,
channel,
correl_id,
count_different_cards,
count_different_installments,
countryCode,
countryFrom,
countryTo,
distance_to_arrival,
distance_to_departure,
domain_proc,
mai_advice,
mai_verification,
mai_reason,
mai_risk"""
// maibis_score"""
// mai_status,
// mai_unique,
// mai_avg_secs"""
// mai_buys,
// mai_searches,
// eulerBadge,
// mai_pax,
// mai_type,
// mai_rels,
// mai_app,
// mai_urgency,
// mai_network,
// mai_all_pax,
// mai_last_secs,

// apocrypha,

// friendly,
// hours_since_first_verification,
// iataFrom,
// iataTo,
// ip_city,
// mai_language,
// mai_negative,
// mai_suspect,
// mai_os,
// mai_policy_score,
// mai_pulevel,
// maibis_reason,
// mai_city,
// mai_region,
// maitris_score,
// lagTimeHours,
// many_holders_for_card,
// many_names_for_document,
// online_airport_state,
// online_billing_address_state,
// online_cep_number_bond,
// online_city_bond,
// online_ddd,
// online_ddd_bond,
// online_death,
// online_email,
// online_family_bond,
// online_ip_state,
// online_name,
// online_phone,
// online_queries,
// online_state_bond,
// paymentsCardType,
// paymentsInstallments,
// same_field_features,
// speed_to_departure,
// totalUsdAmount,
// triangulation_height,
// triangulation_height_speed,
// trip_distance
//   """

  // case class TestRow(apocrypha: Option[Int])
  // sql"SELECT * FROM tabletest".query[TestRow].stream.quick.unsafeRunSync
    //.query[(DataSchema.Label, DataSchema.DataRow)]
  println("")
  (sql"SELECT " ++ Fragment.const0(columns) ++ sql" FROM train")
    .query[DataSchema.DataRow]
    .stream
    .take(1)
    .quick
    .unsafeRunSync

}
