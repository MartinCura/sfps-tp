package sfps.dbloader

import doobie._, doobie.implicits._, doobie.util.ExecutionContexts

object SqlCommands {

  val dropTrain =
    sql"""
      DROP TABLE IF EXISTS train
    """.update.run

  val dropTest =
    sql"""
      DROP TABLE IF EXISTS test
    """.update.run

  val commonColumns =
    """
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
    """

  val createTrain =
    (
      sql"CREATE TABLE train " ++ Fragments.parentheses(
        Fragment.const(commonColumns) ++
        Fragment.const(", APOCRYPHA    INTEGER")
      )
    ).update.run

  val createTest =
    (
      sql"CREATE TABLE test " ++ Fragments.parentheses(
        Fragment.const(commonColumns) ++
        Fragment.const(", APOCRYPHA    INTEGER")
      )
    ).update.run

}
