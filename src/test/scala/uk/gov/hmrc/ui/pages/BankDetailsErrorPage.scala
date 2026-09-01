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

object BankDetailsErrorPage extends BasePage {
  private val pageHeadingLocator: By              = By.cssSelector("h1.govuk-heading-l")
  private val errorHeadingLocator: By             =
    By.xpath("//p[contains(@class, 'govuk-body')][contains(., 'An error occurred')]")
  private val tryAgainButtonLocator: By           = By.partialLinkText("Try again")
  private val rollNumberErrorLocator: By          =
    By.xpath("//ul[contains(@class, 'govuk-list')]/li[contains(., 'roll number')]")
  private val directCreditErrorLocator: By        =
    By.xpath("//ul[contains(@class, 'govuk-list')]/li[contains(., 'direct credit')]")
  private val accountNotFoundErrorLocator: By     =
    By.xpath("//ul[contains(@class, 'govuk-list')]/li[contains(., 'no account')]")
  private val accountNameMismatchErrorLocator: By =
    By.xpath("//ul[contains(@class, 'govuk-list')]/li[contains(., 'name on the account')]")
  private val contactHmrcLocator: By              = By.xpath("//p[contains(@class, 'govuk-body')][contains(., 'contact us')]")

  // List Items (Error Reasons)
  def rollNumberErrorText: String          = getText(rollNumberErrorLocator).trim
  def directCreditErrorText: String        = getText(directCreditErrorLocator).trim
  def accountNotFoundErrorText: String     = getText(accountNotFoundErrorLocator).trim
  def accountNameMismatchErrorText: String = getText(accountNameMismatchErrorLocator).trim

  // Footer Paragraph
  def contactHmrcText: String = getText(contactHmrcLocator).trim

  override def checkJourneyUrl(url: String = "bank-details-not-verified-user"): Unit =
    super.checkJourneyUrl(url)

  def pageHeadingText: String  = getText(pageHeadingLocator).trim
  def errorHeadingText: String = getText(errorHeadingLocator).trim

  def tryAgain(): Unit = click(tryAgainButtonLocator)

  def verifyAllErrorMessages(): Unit = {
    pageHeadingText              shouldBe "We could not verify your bank account details"
    errorHeadingText             shouldBe "An error occurred while attempting to verify your bank account details. This could be because:"
    rollNumberErrorText          shouldBe "a bank account roll number was not supplied for a building society account"
    directCreditErrorText        shouldBe "the supplied account does not support direct credit"
    accountNotFoundErrorText     shouldBe "no account with the supplied details could be found"
    accountNameMismatchErrorText shouldBe "the name on the account did not match what was supplied"
    contactHmrcText              shouldBe "If this issue persists you may have to contact us (opens in new tab)."
  }
}
