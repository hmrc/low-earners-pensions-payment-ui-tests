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
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import org.scalatest.matchers.should.Matchers.shouldBe
import org.slf4j.LoggerFactory

object BARSLockOutPage extends BasePage {
  private val pageHeadingLocator: By      = By.cssSelector("h1.govuk-heading-l")
  private val lockoutTextLocator: By      = By.id("lockout")
  private val returnToPaymentsLocator: By = By.id("return-to")

  // Initialize your class logger
  private val logger = LoggerFactory.getLogger(getClass.getName)

  override def checkJourneyUrl(url: String = "bank-details-lockout"): Unit =
    super.checkJourneyUrl(url)

  def pageHeadingText: String = getText(pageHeadingLocator).trim
  def lockoutText: String     = getText(lockoutTextLocator).trim
  def returnToPayments: Unit  = click(returnToPaymentsLocator)

  def verifyLockoutTimeIs24HoursInFuture(): Unit = {
    // 1. Capture the current time zone (HMRC services typically run on London time)
    val currentDateTime = ZonedDateTime.now(java.time.ZoneId.of("Europe/London"))
    val futureDateTime  = currentDateTime.plusHours(24)

    logger.info(s"[BARS LOCKOUT] Test Run Timestamp baseline: $currentDateTime")

    // 2. Format it to match the DOM style exactly: "h:mm a 'on' EEEE d MMMM yyyy"
    // Note: '.withLowerCell()' makes 'PM/AM' lowercase to match '1:06pm'
    val pattern   = "HH:mm 'on' EEEE d MMMM yyyy"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.UK)

    val expectedTimestamp = futureDateTime.format(formatter).toLowerCase()

    // 3. Get actual UI text and lower-case it to keep comparisons safe
    val actualUiText = lockoutText.toLowerCase()

    // 4. Assert that the generated 24-hour timestamp exists in the string
    actualUiText.contains(expectedTimestamp) shouldBe true
  }
}
