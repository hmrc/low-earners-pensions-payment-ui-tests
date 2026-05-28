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

object BankDetailsPage extends BasePage {

  private val path                     = "bank-details"
  private val pageHeadingLocator: By   = By.cssSelector("h1.govuk-heading-l")
  private val continueButton: By       = By.cssSelector("button[type='submit']")
  private val accountNameLocater: By   = By.id("bankDetails_accountName")
  private val sortCodeLocater: By      = By.id("bankDetails_sortCode")
  private val accountNumberLocater: By = By.id("bankDetails_accountNumber")
  private val rollNumberLocater: By    = By.id("bankDetails_rollNumber")

  def enterName(name: String): Unit =
    sendKeys(accountNameLocater, name)

  def enterSortCode(sortCode: String): Unit =
    sendKeys(sortCodeLocater, sortCode)

  def enterAccountNumber(accountNumber: String): Unit =
    sendKeys(accountNumberLocater, accountNumber)

  def enterBuildingSocietyRollNumber(rollNumber: String): Unit =
    sendKeys(rollNumberLocater, rollNumber)

  def continue(): Unit =
    click(continueButton)

  def checkJourneyUrl(): Unit =
    super.checkJourneyUrl(path)

  def pageHeading: String = getText(pageHeadingLocator)

  def currentUrl: String = driver.getCurrentUrl

  def clearBuildingSocietyRollNumber: Unit =
    clearField(rollNumberLocater)

}
