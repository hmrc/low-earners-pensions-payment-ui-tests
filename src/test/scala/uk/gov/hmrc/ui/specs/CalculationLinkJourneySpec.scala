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
    auth.loginForPaymentJourney("250", "AA223456D")

    When("The user click the Continue button on Start Page")
    startPage.checkJourneyUrl()
    startPage.continue()

    Then("The user will be navigated to the Dashboard page")
    dashboardPage.checkJourneyUrl()

    And("The Page Heading Text should be correct")
    dashboardPage.pageHeadingText shouldBe "Your low earner's pension payments"

  Feature(
    "As a PAYE individual I need to check the calculation for different Payment History Status"
  ) {

    Scenario(
      "Dashboard Page - Standard Payment - Check Calculation for Paid Status"
    ) {
      And("The Available Payments table caption should be correct")
      dashboardPage.availablePaymentsTableCaptionText shouldBe "Available payments"

      And("The Available Payments total payments text should be correct")
      dashboardPage.availablePaymentsTotalPaymentsText shouldBe "You do not have any available payments."

      Then("The Payment History table Inset should be correct")
      dashboardPage.cancelledInsetText shouldBe "Following a review of your entitlement, we have cancelled 2 of your payments. As a result, these payments will not be made."

      And("The Payment history table caption should be correct")
      dashboardPage.paymentHistoryTableCaptionText shouldBe "Payment history"

      And("The correct number of rows should be displayed for Payment history")
      dashboardPage.paymentHistoryTableRowCount shouldBe 5

      And("The correct number of columns should be displayed for Payment history")
      dashboardPage.paymentHistoryTableColumnCount shouldBe 5

      And("The Payment history table headers should be displayed")
      dashboardPage.paymentHistoryTaxYearHeaderText      shouldBe "Tax year"
      dashboardPage.paymentHistoryAmountHeaderText       shouldBe "Amount"
      dashboardPage.paymentHistoryDateAcceptedHeaderText shouldBe "Date accepted"
      dashboardPage.paymentHistoryStatusHeaderText       shouldBe "Status"
      dashboardPage.paymentHistoryActionHeaderText       shouldBe "Action"

      And("The Payment history first row should display correct values")
      dashboardPage.paymentHistoryTaxYear(2)                                              shouldBe "6 April 2024 to 5 April 2025"
      dashboardPage.paymentHistoryAmount(2)                                               shouldBe "£100"
      dashboardPage.isDateWithinTheTimeFrame(dashboardPage.paymentHistoryDateAccepted(2)) shouldBe true
      dashboardPage.paymentHistoryStatus(2)                                               shouldBe "Paid"
      dashboardPage.paymentHistoryAction(2)                                               shouldBe "Check calculation\n6 April 2024 to 5 April 2025"

      When("The user clicks the calculation link for the Paid status")
      dashboardPage.clickCheckCalculationLink("P-2024-1")

      And("The user lands on the breakdown page")
      breakdownPage.checkCalculationourneyUrl("P-2024-1")

      Then("The page heading should show correct amount")
      breakdownPage.pageHeadingText shouldBe "Your £100 paid payment calculation"

      And("The body text should be correct")
      breakdownPage.paragraphBodyText(
        0
      ) shouldBe "This payment was due to you because you did not get tax relief on some or all of your net pay pension contributions."

      And("The inset text should contain correct contribution details")
      breakdownPage.verifyStandardPaymentInsetBlock(
        0,
        "6 April 2024 to 5 April 2025",
        "£500",
        "20%",
        "£100"
      )

      And("The user click Return to your payments")
      breakdownPage.returnToYourPayments

      Then("The user will be navigated to the Dashboard page")
      dashboardPage.checkJourneyUrl()
    }

    Scenario(
      "Dashboard Page - Under Payment - Check Calculation for Paid Status"
    ) {
      And("The Payment history first row should display correct values")
      dashboardPage.paymentHistoryTaxYear(3)                                              shouldBe "6 April 2024 to 5 April 2025"
      dashboardPage.paymentHistoryAmount(3)                                               shouldBe "£100"
      dashboardPage.isDateWithinTheTimeFrame(dashboardPage.paymentHistoryDateAccepted(3)) shouldBe true
      dashboardPage.paymentHistoryStatus(3)                                               shouldBe "Paid"
      dashboardPage.paymentHistoryAction(3)                                               shouldBe "Check calculation\n6 April 2024 to 5 April 2025"

      When("The user clicks the calculation link for the Under payment Paid status")
      dashboardPage.clickCheckCalculationLink("P-2024-3")

      And("The user lands on the breakdown page")
      breakdownPage.checkCalculationourneyUrl("P-2024-3")

      Then("The page heading should show correct amount")
      breakdownPage.pageHeadingText shouldBe "Your additional £100 paid payment calculation"

      And("The body text should be correct")
      breakdownPage.paragraphBodyText(
        0
      ) shouldBe "You were not paid enough in your previous payment for this tax year and we've recalculated the amount."

      And("The body text should be correct")
      breakdownPage.paragraphBodyText(
        1
      ) shouldBe "This is because we've received new information about how much you've earned."

      And("The inset text should contain correct contribution details")
      breakdownPage.verifyUnderPaymentInsetBlock(
        0,
        "6 April 2024 to 5 April 2025",
        "£1000",
        "20%",
        "£200",
        "£100",
        "£100"
      )

      And("The user click Return to your payments")
      breakdownPage.returnToYourPayments

      Then("The user will be navigated to the Dashboard page")
      dashboardPage.checkJourneyUrl()
    }

    Scenario(
      "Dashboard Page - Standard Payment - Check Calculation for Cancelled Status"
    ) {
      And("The Payment history first row should display correct values")
      dashboardPage.paymentHistoryTaxYear(1)      shouldBe "6 April 2023 to 5 April 2024"
      dashboardPage.paymentHistoryAmount(1)       shouldBe "£100"
      dashboardPage.paymentHistoryDateAccepted(1) shouldBe "N/A"
      dashboardPage.paymentHistoryStatus(1)       shouldBe "Cancelled"
      dashboardPage.paymentHistoryAction(1)       shouldBe "Check calculation\n6 April 2023 to 5 April 2024"

      When("The user clicks the calculation link for the Cancelled status")
      dashboardPage.clickCheckCalculationLink("C-2023-1")

      And("The user lands on the breakdown page")
      breakdownPage.checkCalculationourneyUrl("C-2023-1")

      Then("The page heading should show correct amount")
      breakdownPage.pageHeadingText shouldBe "Your £100 cancelled payment calculation"

      And("The body text should be correct")
      breakdownPage.paragraphBodyText(
        0
      ) shouldBe "Following a review of your entitlement, we have cancelled this payment. As a result, this payment will not be made."

      And("The inset text should contain correct contribution details")
      breakdownPage.verifyStandardPaymentInsetBlock(
        0,
        "6 April 2023 to 5 April 2024",
        "£500",
        "20%",
        "£100"
      )

      And("The user click Return to your payments")
      breakdownPage.returnToYourPayments

      Then("The user will be navigated to the Dashboard page")
      dashboardPage.checkJourneyUrl()
    }
    Scenario(
      "Dashboard Page - Under Payment - Check Calculation for Cancelled Status"
    ) {
      And("The Payment history first row should display correct values")
      dashboardPage.paymentHistoryTaxYear(0)      shouldBe "6 April 2024 to 5 April 2025"
      dashboardPage.paymentHistoryAmount(0)       shouldBe "£100"
      dashboardPage.paymentHistoryDateAccepted(0) shouldBe "N/A"
      dashboardPage.paymentHistoryStatus(0)       shouldBe "Cancelled"
      dashboardPage.paymentHistoryAction(0)       shouldBe "Check calculation\n6 April 2024 to 5 April 2025"

      When("The user clicks the calculation link for the Cancelled status")
      dashboardPage.clickCheckCalculationLink("C-2024-2")

      And("The user lands on the breakdown page")
      breakdownPage.checkCalculationourneyUrl("C-2024-2")

      Then("The page heading should show correct amount")
      breakdownPage.pageHeadingText shouldBe "Your additional £100 cancelled payment calculation"

      And("The body text should be correct")
      breakdownPage.paragraphBodyText(
        0
      ) shouldBe "Following a review of your entitlement, we have cancelled this payment. As a result, this payment will not be made."

      And("The inset text should contain correct contribution details")
      breakdownPage.verifyUnderPaymentInsetBlock(
        0,
        "6 April 2024 to 5 April 2025",
        "£1000",
        "20%",
        "£200",
        "£100",
        "£100"
      )

      And("The user click Return to your payments")
      breakdownPage.returnToYourPayments

      Then("The user will be navigated to the Dashboard page")
      dashboardPage.checkJourneyUrl()
    }
  }
}
