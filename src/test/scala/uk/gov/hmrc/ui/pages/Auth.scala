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
import org.openqa.selenium.support.ui.ExpectedConditions
import org.scalatest.matchers.should.Matchers.*
import uk.gov.hmrc.configuration.TestEnvironment
import uk.gov.hmrc.ui.pages.Auth.sendKeys
import org.slf4j.LoggerFactory
import uk.gov.hmrc.ui.pages.BARSLockOutPage.getClass

object Auth extends BasePage {

  private val authUrl: String                  = TestEnvironment.url("auth-login-stub")
  private val submitLocator: By                = By.cssSelector("Input[value='Submit']")
  private val redirectUrlFieldLocator: By      = By.id("redirectionUrl")
  private val confidenceLevelFieldLocator: By  = By.id("confidenceLevel")
  private val ninoFieldLocator: By             = By.id("nino")
  private val enrolmentKeyZeroFieldLocator: By = By.id("enrolment[0].name")
  private val givenNameFieldLocator: By        = By.id("itmp.givenName")
  private val familyNameFieldLocator: By       = By.id("itmp.familyName")

  // Initialize your class logger
  private val logger = LoggerFactory.getLogger(getClass.getName)

  private val redirectUrl: String =
    TestEnvironment.url("low-earners-pensions-payment-frontend")

  def goToAuthorityWizard(): Unit =
    get(authUrl)
    fluentWait.until(ExpectedConditions.urlContains(authUrl))

  def randomNino: String =
    val digits = (10000 to 99999).toVector
    val suffix = Seq("A", "B", "C", "D")
    val number = digits(scala.util.Random.nextInt(digits.size))
    val letter = suffix(scala.util.Random.nextInt(suffix.size))
    s"AA1$number$letter"

  def checkAuthUrl(): Unit =
    getCurrentUrl should startWith(authUrl)

  def loginUsingAuthorityWizard(): Unit =
    submitLoginDetails("250", "AA123456D")

  def loginUsingAuthorityWizardWithCL200(): Unit =
    submitLoginDetails("200", "AA123456D")

  def loginUsingAuthorityWizardWithRandomNino(): Unit = {
    logger.info(s"Random NINO: $randomNino")
    submitLoginDetails("250", randomNino)
  }

  def loginUsingAuthorityWizardWithNino(nino: String): Unit =
    logger.info(s"Random NINO: $nino")
    submitLoginDetails("250", nino)

  def getRandomNino(): String = randomNino

  def submitLoginDetails(confidenceLevelValue: String, ninoValue: String): Unit = {
    getCurrentUrl should startWith(authUrl)
    sendKeys(redirectUrlFieldLocator, redirectUrl)
    selectByValue(confidenceLevelFieldLocator, confidenceLevelValue)
    sendKeys(ninoFieldLocator, ninoValue)
    sendKeys(enrolmentKeyZeroFieldLocator, "HMRC-PI")
    sendKeys(givenNameFieldLocator, "TestGivenName")
    sendKeys(familyNameFieldLocator, "TestFamilyName")
    click(submitLocator)
  }
}
