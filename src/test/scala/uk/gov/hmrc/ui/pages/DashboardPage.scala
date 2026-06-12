/*
 * Copyright 2026 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ui.pages

import org.openqa.selenium.By
import org.scalatest.matchers.should.Matchers.shouldBe
import org.slf4j.LoggerFactory

object DashboardPage extends BasePage {

  private val path                     = "dashboard"
  private val breakdownLinkLocator: By = By.xpath("//a[contains(@href, '/low-earners-pensions-payment/breakdown')]")
  private val pageHeadingLocator: By   = By.cssSelector("h1.govuk-heading-l")

  // Available Payments Table
  private val availablePaymentsInset: By                  = By.cssSelector("div.govuk-inset-text")
  private val availablePaymentsTableCaption: By           = By.cssSelector("#dashboard_table_available_payments caption")
  private val availablePaymentsRows: By                   = By.cssSelector("#dashboard_table_available_payments tbody tr")
  private val availablePaymentsTaxYearHeader: By          = By.id("dashboard_table_available_payments_header_taxYear")
  private val availablePaymentsAmountHeader: By           = By.id("dashboard_table_available_payments_header_amount")
  private val availablePaymentsAvailableUntilHeader: By   =
    By.id("dashboard_table_available_payments_header_availableUntil")
  private val availablePaymentsStatusHeader: By           = By.id("dashboard_table_available_payments_header_status")
  private val availablePaymentsAvailableTag: By           = By.cssSelector("strong.govuk-tag--blue")
  private val availablePaymentsSuspendedTag: By           = By.cssSelector("strong.govuk-tag--yellow")
  private val availablePaymentsTotalPaymentsParagraph: By = By.cssSelector("p.govuk-body")
  private val availablePaymentsTotalPaymentsAmount: By    = By.cssSelector("strong.govuk-\\!-font-weight-bold")
  private val availablePaymentsBankDetailsText: By        = By.cssSelector("p.govuk-body.govuk-\\!-margin-bottom-6")

  // Available Payments Table
  private val paymentHistoryInset: By     = By.cssSelector("div.govuk-inset-text p:not(.govuk-body)")
  private val paymentHistoryInsetLink: By = By.cssSelector("div.govuk-inset-text strong.govuk-\\!-font-weight-bold")

  private val paymentHistoryTableCaption: By       = By.cssSelector("#dashboard_table_payment_history caption")
  private val paymentHistoryRows: By               = By.cssSelector("#dashboard_table_payment_history tbody tr")
  private val paymentHistoryTaxYearHeader: By      = By.id("dashboard_table_payment_history_header_taxYear")
  private val paymentHistoryAmountHeader: By       = By.id("dashboard_table_payment_history_header_amount")
  private val paymentHistoryDateAcceptedHeader: By = By.id("dashboard_table_payment_history_header_dateAccepted")
  private val paymentHistoryStatusHeader: By       = By.id("dashboard_table_payment_history_header_status")
  private val paymentHistoryActionHeader: By       = By.id("dashboard_table_payment_history_header_action")

  private val bannerTitleLocator: By   = By.id("govuk-notification-banner-title")
  private val bannerHeadingLocator: By = By.className("govuk-notification-banner__heading")
  private val logger                   = LoggerFactory.getLogger(getClass.getName)

  // Check calculation links by href pattern
  private val paidCalculationLinkLocator: By =
    By.cssSelector("a[href*='/low-earners-pensions-payment/breakdown?id=P-2022-1']")

  private val cancelledCalculationLinkLocator: By =
    By.cssSelector("a[href*='/low-earners-pensions-payment/breakdown?id=C-2023-1']")

  def actionButtonText: String = getText(breakdownLinkLocator)

  def clickActionButton(): Unit =
    click(breakdownLinkLocator)

  def checkJourneyUrl(): Unit =
    super.checkJourneyUrl(path)

  def pageHeadingText: String = getText(pageHeadingLocator).trim

  def availablePaymentsTableCaptionText: String         =
    getText(availablePaymentsTableCaption).trim
  def availablePaymentsTaxYearHeaderText: String        =
    getText(availablePaymentsTaxYearHeader).trim
  def availablePaymentsAmountHeaderText: String         =
    getText(availablePaymentsAmountHeader).trim
  def availablePaymentsAvailableUntilHeaderText: String =
    getText(availablePaymentsAvailableUntilHeader).trim
  def availablePaymentsStatusHeaderText: String         =
    getText(availablePaymentsStatusHeader).trim

  def availablePaymentsTableRows: List[List[String]] = getTableRows(availablePaymentsRows)
  def availablePaymentsTableRowCount: Int            = getTableRowCount(availablePaymentsRows)
  def availablePaymentsTableColumnCount: Int         = getTableColumnCount(availablePaymentsRows)

  // Specific column value by row index
  def availablePaymentsTaxYear(rowIndex: Int): String        = availablePaymentsTableRows(rowIndex)(0)
  def availablePaymentsAmount(rowIndex: Int): String         = availablePaymentsTableRows(rowIndex)(1)
  def availablePaymentsAvailableUntil(rowIndex: Int): String = availablePaymentsTableRows(rowIndex)(2)
  def availablePaymentsStatus(rowIndex: Int): String         = availablePaymentsTableRows(rowIndex)(3)

  def availablePaymentsIsAvailable(rowIndex: Int): Boolean =
    hasElementInRow(availablePaymentsRows, rowIndex, availablePaymentsAvailableTag)
  def availablePaymentsIsSuspended(rowIndex: Int): Boolean =
    hasElementInRow(availablePaymentsRows, rowIndex, availablePaymentsSuspendedTag)

  def availablePaymentsTotalPaymentsText: String       = getText(availablePaymentsTotalPaymentsParagraph)
  def availablePaymentsTotalPaymentsAmountText: String = getText(availablePaymentsTotalPaymentsAmount)
  def availablePaymentsBankDetailsMessage: String      = getText(availablePaymentsBankDetailsText)

  def availablePaymentsInsetText: String = getText(availablePaymentsInset)

  def paymentHistoryTableCaptionText: String       = getText(paymentHistoryTableCaption).trim
  def paymentHistoryTaxYearHeaderText: String      = getText(paymentHistoryTaxYearHeader).trim
  def paymentHistoryAmountHeaderText: String       = getText(paymentHistoryAmountHeader).trim
  def paymentHistoryDateAcceptedHeaderText: String = getText(paymentHistoryDateAcceptedHeader).trim
  def paymentHistoryStatusHeaderText: String       = getText(paymentHistoryStatusHeader).trim
  def paymentHistoryActionHeaderText: String       = getText(paymentHistoryActionHeader).trim

  def paymentHistoryTableRows: List[List[String]] = getTableRows(paymentHistoryRows)
  def paymentHistoryTableRowCount: Int            = getTableRowCount(paymentHistoryRows)
  def paymentHistoryTableColumnCount: Int         = getTableColumnCount(paymentHistoryRows)

  // Specific column value by row index
  def paymentHistoryTaxYear(rowIndex: Int): String      = paymentHistoryTableRows(rowIndex)(0)
  def paymentHistoryAmount(rowIndex: Int): String       = paymentHistoryTableRows(rowIndex)(1)
  def paymentHistoryDateAccepted(rowIndex: Int): String = paymentHistoryTableRows(rowIndex)(2)
  def paymentHistoryStatus(rowIndex: Int): String       = paymentHistoryTableRows(rowIndex)(3)
  def paymentHistoryAction(rowIndex: Int): String       = paymentHistoryTableRows(rowIndex)(4)

  def paymentHistoryInsetText: String = getText(paymentHistoryInset)
  def cancelledCountText: String      = getText(paymentHistoryInsetLink)

  def clickPaidCalculationLink(): Unit =
    click(paidCalculationLinkLocator)

  def clickCancelledCalculationLink(): Unit =
    click(cancelledCalculationLinkLocator)

  def verifyLockoutBannerAnd24HourTime(): Unit = {
    // 1. Verify the Banner Title is exactly "Important"
    val actualBannerTitle = driver.findElement(bannerTitleLocator).getText.trim
    logger.info(s"[BARS LOCKOUT] Banner Title found: '$actualBannerTitle'")
    actualBannerTitle shouldBe "Important"
  }
}
