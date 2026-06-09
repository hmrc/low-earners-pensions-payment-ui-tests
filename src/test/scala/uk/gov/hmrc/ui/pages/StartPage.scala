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

object StartPage extends BasePage {

  private val path                  = "start"
  private val startNowButton: By    = By.xpath("//a[normalize-space()='Start now']")
  private val viewPaymentButton: By = By.xpath("//a[normalize-space()='View Payment']")
  private val continueButton: By    = By.xpath("//*[normalize-space()='Continue']")

  def startNow(): Unit =
    click(startNowButton)

  def viewPayment(): Unit =
    click(viewPaymentButton)

  def clickLink(link: String): Unit =
    click(By.id(link))

  def continue(): Unit =
    click(continueButton)

  def checkJourneyUrl(): Unit =
    super.checkJourneyUrl(path)
}
