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

object Auth extends BasePage {

  private val authUrl: String = TestEnvironment.url("auth-login-stub")

  private val redirectUrl: String =
    TestEnvironment.url("low-earners-pensions-payment-frontend")

  def goToAuthorityWizard(): Unit =
    get(authUrl)
    fluentWait.until(ExpectedConditions.urlContains(authUrl))

  def checkAuthUrl(): Unit =
    getCurrentUrl should startWith(authUrl)

  def loginUsingAuthorityWizard(): Unit = {
    getCurrentUrl should startWith(authUrl)
    sendKeys(By.id("redirectionUrl"), redirectUrl)
    selectByValue(By.id("confidenceLevel"), "250")
    sendKeys(By.id("nino"), "AA123456D")
    click(By.cssSelector("Input[value='Submit']"))
  }

  def loginUsingAuthorityWizardWithCL200(): Unit = {
    getCurrentUrl should startWith(authUrl)
    sendKeys(By.id("redirectionUrl"), redirectUrl)
    selectByValue(By.id("confidenceLevel"), "200")
    sendKeys(By.id("nino"), "AA000003D")
    click(By.cssSelector("Input[value='Submit']"))
  }
}
