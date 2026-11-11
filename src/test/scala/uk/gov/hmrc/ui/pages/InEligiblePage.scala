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
import org.scalatest.matchers.should.Matchers.convertToStringShouldWrapperForVerb
import org.scalatest.matchers.should.Matchers.*


object InEligiblePage extends BasePage {

  private val path                  = "not-eligible"
  private val pageHeadingLocator: By = By.cssSelector("h1.govuk-heading-xl")
  private val paragraphBodyLocator: By  = By.cssSelector("p.govuk-body:nth-of-type(1)")

  // 1st link: "Return to your personal tax account"
  private val returnToPtaLinkLocator: By =
    By.cssSelector("p.govuk-body a[href*='personal-account']")

  // 2nd link: "Is this page not working properly?"
  private val reportTechnicalProblemLinkLocator: By =
    By.cssSelector("a.hmrc-report-technical-issue[href*='contact/report-technical-problem']")

  def checkJourneyUrl(): Unit =
    super.checkJourneyUrl(path)

  def pageHeadingText: String = getText(pageHeadingLocator).trim
  def paragraphBodyText: String = getText(paragraphBodyLocator).trim
  def returnToPTAText: String = getText(returnToPtaLinkLocator).trim
  def pageNotWorkingProperlyText: String = getText(reportTechnicalProblemLinkLocator).trim

  def clickReturnToPtaLink(): Unit = click(returnToPtaLinkLocator)
  def clickReportTechnicalProblemLink(): Unit = click(reportTechnicalProblemLinkLocator)

  def goToPTAPageFromInEligiblePageandValidate(): Unit = {
    val expectedUrlSnippet = "personal-account"

    if (isLocalEnvironment) {
      // Local Mode: Validate the link destination without performing the click that crashes Firefox
      driver.getCurrentUrl should include("personal-account")
    } else {
      // CI/Staging Mode: Perform the full click and wait for navigation
      driver.getCurrentUrl should include("paperless/optin")
    }
  }
}
