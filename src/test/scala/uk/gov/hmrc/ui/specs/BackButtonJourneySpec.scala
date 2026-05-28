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

package uk.gov.hmrc.ui.specs

import uk.gov.hmrc.ui.pages.*

class BackButtonJourneySpec extends BaseSpec {
  private val auth = Auth
  private val startPage = StartPage
  private val dashboardPage = DashboardPage
  private val breakdownPage = BreakdownPage
  private val bankDetailsPage = BankDetailsPage
  private val checkYourAnswersPage = CheckYourAnswersPage
  private val confirmationPage = ConfirmationPage

  override def beforeEach(): Unit =
    super.beforeEach()
    Given("The user enters the auth details")
    auth.goToAuthorityWizard()
    auth.loginUsingAuthorityWizard()

    And("The user click the Continue button on Start Page")
    startPage.checkJourneyUrl()

    And("The user click the Continue button on Dashboard page")
    startPage.checkJourneyUrl()

  Feature(
    "As a PAYE individual I need to click the back button and I should be navigated to the previous page"
  ) {
    Scenario(
      "Start Page - Click Back button goes to Authority Wizard Page"
    ) {
      When("The user clicks the back button")
      startPage.goBackToPreviousPage()

      And("The new tab should have the correct title")
      startPage.assertTitle("Authority Wizard")
    }

    Scenario(
      "Dashboard Page - Click Back button goes to Start Page"
    ) {
      And("The user click the Continue button on Start Page")
      startPage.continue()

      And("The user navigates to the Dashboard Page")
      dashboardPage.checkJourneyUrl()

      When("The user clicks the back button")
      dashboardPage.goBackToPreviousPage()

      Then("The user navigates to the Start Page")
      startPage.checkJourneyUrl()

      And("The new tab should have the correct title")
      startPage.assertTitle("Accept your low earner's pension payment - Accept your low earner's pension payment - site.govuk")
    }

    Scenario(
      "Breakdown Page - Click Back button goes to Dashboard Page"
    ) {
      And("The user click the Continue button on Start Page")
      startPage.continue()

      And("The user navigates to the Dashboard Page")
      dashboardPage.checkJourneyUrl()

      And("The user click Accept Payments button")
      dashboardPage.acceptPayments()

      And("The user lands on the breakdown page")
      breakdownPage.checkJourneyUrl()

      When("The user clicks the back button")
      breakdownPage.goBackToPreviousPage()

      Then("The user navigates to the Start Page")
      dashboardPage.checkJourneyUrl()

      And("The new tab should have the correct title")
      dashboardPage.assertTitle("Your low earner's pension payments - Accept your low earner's pension payment - site.govuk")
    }
  }
}