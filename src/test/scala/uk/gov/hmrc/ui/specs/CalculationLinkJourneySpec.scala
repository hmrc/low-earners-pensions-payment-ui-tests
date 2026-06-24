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

import uk.gov.hmrc.ui.pages.{Auth, BreakdownPage, DashboardPage, StartPage}

class CalculationLinkJourneySpec extends BaseSpec {
  private val auth          = Auth
  private val startPage     = StartPage
  private val dashboardPage = DashboardPage
  private val breakdownPage = BreakdownPage

  override def beforeEach(): Unit =
    super.beforeEach()
    Given("The user enters the auth details")
    auth.goToAuthorityWizard()
    auth.loginUsingAuthorityWizard()

    And("The user click the Continue button on Start Page")
    startPage.checkJourneyUrl()

    And("The user click the Continue button on Dashboard page")
    startPage.checkJourneyUrl()

    And("The user click the Continue button on Start Page")
    startPage.continue()

    And("The user navigates to the Dashboard Page")
    dashboardPage.checkJourneyUrl()

  Feature(
    "As a PAYE individual I need to check the calculation for different Payment History Status"
  ) {

    Scenario(
      "Dashboard Page - Check Calculation for Paid Status"
    ) {
      When("The user clicks the calculation link for the Paid status")
      dashboardPage.clickPaidCalculationLink()

      And("The user lands on the breakdown page")
      breakdownPage.checkJourneyUrl()

      Then("The page heading should show correct amount")
      breakdownPage.pageHeadingText shouldBe "You're eligible for a total of £200"

      And("The body text should be correct")
      breakdownPage.eligibilityBodyText shouldBe "These payments are due to you because you did not get tax relief on some or all of your net pay pension contributions."

      And("The inset text should contain correct contribution details")
      breakdownPage.verifyStandardPaymentInsetBlock(
        0,
        "6 April 2024 to 5 April 2025",
        "£1000",
        "20%",
        "£200"
      )

      And("The bank details text should be correct")
      breakdownPage.bankDetailsText shouldBe "To accept these payments, you need to provide us with your bank details."
    }

    Scenario(
      "Dashboard Page - Check Calculation for Cancelled Status"
    ) {
      When("The user clicks the calculation link for the Cancelled status")
      dashboardPage.clickCancelledCalculationLink()

      And("The user lands on the breakdown page")
      breakdownPage.checkJourneyUrl()

      Then("The page heading should show correct amount")
      breakdownPage.pageHeadingText shouldBe "You're eligible for a total of £200"

      And("The body text should be correct")
      breakdownPage.eligibilityBodyText shouldBe "These payments are due to you because you did not get tax relief on some or all of your net pay pension contributions."

      And("The inset text should contain correct contribution details")
      breakdownPage.verifyStandardPaymentInsetBlock(
        0,
        "6 April 2025 to 5 April 2026",
        "£1000",
        "20%",
        "£200"
      )

      And("The bank details text should be correct")
      breakdownPage.bankDetailsText shouldBe "To accept these payments, you need to provide us with your bank details."
    }
  }
}
