package sfps.schema

import cats.Applicative
import cats.effect.Sync
import io.circe.generic.semiauto.deriveDecoder
import io.circe.{Decoder, Encoder, Json}
import org.http4s.circe.{jsonEncoderOf, jsonOf}
import org.http4s.{EntityDecoder, EntityEncoder}
import sfps.db.Schema
import sfps.db.Schema._
import sfps.types._

//Simple row to be parsed automatically by circe.
case class RowDTO(
   id:                                     Option[Int],
   DeviceMatch:                            Option[Int],
   FactorCodes:                            Option[Int],
   FirstEncounter:                         Option[Int],
   IcAddress:                              Option[Int],
   IcInternet:                             Option[Int],
   IcSuspicious:                           Option[Int],
   IcVelocity:                             Option[Int],
   Icidentity:                             Option[Int],
   IpRoutingMethod:                        Option[Int],
   ReasonCode:                             Option[Int],
   TimeOnPage:                             Option[Double],
   pp_1:                                   Option[Int],
   pp_30:                                  Option[Int],
   pp_60:                                  Option[Int],
   pp_90:                                  Option[Int],
   countryCode:                            Option[String],
   countryFrom:                            Option[String],
   countryTo:                              Option[String],
   distance_to_arrival:                    Option[Double],
   distance_to_departure:                  Option[Double],
   iataFrom:                               Option[String],
   iataTo:                                 Option[String],
   ip_city:                                Option[String],
   lagTimeHours:                           Option[Int],
   speed_to_departure:                     Option[Double],
   triangulation_height:                   Option[Double],
   triangulation_height_speed:             Option[Double],
   trip_distance:                          Option[Double],
   mai_score:                              Option[Int],
   mai_advice:                             Option[Int],
   mai_verification:                       Option[String],
   mai_reason:                             Option[Int],
   mai_risk:                               Option[Int],
   mai_status:                             Option[Int],
   mai_unique:                             Option[Int],
   mai_avg_secs:                           Option[Int],
   mai_buys:                               Option[Int],
   mai_searches:                           Option[Int],
   mai_pax:                                Option[String],
   mai_type:                               Option[String],
   mai_rels:                               Option[String],
   mai_app:                                Option[Int],
   mai_urgency:                            Option[String],
   mai_network:                            Option[String],
   mai_all_pax:                            Option[Int],
   mai_last_secs:                          Option[Int],
   mai_language:                           Option[String],
   mai_negative:                           Option[Int],
   mai_suspect:                            Option[Int],
   mai_os:                                 Option[String],
   mai_policy_score:                       Option[Int],
   mai_pulevel:                            Option[Int],
   mai_city:                               Option[String],
   mai_region:                             Option[String],
   maibis_score:                           Option[Int],
   maibis_reason:                          Option[String],
   maitris_score:                          Option[Int],
   online_airport_state:                   Option[Int],
   online_billing_address_state:           Option[Int],
   online_cep_number_bond:                 Option[Int],
   online_city_bond:                       Option[Int],
   online_ddd:                             Option[Int],
   online_ddd_bond:                        Option[Int],
   online_death:                           Option[Int],
   online_email:                           Option[Int],
   online_family_bond:                     Option[Int],
   online_ip_state:                        Option[Int],
   online_name:                            Option[Int],
   online_phone:                           Option[Int],
   online_queries:                         Option[Int],
   online_state_bond:                      Option[Int],
   billingCountryCode:                     Option[String],
   cancelled:                              Option[Int],
   cardCountryCode:                        Option[String],
   totalUsdAmount:                         Option[Double],
   paymentsCardType:                       Option[String],
   paymentsInstallments:                   Option[Int],
   caseDate:                               Option[String],
   case_minutes_distance:                  Option[Int],
   cases_count:                            Option[Int],
   channel:                                Option[String],
   correl_id:                              Option[Int],
   count_different_cards:                  Option[String],
   count_different_installments:           Option[Int],
   domain_proc:                            Option[Double],
   eulerBadge:                             Option[String],
   friendly:                               Option[Int],
   hours_since_first_verification:         Option[Int],
   many_holders_for_card:                  Option[Double],
   many_names_for_document:                Option[Double],
   same_field_features:                    Option[String])

object RowDTO {

  def mapToDataRow(row: RowDTO) : Schema.DataRow = {

    val a = ActivityFields(DeviceMatch(row.DeviceMatch),
                   FactorCodes(row.FactorCodes),
                   FirstEncounter(row.FirstEncounter),
                   IcAddress(row.IcAddress),
                   IcInternet(row.IcInternet),
                   IcSuspicious(row.IcSuspicious),
                   IcVelocity(row.IcVelocity),
                   IcIdentity(row.Icidentity),
                   IpRoutingMethod(row.IpRoutingMethod),
                   ReasonCode(row.ReasonCode),
                   TimeOnPage(row.TimeOnPage.map(x => x.toInt)),
                   Pp1(row.pp_1),
                   Pp30(row.pp_30),
                   Pp60(row.pp_60),
                   Pp90(row.pp_90))


    val t = TripFields(CountryCode(row.countryCode),
      CountryFrom(row.countryFrom),
      CountryTo(row.countryTo),
      DistanceToArrival(row.distance_to_arrival),
      DistanceToDeparture(row.distance_to_departure),
      IataFrom(row.iataFrom),
      IataTo(row.iataTo),
      IpCity(row.ip_city),
      LagTimeHours(row.lagTimeHours),
      SpeedToDeparture(row.speed_to_departure),
      TriangulationHeight(row.triangulation_height),
      TriangulationHeightSpeed(row.triangulation_height_speed),
      TripDistance(row.trip_distance))

    val m1 = MaiFields1(MaiScore(row.mai_score),
      MaiAdvice(row.mai_advice),
      MaiVerification(row.mai_verification),
      MaiReason(row.mai_reason),
      MaiRisk(row.mai_risk),
      MaiStatus(row.mai_status),
      MaiUnique(row.mai_unique),
      MaiAvgSecs(row.mai_avg_secs),
      MaiBuys(row.mai_buys),
      MaiSearches(row.mai_searches),
      MaiPax(row.mai_pax),
      MaiType(row.mai_type),
      MaiRels(row.mai_rels),
      MaiApp(row.mai_app))

    val m2 = MaiFields2(MaiUrgency(row.mai_urgency),
      MaiNetwork(row.mai_network),
      MaiAllPax(row.mai_all_pax),
      MaiLastSecs(row.mai_last_secs),
      MaiLanguage(row.mai_language),
      MaiNegative(row.mai_negative),
      MaiSuspect(row.mai_suspect),
      MaiOs(row.mai_os),
      MaiPolicyScore(row.mai_policy_score),
      MaiPulevel(row.mai_pulevel),
      MaiCity(row.mai_city),
      MaiRegion(row.mai_region),
      MaibisScore(row.maibis_score),
      MaibisReason(row.maibis_reason),
      MaitrisScore(row.maitris_score))

    val o1 = OnlineFields(
      OnlineAirportState(row.online_airport_state),
      OnlineBillingAddressState(row.online_billing_address_state),
      OnlineCepNumberBond(row.online_cep_number_bond),
      OnlineCityBond(row.online_city_bond),
      OnlineDdd(row.online_ddd),
      OnlineDddBond(row.online_ddd_bond),
      OnlineDeath(row.online_death),
      OnlineEmail(row.online_email),
      OnlineFamilyBond(row.online_family_bond),
      OnlineIpState(row.online_ip_state),
      OnlineName(row.online_name),
      OnlinePhone(row.online_phone),
      OnlineQueries(row.online_queries),
      OnlineStateBond(row.online_state_bond)
    )

    val p = PaymentFields(BillingCountryCode(row.billingCountryCode),
      Cancelled(row.cancelled),
      CardCountryCode(row.cardCountryCode),
      TotalUsdAmount(row.totalUsdAmount),
      PaymentsCardType(row.paymentsCardType),
      PaymentsInstallments(row.paymentsInstallments))

    val o2 = OtherFields(CaseDate(row.caseDate),
      CaseMinutesDistance(row.case_minutes_distance),
      CasesCount(row.cases_count),
      Channel(row.channel),
      CorrelId(row.correl_id),
      CountDifferentCards(row.count_different_cards),
      CountDifferentInstallments(row.count_different_installments),
      DomainProc(row.domain_proc),
      EulerBadge(row.eulerBadge),
      Friendly(row.friendly),
      HoursSinceFirstVerification(row.hours_since_first_verification),
      ManyHoldersForCard(row.many_holders_for_card),
      ManyNamesForDocument(row.many_names_for_document),
      SameFieldFeatures(row.same_field_features))

    new Schema.DataRow(RowId(0), a, t, m1, m2, o1, p, o2, Label(Apocrypha(Some(0))))

  }

  implicit val myRowDTOEnconder: Encoder[sfps.schema.RowDTO] = new Encoder[sfps.schema.RowDTO] {
    final def apply(a: RowDTO): Json = Json.obj(
      //TODO : serialization may not be necesary
    )
  }


  implicit val myRowDTODecoder: Decoder[sfps.schema.RowDTO] = {
    deriveDecoder[sfps.schema.RowDTO]
  }

  implicit def myRowDTOEntityDecoder[F[_]: Sync]: EntityDecoder[F, sfps.schema.RowDTO] =
    jsonOf

  implicit def myRowDTOEntityEncoder[F[_]: Applicative]: EntityEncoder[F, sfps.schema.RowDTO] =
    jsonEncoderOf[F, sfps.schema.RowDTO]

}
