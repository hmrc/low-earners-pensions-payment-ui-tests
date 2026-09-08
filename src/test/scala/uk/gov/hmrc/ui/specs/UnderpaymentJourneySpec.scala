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

class UnderpaymentJourneySpec extends BaseSpec {

  private val auth                 = Auth
  private val startPage            = StartPage
  private val dashboardPage        = DashboardPage
  private val breakdownPage        = BreakdownPage
  private val bankDetailsPage      = BankDetailsPage
  private val checkYourAnswersPage = CheckYourAnswersPage
  private val confirmationPage     = ConfirmationPage

  Feature(
    "As a PAYE Individual I need to claim the low income pension payment and view the status of payment for Underpayment journey"
  ) {

    Scenario(
      "Underpayment Journey - Submit the bank account details and Navigate to confirmation page"
    ) {

      Given("User enters the auth details")
      auth.goToAuthorityWizard()
      auth.loginForUnderpaymentOnly()

      When("The user click the Continue button on Start Page")
      startPage.checkJourneyUrl()
      startPage.continue()

      When("The user click the Continue button on Dashboard page")
      dashboardPage.checkJourneyUrl()

      Then("The Page Heading Text should be correct")
      dashboardPage.pageHeadingText shouldBe "Your low earner's pension payments"

      And("The Available Payments table caption should be correct")
      dashboardPage.availablePaymentsTableCaptionText shouldBe "Available payments"

      And("The correct number of rows should be displayed for Available payments")
      dashboardPage.availablePaymentsTableRowCount shouldBe 1

      And("The correct number of columns should be displayed for Available payments")
      dashboardPage.availablePaymentsTableColumnCount shouldBe 4

      And("The Available Payments able headers should be displayed")
      dashboardPage.availablePaymentsTaxYearHeaderText        shouldBe "Tax year"
      dashboardPage.availablePaymentsAmountHeaderText         shouldBe "Amount"
      dashboardPage.availablePaymentsAvailableUntilHeaderText shouldBe "Available until"
      dashboardPage.availablePaymentsStatusHeaderText         shouldBe "Status"

      And("The Available Payments first row should display correct values")
      dashboardPage.availablePaymentsTaxYear(0)        shouldBe "6 April 2025 to 5 April 2026"
      dashboardPage.availablePaymentsAmount(0)         shouldBe "£50"
      dashboardPage.availablePaymentsAvailableUntil(0) shouldBe "5 April 2030"
      dashboardPage.availablePaymentsStatus(0)         shouldBe "Available"
      dashboardPage.availablePaymentsIsAvailable(0)    shouldBe true

      And("The Available Payments total payments text should be correct")
      dashboardPage.availablePaymentsTotalPaymentsText shouldBe "You have a payment of £50 available to accept."

      And("The bank details message should be correct")
      dashboardPage.availablePaymentsBankDetailsMessage shouldBe "To accept this payment, you need to provide us with your bank details."

      And("The Payment history table caption should be correct")
      dashboardPage.paymentHistoryTableCaptionText shouldBe "Payment history"

      And("The correct number of rows should be displayed for Payment history")
      dashboardPage.paymentHistoryTableRowCount shouldBe 1

      And("The correct number of columns should be displayed for Payment history")
      dashboardPage.paymentHistoryTableColumnCount shouldBe 5

      And("The Payment history table headers should be displayed")
      dashboardPage.paymentHistoryTaxYearHeaderText      shouldBe "Tax year"
      dashboardPage.paymentHistoryAmountHeaderText       shouldBe "Amount"
      dashboardPage.paymentHistoryDateAcceptedHeaderText shouldBe "Date accepted"
      dashboardPage.paymentHistoryStatusHeaderText       shouldBe "Status"
      dashboardPage.paymentHistoryActionHeaderText       shouldBe "Action"

      And("The Payment history first row should display correct values")
      dashboardPage.paymentHistoryTaxYear(0)                                              shouldBe "6 April 2025 to 5 April 2026"
      dashboardPage.paymentHistoryAmount(0)                                               shouldBe "£100"
      dashboardPage.isDateWithinTheTimeFrame(dashboardPage.paymentHistoryDateAccepted(0)) shouldBe true
      dashboardPage.paymentHistoryStatus(0)                                               shouldBe "Paid"
      dashboardPage.paymentHistoryAction(0)                                               shouldBe "Check calculation\n6 April 2025 to 5 April 2026"

      And("The action button should show Accept payments")
      dashboardPage.actionButtonText shouldBe "Accept payment"

      And("The user click Accept Payments button")
      dashboardPage.clickActionButton()

      And("The user lands on the breakdown page")
      breakdownPage.checkJourneyUrl()

      Then("The page heading should show correct amount")
      breakdownPage.pageHeadingText shouldBe "You're eligible for an additional £50 payment"

      And("The body text should be correct")
      breakdownPage.paragraphBodyText(
        0
      ) shouldBe "You were not paid enough in your previous payment for this tax year and we've recalculated the amount."

      And("The additional body text should be correct")
      breakdownPage.paragraphBodyText(
        1
      ) shouldBe "This is because we've received new information about how much you've earned."

      And("The inset text should contain correct contribution details")
      breakdownPage.verifyUnderPaymentInsetBlock(
        0,
        "6 April 2025 to 5 April 2026",
        "£750",
        "20%",
        "£150",
        "£100",
        "£50"
      )

      And("The validation body text should be correct")
      breakdownPage.paragraphBodyText(
        2
      ) shouldBe "If you think the amounts are wrong, you can contact us (opens in new tab)."

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
  }
}
