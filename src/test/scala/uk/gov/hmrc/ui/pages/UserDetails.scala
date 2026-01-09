/*
 * Copyright 2023 HM Revenue & Customs
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
import org.openqa.selenium.support.ui.{ExpectedConditions, FluentWait}
import uk.gov.hmrc.configuration.TestEnvironment
import uk.gov.hmrc.selenium.webdriver.Driver

import scala.language.postfixOps
import scala.util.Random

object UserDetails extends BasePage {

  private val dashboardUrl: String =
    TestEnvironment.url("low-earners-anomaly-frontend") + "/low-earners-anomaly"

  def waitForElement(by: By): Unit =
    new FluentWait(Driver.instance).until(ExpectedConditions.presenceOfElementLocated(by))

  def startNow(): Unit =
    click(startNowButton)

  def viewPayment(): Unit =
    click(viewPaymentButton)

  def continue(): Unit =
    click(continueButton)

  def submit(): Unit =
    click(submitButton)

  def enterName(firstName: String, lastName: String): Unit = {
    sendKeys(By.id("firstName"), firstName)
    sendKeys(By.id("lastName"), lastName)
    click(continueButton)
  }

  def enterSortCode(sortCode: String): Unit = {
    sendKeys(By.id("sort-code"), sortCode)
  }

  def enterAccountNumber(accountNumber: String): Unit = {
    sendKeys(By.id("account-number"), accountNumber)
  }

  def enterBuildingSocietyRollNumber(rollNumber: String): Unit = {
    sendKeys(By.id("roll-number"), rollNumber)
  }

  def clickLink(link: String): Unit =
    click(By.id(link))

  def checkJourneyUrl(page: String): Unit =
    val url = s"$dashboardUrl/$page"
    fluentWait.until(ExpectedConditions.urlContains(url))
    getCurrentUrl.startsWith(url)

}
