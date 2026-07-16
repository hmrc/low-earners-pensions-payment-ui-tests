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
import scala.jdk.CollectionConverters.*

object CheckYourAnswersPage extends BasePage {
  // Keys
  private val accountNameKeyLocator: By   =
    By.cssSelector("div.govuk-summary-list__row:nth-child(1) dt.govuk-summary-list__key")
  private val sortCodeKeyLocator: By      =
    By.cssSelector("div.govuk-summary-list__row:nth-child(2) dt.govuk-summary-list__key")
  private val accountNumberKeyLocator: By =
    By.cssSelector("div.govuk-summary-list__row:nth-child(3) dt.govuk-summary-list__key")
  private val rollNumberKeyLocator: By    =
    By.cssSelector("div.govuk-summary-list__row:nth-child(4) dt.govuk-summary-list__key")

  // Values
  private val accountNameValueLocator: By   =
    By.cssSelector("div.govuk-summary-list__row:nth-child(1) dd.govuk-summary-list__value")
  private val sortCodeValueLocator: By      =
    By.cssSelector("div.govuk-summary-list__row:nth-child(2) dd.govuk-summary-list__value")
  private val accountNumberValueLocator: By =
    By.cssSelector("div.govuk-summary-list__row:nth-child(3) dd.govuk-summary-list__value")
  private val rollNumberValueLocator: By    =
    By.cssSelector("div.govuk-summary-list__row:nth-child(4) dd.govuk-summary-list__value")

  // Change links
  private val changeAccountNameLocator: By   = By.id("change-account-name")
  private val changeSortCodeLocator: By      = By.id("change-sort-code")
  private val changeAccountNumberLocator: By = By.id("change-account-number")
  private val changeRollNumberLocator: By    = By.id("change-roll-number")

  private val submitButton: By = By.id("submit")

  def submit(): Unit =
    click(submitButton)

  override def checkJourneyUrl(url: String = "check-your-answers"): Unit =
    super.checkJourneyUrl(url)

  // Key getters
  def accountNameKey: String   = getText(accountNameKeyLocator)
  def sortCodeKey: String      = getText(sortCodeKeyLocator)
  def accountNumberKey: String = getText(accountNumberKeyLocator)

  // Value getters
  def accountNameValue: String   = getText(accountNameValueLocator)
  def sortCodeValue: String      = getText(sortCodeValueLocator)
  def accountNumberValue: String = getText(accountNumberValueLocator)

  // Check presence first using findElements
  def isRollNumberPresent: Boolean =
    driver.findElements(rollNumberKeyLocator).asScala.nonEmpty

  def rollNumberKey: Option[String] =
    if isRollNumberPresent then Some(driver.findElement(rollNumberKeyLocator).getText.trim)
    else None

  def rollNumberValue: Option[String] =
    if isRollNumberPresent then Some(driver.findElement(rollNumberValueLocator).getText.trim)
    else None

  // Change link clicks
  def clickChangeAccountName(): Unit =
    click(changeAccountNameLocator)

  def clickChangeSortCode(): Unit =
    click(changeSortCodeLocator)

  def clickChangeAccountNumber(): Unit =
    click(changeAccountNumberLocator)

  def clickChangeRollNumber(): Unit =
    click(changeRollNumberLocator)

  def currentUrl: String = driver.getCurrentUrl

  // Inside CheckYourAnswersPage.scala
  def verifySummaryList(
    expectedName: String,
    expectedSortCode: String,
    expectedAccountNumber: String,
    expectedRollNumber: Option[String] = None
  ): Unit = {

    // Core assertions that always run
    accountNameKey   shouldBe "Name on the account"
    accountNameValue shouldBe expectedName

    sortCodeKey   shouldBe "Sort code"
    sortCodeValue shouldBe expectedSortCode

    accountNumberKey   shouldBe "Account number"
    accountNumberValue shouldBe expectedAccountNumber

    // Dynamic assertion for the optional roll number
    expectedRollNumber match {
      case Some(rollNum) =>
        rollNumberKey   shouldBe Some("Building society roll number")
        rollNumberValue shouldBe Some(rollNum)

      case None =>
        isRollNumberPresent shouldBe false
    }
  }
}
