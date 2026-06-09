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

package uk.gov.hmrc.ui.specs

import uk.gov.hmrc.ui.pages.{Auth, BankDetailsPage, BreakdownPage, CheckYourAnswersPage, ConfirmationPage, DashboardPage, StartPage}

class StandardPaymentJourneySpec extends BaseSpec {

  private val auth                 = Auth
  private val startPage            = StartPage
  private val dashboardPage        = DashboardPage
  private val breakdownPage        = BreakdownPage
  private val bankDetailsPage      = BankDetailsPage
  private val checkYourAnswersPage = CheckYourAnswersPage
  private val confirmationPage     = ConfirmationPage

  Feature(
    "As a PAYE individual I need to claim the low income pension payment and view the status of payment for Standard Payment Journey"
  ) {

    Scenario(
      "Standard Payment Journey - Bank account details with Building society roll number"
    ) {

      Given("The user enters the auth details")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard()

      When("The user click the Continue button on Start Page")
      startPage.checkJourneyUrl()
      startPage.continue()

      When("The user click the Continue button on Dashboard page")
      dashboardPage.checkJourneyUrl()

      Then("The Page Heading Text should be correct")
      dashboardPage.pageHeadingText shouldBe "Your low earner's pension payments"

      And("The Available Payments Inset Text should be correct")
      dashboardPage.availablePaymentsInsetText shouldBe "Your payments are suspended. For more information, contact us (opens in new tab)"

      And("The Available Payments table caption should be correct")
      dashboardPage.availablePaymentsTableCaptionText shouldBe "Available payments"

      And("The correct number of rows should be displayed for Available payments")
      dashboardPage.availablePaymentsTableRowCount shouldBe 4

      And("The correct number of columns should be displayed for Available payments")
      dashboardPage.availablePaymentsTableColumnCount shouldBe 4

      And("The Available Payments able headers should be displayed")
      dashboardPage.availablePaymentsTaxYearHeaderText        shouldBe "Tax year"
      dashboardPage.availablePaymentsAmountHeaderText         shouldBe "Amount"
      dashboardPage.availablePaymentsAvailableUntilHeaderText shouldBe "Available until"
      dashboardPage.availablePaymentsStatusHeaderText         shouldBe "Status"

      And("The Available Payments first row should display correct values")
      dashboardPage.availablePaymentsTaxYear(0)        shouldBe "6 April 2022 to 5 April 2023"
      dashboardPage.availablePaymentsAmount(0)         shouldBe "£10.56"
      dashboardPage.availablePaymentsAvailableUntil(0) shouldBe "5 April 2027"
      dashboardPage.availablePaymentsStatus(0)         shouldBe "Available"
      dashboardPage.availablePaymentsIsAvailable(0)    shouldBe true

      And("The Available Payments second row should display correct values")
      dashboardPage.availablePaymentsTaxYear(1)        shouldBe "6 April 2023 to 5 April 2024"
      dashboardPage.availablePaymentsAmount(1)         shouldBe "£10.56"
      dashboardPage.availablePaymentsAvailableUntil(1) shouldBe "5 April 2028"
      dashboardPage.availablePaymentsStatus(1)         shouldBe "Available"
      dashboardPage.availablePaymentsIsAvailable(1)    shouldBe true

      And("The Available Payments second row should display correct values")
      dashboardPage.availablePaymentsTaxYear(2)        shouldBe "6 April 2024 to 5 April 2025"
      dashboardPage.availablePaymentsAmount(2)         shouldBe "£10.56"
      dashboardPage.availablePaymentsAvailableUntil(2) shouldBe "5 April 2029"
      dashboardPage.availablePaymentsStatus(2)         shouldBe "Suspended"
      dashboardPage.availablePaymentsIsSuspended(2)    shouldBe true

      And("The Available Payments second row should display correct values")
      dashboardPage.availablePaymentsTaxYear(3)        shouldBe "6 April 2025 to 5 April 2026"
      dashboardPage.availablePaymentsAmount(3)         shouldBe "£10.56"
      dashboardPage.availablePaymentsAvailableUntil(3) shouldBe "5 April 2030"
      dashboardPage.availablePaymentsStatus(3)         shouldBe "Suspended"
      dashboardPage.availablePaymentsIsSuspended(3)    shouldBe true

      And("The Available Payments total payments text should be correct")
      dashboardPage.availablePaymentsTotalPaymentsText shouldBe "You have a total of £21.12 in payments available to accept."

      And("The Available Payments amount should be £21.12")
      dashboardPage.availablePaymentsTotalPaymentsAmountText shouldBe "£21.12"

      And("The bank details message should be correct")
      dashboardPage.availablePaymentsBankDetailsMessage shouldBe "To accept these payments, you need to provide us with your bank details."

      Then("The Payment History table Inset should be correct")
      dashboardPage.paymentHistoryInsetText shouldBe "We cancelled 3 of your payments. For more information, contact us (opens in new tab)"

      And("The Payment History Cancelled Count Text should be correct")
      dashboardPage.cancelledCountText shouldBe "3"

      And("The Payment history table caption should be correct")
      dashboardPage.paymentHistoryTableCaptionText shouldBe "Payment history"

      And("The correct number of rows should be displayed for Payment history")
      dashboardPage.paymentHistoryTableRowCount shouldBe 4

      And("The correct number of columns should be displayed for Payment history")
      dashboardPage.paymentHistoryTableColumnCount shouldBe 5

      And("The Payment history table headers should be displayed")
      dashboardPage.paymentHistoryTaxYearHeaderText      shouldBe "Tax year"
      dashboardPage.paymentHistoryAmountHeaderText       shouldBe "Amount"
      dashboardPage.paymentHistoryDateAcceptedHeaderText shouldBe "Date accepted"
      dashboardPage.paymentHistoryStatusHeaderText       shouldBe "Status"
      dashboardPage.paymentHistoryActionHeaderText       shouldBe "Action"

      And("The Payment history first row should display correct values")
      dashboardPage.paymentHistoryTaxYear(0)      shouldBe "6 April 2023 to 5 April 2024"
      dashboardPage.paymentHistoryAmount(0)       shouldBe "£10.56"
      dashboardPage.paymentHistoryDateAccepted(0) shouldBe "N/A"
      dashboardPage.paymentHistoryStatus(0)       shouldBe "Cancelled"
      dashboardPage.paymentHistoryAction(0)       shouldBe "Check calculation"

      And("The Payment history second row should display correct values")
      dashboardPage.paymentHistoryTaxYear(1)      shouldBe "6 April 2024 to 5 April 2025"
      dashboardPage.paymentHistoryAmount(1)       shouldBe "£10.56"
      dashboardPage.paymentHistoryDateAccepted(1) shouldBe "N/A"
      dashboardPage.paymentHistoryStatus(1)       shouldBe "Cancelled"
      dashboardPage.paymentHistoryAction(1)       shouldBe "Check calculation"

      And("The Payment history first row should display correct values")
      dashboardPage.paymentHistoryTaxYear(2)      shouldBe "6 April 2025 to 5 April 2026"
      dashboardPage.paymentHistoryAmount(2)       shouldBe "£10.56"
      dashboardPage.paymentHistoryDateAccepted(2) shouldBe "N/A"
      dashboardPage.paymentHistoryStatus(2)       shouldBe "Cancelled"
      dashboardPage.paymentHistoryAction(2)       shouldBe "Check calculation"

      And("The Payment history second row should display correct values")
      dashboardPage.paymentHistoryTaxYear(3)      shouldBe "6 April 2022 to 5 April 2023"
      dashboardPage.paymentHistoryAmount(3)       shouldBe "£10.56"
      dashboardPage.paymentHistoryDateAccepted(3) shouldBe "27 June 2022"
      dashboardPage.paymentHistoryStatus(3)       shouldBe "Paid"
      dashboardPage.paymentHistoryAction(3)       shouldBe "Check calculation"

      And("The action button should show Accept payments")
      dashboardPage.actionButtonText shouldBe "Accept payments"

      And("The user click Accept Payments button")
      dashboardPage.clickActionButton()

      And("The user lands on the breakdown page")
      breakdownPage.checkJourneyUrl()

      Then("The page heading should show correct amount")
      breakdownPage.pageHeadingText shouldBe "You're eligible for a total of £21.12"

      And("The body text should be correct")
      breakdownPage.eligibilityBodyText shouldBe "These payments are due to you because you did not get tax relief on some or all of your net pay pension contributions."

      And("The inset text should contain correct contribution details")
      breakdownPage.verifyInsetBlock(0, "For the tax year 6 April 2022 to 5 April 2023", "£10.56", "10.56%", "£10.56")
      breakdownPage.verifyInsetBlock(1, "For the tax year 6 April 2023 to 5 April 2024", "£10.56", "10.56%", "£10.56")

      And("The bank details text should be correct")
      breakdownPage.bankDetailsText shouldBe "To accept these payments, you need to provide us with your bank details."

      And("The user click the Continue link")
      breakdownPage.continue()

      And("The user navigates to the Bank details page")
      bankDetailsPage.checkJourneyUrl()

      And("The user fill in the bank details and click continue")
      bankDetailsPage.submitBankDetails("Melvin Loper", "20-71-06", "44311677", Some("0123456789"))

      And("The user navigates to the Check Your Answers Page")
      checkYourAnswersPage.checkJourneyUrl()

      Then("The summary list keys & values should be correct")
      checkYourAnswersPage.verifySummaryList("Melvin Loper", "20-71-06", "44311677", Some("0123456789"))

      And("The user click the Submit button")
      checkYourAnswersPage.submit()

      And("The user navigate to confirmation page")
      confirmationPage.checkJourneyUrl()
    }
    Scenario(
      "Standard Payment Journey - Bank account details without Building society roll number"
    ) {
      Given("The user enters the auth details")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard()

      When("The user navigates to the Start Page")
      startPage.checkJourneyUrl()

      When("The user click the Continue button on Start Page")
      startPage.continue()

      When("The user navigates to the Dashboard page")
      dashboardPage.checkJourneyUrl()

      And("The action button should show Accept payments")
      dashboardPage.actionButtonText shouldBe "Accept payments"

      And("The user click Accept Payments button")
      dashboardPage.clickActionButton()

      And("The user lands on the breakdown page")
      breakdownPage.checkJourneyUrl()

      And("The user click the Continue link")
      breakdownPage.continue()

      And("The user navigates to the Bank details page")
      bankDetailsPage.checkJourneyUrl()

      And("The user fill in the bank details and click continue")
      bankDetailsPage.submitBankDetails("Melvin Loper", "20-71-06", "44311677")

      And("The user navigates to the Check Your Answers Page")
      checkYourAnswersPage.checkJourneyUrl()

      And("The summary list keys & values should be correct")
      checkYourAnswersPage.verifySummaryList("Melvin Loper", "20-71-06", "44311677")

      And("The user click the Submit button")
      checkYourAnswersPage.submit()

      And("The user navigate to confirmation page")
      confirmationPage.checkJourneyUrl()
    }
  }
}
