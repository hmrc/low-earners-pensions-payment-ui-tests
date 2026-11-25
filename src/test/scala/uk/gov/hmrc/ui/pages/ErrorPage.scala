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

object ErrorPage extends BasePage {

  private val pageHeadingLocator: By    = By.cssSelector("main h1")
  private val bodyParagraphsLocator: By = By.cssSelector("p.govuk-body")

  def pageHeadingText: String   = getText(pageHeadingLocator).trim
  def bodyParagraphText: String = getText(bodyParagraphsLocator).trim

  override def checkJourneyUrl(url: String = "problem-with-service"): Unit =
    super.checkJourneyUrl(url)
}
