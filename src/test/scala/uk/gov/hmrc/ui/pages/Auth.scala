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

  private val authUrl: String = TestEnvironment.url("auth-login-stub")
  // Initialize your class logger
  private val logger          = LoggerFactory.getLogger(getClass.getName)

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

  def loginUsingAuthorityWizard(): Unit = {
    getCurrentUrl should startWith(authUrl)
    sendKeys(By.id("redirectionUrl"), redirectUrl)
    selectByValue(By.id("confidenceLevel"), "250")
    sendKeys(By.id("nino"), "AA123456D")
    sendKeys(By.id("enrolment[0].name"), "HMRC-PI")
    click(By.cssSelector("Input[value='Submit']"))
  }

  def loginUsingAuthorityWizardWithCL200(): Unit = {
    getCurrentUrl should startWith(authUrl)
    sendKeys(By.id("redirectionUrl"), redirectUrl)
    selectByValue(By.id("confidenceLevel"), "200")
    sendKeys(By.id("nino"), "AA000003D")
    sendKeys(By.id("enrolment[0].name"), "HMRC-PI")
    click(By.cssSelector("Input[value='Submit']"))
  }

  def loginUsingAuthorityWizardWithRandomNino(): Unit = {
    logger.info(s"Random NINO: $randomNino")
    getCurrentUrl should startWith(authUrl)
    sendKeys(By.id("redirectionUrl"), redirectUrl)
    selectByValue(By.id("confidenceLevel"), "250")
    sendKeys(By.id("nino"), randomNino)
    sendKeys(By.id("enrolment[0].name"), "HMRC-PI")
    click(By.cssSelector("Input[value='Submit']"))
  }
}
