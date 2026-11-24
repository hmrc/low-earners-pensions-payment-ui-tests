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
import org.scalatest.Assertion

import scala.jdk.CollectionConverters.*
import java.time.{LocalDate, LocalTime, ZoneId}
import java.time.format.{DateTimeFormatter, DateTimeParseException}
import java.util.Locale
import scala.util.matching.Regex
import org.scalatest.Assertions.{fail, succeed}

object ConfirmationPage extends BasePage {

  // Headers
  private val pageHeadingLocator: By       = By.cssSelector("main h1")
  private val failedHeadingLocator: By     = By.xpath("//h2[normalize-space(.)='Failed payments']")
  private val successfulHeadingLocator: By = By.xpath("//h2[normalize-space(.)='Successful payments']")
  private val nextStepsHeadingLocator: By  = By.xpath("//h2[normalize-space(.)='What you can do next']")
  private val submittedPattern: Regex      =
    """^Submitted on (\d{1,2} [A-Z][a-z]+ \d{4}) at (\d{2}:\d{2})$""".r

  // Paragraphs (p.govuk-body collection)
  private val bodyParagraphsLocator: By = By.cssSelector("p.govuk-body")

  // Table locators
  private val failedTableRowsLocator: By     = By.cssSelector("#confirmation_table_available_payments tbody tr")
  private val successfulTableRowsLocator: By = By.cssSelector("#confirmation_table_accepted_payments tbody tr")

  private val submittedTextLocator: By        =
    By.xpath("//p[contains(@class,'govuk-body') and contains(normalize-space(.), 'Submitted on ')]")
  private val printThisPageLinkLocator: By    =
    By.cssSelector("a[data-module='hmrc-print-link']")
  private val viewYourPaymentsLinkLocator: By =
    By.cssSelector("a.govuk-link[href*='/accept-your-low-earners-pension-payment/payments']")
  private val feedbackLinkLocator: By         =
    By.xpath(
      "//a[contains(@class,'govuk-link') and contains(normalize-space(.), 'Tell us what you think of this service')]"
    )

  // Getter methods
  def pageHeadingText: String               = getText(pageHeadingLocator).trim
  def failedPaymentsHeadingText: String     = getText(failedHeadingLocator)
  def successfulPaymentsHeadingText: String = getText(successfulHeadingLocator)
  def whatYouCanDoNextHeadingText: String   = getText(nextStepsHeadingLocator)

  def getBodyParagraphText(index: Int): String =
    driver.findElements(bodyParagraphsLocator).asScala(index).getText.trim

  def failedPaymentsTableRows: List[List[String]] = getTableRows(failedTableRowsLocator)

  def failedPaymentsTaxYear(rowIndex: Int): String        = failedPaymentsTableRows(rowIndex)(0)
  def failedPaymentsAvailableUntil(rowIndex: Int): String = failedPaymentsTableRows(rowIndex)(1)
  def failedPaymentsAmount(rowIndex: Int): String         = failedPaymentsTableRows(rowIndex)(2)

  def failedPaymentsRowCount: Int =
    getTableRowCount(failedTableRowsLocator)

  def successfulPaymentsTableRows: List[List[String]] = getTableRows(successfulTableRowsLocator)

  def successfulPaymentsTaxYear(rowIndex: Int): String = successfulPaymentsTableRows(rowIndex)(0)
  def successfulPaymentsAmount(rowIndex: Int): String  = successfulPaymentsTableRows(rowIndex)(1)

  def successfulPaymentsRowCount: Int =
    getTableRowCount(successfulTableRowsLocator)

  def submittedText: String = getText(submittedTextLocator).trim

  def printThisPageText: String = getText(printThisPageLinkLocator).trim

  def printThisPageHref: String = getLinkHref(printThisPageLinkLocator)

  def viewYourPaymentsText: String = getText(viewYourPaymentsLinkLocator).trim

  def viewYourPaymentsHref: String = getLinkHref(viewYourPaymentsLinkLocator)

  def feedbackLinkText: String = getText(feedbackLinkLocator).trim

  def feedbackLinkHref: String = getLinkHref(feedbackLinkLocator)

  def verifySubmittedDateTimeFormat(actualSubmitted: String): Assertion =
    actualSubmitted match {
      case submittedPattern(datePart, timePart) =>
        val dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.UK)
        val parsedDate    = LocalDate.parse(datePart, dateFormatter)
        val expectedDate  = LocalDate.now(ZoneId.of("Europe/London"))

        if (parsedDate != expectedDate) {
          fail(s"Expected today's date ($expectedDate), got $parsedDate")
        }

        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.UK)
        try
          LocalTime.parse(timePart, timeFormatter)
        catch {
          case _: DateTimeParseException =>
            fail(s"Expected valid HH:mm (24-hour) time, got '$timePart'")
        }

        succeed
      case other                                =>
        fail(s"Unexpected submitted text format: '$other'")
    }

  override def checkJourneyUrl(url: String = "bank-details-received"): Unit =
    super.checkJourneyUrl(url)
}
