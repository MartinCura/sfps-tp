import doobie._, doobie.implicits._, doobie.util.ExecutionContexts
import cats._, cats.data._, cats.effect.IO, cats.implicits._
import scala.io.Source
import com.github.tototoshi.csv.CSVReader
import java.util.NoSuchElementException

object DBLoader extends App {

  // TODO: read from .env
  lazy val DB_NAME = "sfps_db"

  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)
  val xa = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver",
    s"jdbc:postgresql://localhost:5442/$DB_NAME",
    "postgres",
    "",
    ExecutionContexts.synchronous
  )

  val dropTrain =
    sql"""
      DROP TABLE IF EXISTS train
    """.update.run

  // TODO: Put SQL statements or table format in own file
  val createTrain =
    sql"""
      CREATE TABLE train (
        id              SERIAL,
        mai_score       INTEGER,
        DeviceMatch     INTEGER,
        FactorCodes     INTEGER,
        FirstEncounter  INTEGER,
        IcAddress       INTEGER,
        IcInternet      INTEGER,
        IcSuspicious    INTEGER,
        IcVelocity      INTEGER,
        Icidentity      INTEGER,
        IpRoutingMethod INTEGER,
        ReasonCode      INTEGER,
        TimeOnPage      INTEGER,
        billingCountryCode VARCHAR,
        cancelled       INTEGER,
        cardCountryCode VARCHAR,
        pp_1            INTEGER,
        pp_30           INTEGER,
        pp_60           INTEGER,
        pp_90           INTEGER,
        caseDate        VARCHAR,
        case_minutes_distance    INTEGER,
        cases_count    INTEGER,
        channel    VARCHAR,
        correl_id    INTEGER,
        count_different_cards    VARCHAR,
        count_different_installments    INTEGER,
        countryCode    VARCHAR,
        countryFrom    VARCHAR,
        countryTo    VARCHAR,
        distance_to_arrival    DECIMAL(8,1),
        distance_to_departure    DECIMAL(8,1),
        domain_proc    DECIMAL(5,4),
        mai_advice    INTEGER,
        mai_verification    VARCHAR,
        mai_reason    INTEGER,
        mai_risk    INTEGER,
        maibis_score    INTEGER,
        mai_status    INTEGER,
        mai_unique    INTEGER,
        mai_avg_secs    INTEGER,
        mai_buys    INTEGER,
        mai_searches    INTEGER,
        eulerBadge    VARCHAR,
        mai_pax    VARCHAR,
        mai_type    VARCHAR,
        mai_rels    VARCHAR,
        mai_app    INTEGER,
        mai_urgency    VARCHAR,
        mai_network    VARCHAR,
        mai_all_pax    INTEGER,
        mai_last_secs    INTEGER,
        APOCRYPHA    INTEGER,
        friendly    INTEGER,
        hours_since_first_verification    INTEGER,
        iataFrom    VARCHAR,
        iataTo    VARCHAR,
        ip_city    VARCHAR,
        mai_language    VARCHAR,
        mai_negative    INTEGER,
        mai_suspect    INTEGER,
        mai_os    VARCHAR,
        mai_policy_score    INTEGER,
        mai_pulevel    INTEGER,
        maibis_reason    VARCHAR,
        mai_city    VARCHAR,
        mai_region    VARCHAR,
        maitris_score    INTEGER,
        lagTimeHours    INTEGER,
        many_holders_for_card    DECIMAL(3,2),
        many_names_for_document    DECIMAL(3,2),
        online_airport_state    INTEGER,
        online_billing_address_state    INTEGER,
        online_cep_number_bond    INTEGER,
        online_city_bond    INTEGER,
        online_ddd    INTEGER,
        online_ddd_bond    INTEGER,
        online_death    INTEGER,
        online_email    INTEGER,
        online_family_bond    INTEGER,
        online_ip_state    INTEGER,
        online_name    INTEGER,
        online_phone    INTEGER,
        online_queries    INTEGER,
        online_state_bond    INTEGER,
        paymentsCardType    VARCHAR,
        paymentsInstallments    INTEGER,
        same_field_features    VARCHAR,
        speed_to_departure    DECIMAL(8,1),
        totalUsdAmount    DECIMAL(8,2),
        triangulation_height    DECIMAL(8,2),
        triangulation_height_speed    DECIMAL(8,2),
        trip_distance    DECIMAL(8,2)
      )
    """.update.run

  // Create train table
  // (dropTrain, createTrain).mapN(_ + _).transact(xa).unsafeRunSync

  // def insert1(tablename: String, keys: String, row: String): Update0 =
  def insert1(tablename: String, keys: String, row: String) : Update0 =
    // println(sql"""
    //   INSERT INTO $tablename (${keys})
    //   VALUES (${row})
    // """.toString)
    (
      sql"INSERT INTO " ++ Fragment.const(tablename) ++ Fragments.parentheses(Fragment.const0(keys)) ++ sql"""
          VALUES """ ++ Fragments.parentheses(Fragment.const0(row))
    ).update

  val filename = "../../train.csv"
  // for (line <- Source.fromFile(filename).getLines.drop(1).take(1)) {}

  // Numbers go plain, empty values are NULL, and any other strings is surrounded by ''
  def formatStringForSql(s: String): String =
    try {
      s.toDouble
      return s
    } catch {
      case ex: NumberFormatException => {
        s match {
          case "" => "NULL"
          case "\"\"" => "''"
          case _ => s"'${s.replace("'", "\"")}'"
        }
      }
    }

  // val str1: String = "iataFrom,online_airport_state,mai_rels,channel,IcAddress,count_different_installments,pp_1,triangulation_height,ip_city,mai_region,trip_distance,maitris_score,mai_pax,count_different_cards,online_death,countryTo,mai_reason,online_email,mai_all_pax,cases_count,maibis_reason,online_queries,online_phone,mai_unique,IcVelocity,caseDate,online_ip_state,IcInternet,distance_to_departure,mai_score,online_family_bond,mai_advice,online_city_bond,online_name,mai_last_secs,lagTimeHours,many_holders_for_card,hours_since_first_verification,cancelled,mai_app,online_cep_number_bond,cardCountryCode,iataTo,maibis_score,mai_status,mai_type,pp_90,IpRoutingMethod,mai_language,friendly,mai_searches,countryCode,pp_60,DeviceMatch,online_billing_address_state,online_ddd,speed_to_departure,mai_urgency,mai_negative,TimeOnPage,mai_network,countryFrom,mai_os,case_minutes_distance,pp_30,totalUsdAmount,same_field_features,domain_proc,Icidentity,mai_suspect,ReasonCode,correl_id,mai_avg_secs,many_names_for_document,APOCRYPHA,mai_pulevel,paymentsInstallments,mai_buys,mai_policy_score,eulerBadge,online_state_bond,mai_verification,FirstEncounter,FactorCodes,IcSuspicious,mai_city,distance_to_arrival,mai_risk,paymentsCardType,billingCountryCode,online_ddd_bond,triangulation_height_speed"
  // sql"""INSERT INTO train ()
  // VALUES ('LUCYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY',0,NULL,'travel-agency-whitelabel',0,NULL,1,2778.47,'CERLJ',NULL,9931.5,0,NULL,NULL,0,'NG',63,0,NULL,1,'Anonymous Proxy',0,0,1,0,'2017-02-14T15:35:26Z',0,0,7680.8,99,0,1,0,0,NULL,52,NULL,3192,1,0,0,'US','LOS',890,2,NULL,5,0,'en-GB,en-US;q',0,NULL,'BR',4,0,0,0,147.7,'INACTIVE',1,NULL,'DESPEGAR','BR','Windows',NULL,4,1797,'{u"same_phone": {}, u"same_ip": {}, u"same_document_number": {}, u"same_email": {u"fraud_min_days": 3, u"cancelled_max_days": 18, u"in_progress_max_days": 66, u"fraud_max_days": 66, u"cancelled_counter": 4, u"in_progress_min_days": 66, u"in_progress_counter": 1, u"cancelled_min_days": 3, u"fraud_counter": 5}, u"same_card": {}, u"same_smart_id": {u"fraud_min_days": 3, u"cancelled_max_days": 3, u"fraud_max_days": 3, u"cancelled_counter": 1, u"cancelled_min_days": 3, u"fraud_counter": 1}, u"same_device_id": {}, u"same_tracker_id": {}}',0.0123,0,0,481,0,NULL,NULL,1,0,1,NULL,-10,'CHECKER',0,'2016-10-04T15:28:09Z',0,0,0,NULL,3924,5,'AX',NULL,0,53.4)
  // """.update.run.transact(xa).unsafeRunSync
  // println("hecho esto")

  // def sqlStatement(s: String): Update0 =
  //   HC.stream[Any](s, ().pure[PreparedStatementIO], 512)

  def addLineToDB(line: Map[String, String]) = {
    println(line)
    val k = line.keys.reduce(_ + "," + _)
    val v = line.values.map(formatStringForSql(_)).reduce(_ + "," + _)
    println(k)
    println(v)
//     val s = s"""INSERT INTO ${"train"} ($k)
// VALUES (${v});"""
    // println(s)
    // val k = "iataFrom,online_airport_state,mai_rels,channel,IcAddress,count_different_installments,pp_1,triangulation_height,ip_city,mai_region,trip_distance,maitris_score,mai_pax,count_different_cards,online_death,countryTo,mai_reason,online_email,mai_all_pax,cases_count,maibis_reason,online_queries,online_phone,mai_unique,IcVelocity,caseDate,online_ip_state,IcInternet,distance_to_departure,mai_score,online_family_bond,mai_advice,online_city_bond,online_name,mai_last_secs,lagTimeHours,many_holders_for_card,hours_since_first_verification,cancelled,mai_app,online_cep_number_bond,cardCountryCode,iataTo,maibis_score,mai_status,mai_type,pp_90,IpRoutingMethod,mai_language,friendly,mai_searches,countryCode,pp_60,DeviceMatch,online_billing_address_state,online_ddd,speed_to_departure,mai_urgency,mai_negative,TimeOnPage,mai_network,countryFrom,mai_os,case_minutes_distance,pp_30,totalUsdAmount,same_field_features,domain_proc,Icidentity,mai_suspect,ReasonCode,correl_id,mai_avg_secs,many_names_for_document,APOCRYPHA,mai_pulevel,paymentsInstallments,mai_buys,mai_policy_score,eulerBadge,online_state_bond,mai_verification,FirstEncounter,FactorCodes,IcSuspicious,mai_city,distance_to_arrival,mai_risk,paymentsCardType,billingCountryCode,online_ddd_bond,triangulation_height_speed"
    // val v = "'ARG',0,NULL,'travel-agency-whitelabel',0,NULL,1,2778.47,'CERLJ',NULL,9931.5,0,NULL,NULL,0,'NG',63,0,NULL,1,'Anonymous Proxy',0,0,1,0,'2017-02-14T15:35:26Z',0,0,7680.8,99,0,1,0,0,NULL,52,NULL,3192,1,0,0,'US','LOS',890,2,NULL,5,0,'en-GB,en-US;q',0,NULL,'BR',4,0,0,0,147.7,'INACTIVE',1,NULL,'DESPEGAR','BR','Windows',NULL,4,1797,'{u\"same_phone\": {}, u\"same_ip\": {}, u\"same_document_number\": {}, u\"same_email\": {u\"fraud_min_days\": 3, u\"cancelled_max_days\": 18, u\"in_progress_max_days\": 66, u\"fraud_max_days\": 66, u\"cancelled_counter\": 4, u\"in_progress_min_days\": 66, u\"in_progress_counter\": 1, u\"cancelled_min_days\": 3, u\"fraud_counter\": 5}, u\"same_card\": {}, u\"same_smart_id\": {u\"fraud_min_days\": 3, u\"cancelled_max_days\": 3, u\"fraud_max_days\": 3, u\"cancelled_counter\": 1, u\"cancelled_min_days\": 3, u\"fraud_counter\": 1}, u\"same_device_id\": {}, u\"same_tracker_id\": {}}',0.0123,0,0,481,0,NULL,NULL,1,0,1,NULL,-10,'CHECKER',0,'2016-10-04T15:28:09Z',0,0,0,NULL,3924,5,'AX',NULL,0,53.4"
    insert1("train", k, v).run.transact(xa).unsafeRunSync
    print('.')
  }

  val reader = CSVReader.open(filename)
  val it = reader.iteratorWithHeaders.take(5)
  try {
    while (true) {
      addLineToDB(it.next)
    }
  } catch {
    case e: java.util.NoSuchElementException => println("EOF")
  }
  reader.close()
}
