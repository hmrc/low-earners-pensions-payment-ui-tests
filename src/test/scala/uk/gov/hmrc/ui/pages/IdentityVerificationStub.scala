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
import scala.jdk.CollectionConverters._

object IdentityVerificationStub extends BasePage {

  private val submitContinueButton: By     = By.id("submit-continue")
  private val journeyResultRadioButton: By = By.tagName("label")

  def clickRadioButton(text: String): Unit =
    driver.findElements(journeyResultRadioButton).asScala.filter(_.getText.trim == text).head.click()

  def submitContinue(): Unit =
    click(submitContinueButton)
}
