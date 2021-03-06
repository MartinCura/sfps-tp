package sfps.types

import sfps.db.{Schema, DoubleTypedDatasetRow}


object Mappers {

    val NONE = -99999999
    def doubleTypedMapper(x: Schema.DataRow) : DoubleTypedDatasetRow = {
        DoubleTypedDatasetRow(
            x._1.id,
            x._2.deviceMatch.toDouble.getOrElse(NONE),
            x._2.factorCodes.toDouble.getOrElse(NONE),
            x._2.firstEncounter.toDouble.getOrElse(NONE),
            x._2.icAddress.toDouble.getOrElse(NONE),
            x._2.icInternet.toDouble.getOrElse(NONE),
            x._2.icSuspicious.toDouble.getOrElse(NONE),
            x._2.icVelocity.toDouble.getOrElse(NONE),
            x._2.icidentity.toDouble.getOrElse(NONE),
            x._2.ipRoutingMethod.toDouble.getOrElse(NONE),
            x._2.reasonCode.toDouble().getOrElse(NONE),
            x._2.timeOnPage.toDouble().getOrElse(NONE),
            x._2.pp_1.toDouble().getOrElse(NONE),
            x._2.pp_30.toDouble().getOrElse(NONE),
            x._2.pp_60.toDouble().getOrElse(NONE),
            x._2.pp_90.toDouble().getOrElse(NONE),
            x._3.countryCode.toDouble.getOrElse(NONE),
            x._3.countryFrom.toDouble().getOrElse(NONE),
            x._3.countryTo.toDouble().getOrElse(NONE),
            x._3.distance_to_arrival.toDouble.getOrElse(NONE),
            x._3.distance_to_departure.toDouble().getOrElse(NONE),
            x._3.iataFrom.toDouble().getOrElse(NONE),
            x._3.iataTo.toDouble().getOrElse(NONE),
            x._3.ip_city.toDouble().getOrElse(NONE),
            x._3.lagTimeHours.toDouble().getOrElse(NONE),
            x._3.speed_to_departure.toDouble().getOrElse(NONE),
            x._3.triangulation_height.toDouble().getOrElse(NONE),
            x._3.triangulation_height_speed.toDouble().getOrElse(NONE),
            x._3.trip_distance.toDouble().getOrElse(NONE),
            x._4.mai_score.toDouble().getOrElse(NONE),
            x._4.mai_advice.toDouble().getOrElse(NONE),
            x._4.mai_verification.toDouble().getOrElse(NONE),
            x._4.mai_reason.toDouble().getOrElse(NONE),
            x._4.mai_risk.toDouble().getOrElse(NONE),
            x._4.mai_status.toDouble().getOrElse(NONE),
            x._4.mai_unique.toDouble().getOrElse(NONE),
            x._4.mai_avg_secs.toDouble().getOrElse(NONE),
            x._4.mai_buys.toDouble().getOrElse(NONE),
            x._4.mai_searches.toDouble().getOrElse(NONE),
            x._4.mai_pax.toDouble().getOrElse(NONE),
            x._4.mai_type.toDouble().getOrElse(NONE),
            x._4.mai_rels.toDouble().getOrElse(NONE),
            x._4.mai_app.toDouble().getOrElse(NONE),
            x._5.mai_urgency.toDouble().getOrElse(NONE),
            x._5.mai_network.toDouble().getOrElse(NONE),
            x._5.mai_all_pax.toDouble().getOrElse(NONE),
            x._5.mai_last_secs.toDouble().getOrElse(NONE),
            x._5.mai_language.toDouble().getOrElse(NONE),
            x._5.mai_negative.toDouble().getOrElse(NONE),
            x._5.mai_suspect.toDouble().getOrElse(NONE),
            x._5.mai_os.toDouble().getOrElse(NONE),
            x._5.mai_policy_score.toDouble().getOrElse(NONE),
            x._5.mai_pulevel.toDouble().getOrElse(NONE),
            x._5.mai_city.toDouble().getOrElse(NONE),
            x._5.mai_region.toDouble().getOrElse(NONE),
            x._5.maibis_score.toDouble().getOrElse(NONE),
            x._5.maibis_reason.toDouble().getOrElse(NONE),
            x._5.maitris_score.toDouble().getOrElse(NONE),
            x._6.online_airport_state.toDouble.getOrElse(NONE),
            x._6.online_billing_address_state.toDouble.getOrElse(NONE),
            x._6.online_cep_number_bond.toDouble.getOrElse(NONE),
            x._6.online_city_bond.toDouble.getOrElse(NONE),
            x._6.online_ddd.toDouble.getOrElse(NONE),
            x._6.online_ddd_bond.toDouble.getOrElse(NONE),
            x._6.online_death.toDouble.getOrElse(NONE),
            x._6.online_email.toDouble.getOrElse(NONE),
            x._6.online_family_bond.toDouble.getOrElse(NONE),
            x._6.online_ip_state.toDouble.getOrElse(NONE),
            x._6.online_name.toDouble.getOrElse(NONE),
            x._6.online_phone.toDouble.getOrElse(NONE),
            x._6.online_queries.toDouble.getOrElse(NONE),
            x._6.online_state_bond.toDouble.getOrElse(NONE),
            x._7.billingCountryCode.toDouble.getOrElse(NONE),
            x._7.cancelled.toDouble.getOrElse(NONE),
            x._7.cardCountryCode.toDouble.getOrElse(NONE),
            x._7.totalUsdAmount.toDouble.getOrElse(NONE),
            x._7.paymentsCardType.toDouble.getOrElse(NONE),
            x._7.paymentsInstallments.toDouble.getOrElse(NONE),
            x._8.caseDate.toDouble.getOrElse(NONE),
            x._8.case_minutes_distance.toDouble.getOrElse(NONE),
            x._8.cases_count.toDouble.getOrElse(NONE),
            x._8.channel.toDouble.getOrElse(NONE),
            x._8.correl_id.toDouble.getOrElse(NONE),
            x._8.count_different_cards.toDouble.getOrElse(NONE),
            x._8.count_different_installments.toDouble.getOrElse(NONE),
            x._8.domain_proc.toDouble.getOrElse(NONE),
            x._8.eulerBadge.toDouble.getOrElse(NONE),
            x._8.friendly.toDouble.getOrElse(NONE),
            x._8.hours_since_first_verification.toDouble.getOrElse(NONE),
            x._8.many_holders_for_card.toDouble.getOrElse(NONE),
            x._8.many_names_for_document.toDouble.getOrElse(NONE),
            x._8.same_field_features.toDouble.getOrElse(NONE),

            x._9.apocrypha.toDouble().getOrElse(NONE)
          )

    }

    def asFeatureList(doubleTypedDatasetRow: DoubleTypedDatasetRow) : List[Double] = {
        List(
            doubleTypedDatasetRow.deviceMatch,
            doubleTypedDatasetRow.factorCodes,
            doubleTypedDatasetRow.firstEncounter,
            doubleTypedDatasetRow.icAddress,
            doubleTypedDatasetRow.icInternet,
            doubleTypedDatasetRow.icSuspicious,
            doubleTypedDatasetRow.icVelocity,
            doubleTypedDatasetRow.icidentity,
            doubleTypedDatasetRow.ipRoutingMethod,
            doubleTypedDatasetRow.reasonCode,
            doubleTypedDatasetRow.timeOnPage,
            doubleTypedDatasetRow.pp_1,
            doubleTypedDatasetRow.pp_30,
            doubleTypedDatasetRow.pp_60,
            doubleTypedDatasetRow.pp_90,
            doubleTypedDatasetRow.countryCode,
            doubleTypedDatasetRow.countryFrom,
            doubleTypedDatasetRow.countryTo,
            doubleTypedDatasetRow.distance_to_arrival,
            doubleTypedDatasetRow.distance_to_departure,
            doubleTypedDatasetRow.iataFrom,
            doubleTypedDatasetRow.iataTo,
            doubleTypedDatasetRow.ip_city,
            doubleTypedDatasetRow.lagTimeHours,
            doubleTypedDatasetRow.speed_to_departure,
            doubleTypedDatasetRow.triangulation_height,
            doubleTypedDatasetRow.triangulation_height_speed,
            doubleTypedDatasetRow.trip_distance,
            doubleTypedDatasetRow.mai_score,
            doubleTypedDatasetRow.mai_advice,
            doubleTypedDatasetRow.mai_verification,
            doubleTypedDatasetRow.mai_reason,
            doubleTypedDatasetRow.mai_risk,
            doubleTypedDatasetRow.mai_status,
            doubleTypedDatasetRow.mai_unique,
            doubleTypedDatasetRow.mai_avg_secs,
            doubleTypedDatasetRow.mai_buys,
            doubleTypedDatasetRow.mai_searches,
            doubleTypedDatasetRow.mai_pax,
            doubleTypedDatasetRow.mai_type,
            doubleTypedDatasetRow.mai_rels,
            doubleTypedDatasetRow.mai_app,
            doubleTypedDatasetRow.mai_urgency,
            doubleTypedDatasetRow.mai_network,
            doubleTypedDatasetRow.mai_all_pax,
            doubleTypedDatasetRow.mai_last_secs,
            doubleTypedDatasetRow.mai_language,
            doubleTypedDatasetRow.mai_negative,
            doubleTypedDatasetRow.mai_suspect,
            doubleTypedDatasetRow.mai_os,
            doubleTypedDatasetRow.mai_policy_score,
            doubleTypedDatasetRow.mai_pulevel,
            doubleTypedDatasetRow.mai_city,
            doubleTypedDatasetRow.mai_region,
            doubleTypedDatasetRow.maibis_score,
            doubleTypedDatasetRow.maibis_reason,
            doubleTypedDatasetRow.maitris_score,
            doubleTypedDatasetRow.online_airport_state,
            doubleTypedDatasetRow.online_billing_address_state,
            doubleTypedDatasetRow.online_cep_number_bond,
            doubleTypedDatasetRow.online_city_bond,
            doubleTypedDatasetRow.online_ddd,
            doubleTypedDatasetRow.online_ddd_bond,
            doubleTypedDatasetRow.online_death,
            doubleTypedDatasetRow.online_email,
            doubleTypedDatasetRow.online_family_bond,
            doubleTypedDatasetRow.online_ip_state,
            doubleTypedDatasetRow.online_name,
            doubleTypedDatasetRow.online_phone,
            doubleTypedDatasetRow.online_queries,
            doubleTypedDatasetRow.online_state_bond,
            doubleTypedDatasetRow.billingCountryCode,
            doubleTypedDatasetRow.cancelled,
            doubleTypedDatasetRow.cardCountryCode,
            doubleTypedDatasetRow.totalUsdAmount,
            doubleTypedDatasetRow.paymentsCardType,
            doubleTypedDatasetRow.paymentsInstallments,
            doubleTypedDatasetRow.caseDate,
            doubleTypedDatasetRow.case_minutes_distance,
            doubleTypedDatasetRow.cases_count,
            doubleTypedDatasetRow.channel,
            doubleTypedDatasetRow.correl_id,
            doubleTypedDatasetRow.count_different_cards,
            doubleTypedDatasetRow.count_different_installments,
            doubleTypedDatasetRow.domain_proc,
            doubleTypedDatasetRow.eulerBadge,
            doubleTypedDatasetRow.friendly,
            doubleTypedDatasetRow.hours_since_first_verification,
            doubleTypedDatasetRow.many_holders_for_card,
            doubleTypedDatasetRow.many_names_for_document,
            doubleTypedDatasetRow.same_field_features)
    }
}
