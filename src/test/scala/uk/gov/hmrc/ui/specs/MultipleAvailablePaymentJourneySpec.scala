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

import uk.gov.hmrc.ui.pages.*
import org.scalatest.matchers.should.Matchers.*

class MultipleAvailablePaymentJourneySpec extends BaseSpec {

  private val auth                 = Auth
  private val startPage            = StartPage
  private val dashboardPage        = DashboardPage
  private val breakdownPage        = BreakdownPage
  private val bankDetailsPage      = BankDetailsPage
  private val checkYourAnswersPage = CheckYourAnswersPage
  private val confirmationPage     = ConfirmationPage

  Feature(
    "As a PAYE individual I need to claim the low income pension payment and view the status of payment for Multiple Payment Journey"
  ) {

    Scenario(
      "Multiple Payments - Standard Journey"
    ) {

      Given("The user enters the auth details")
      auth.goToAuthorityWizard()
      auth.loginForMultiplePaymentJourney("AA923456D")

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
      dashboardPage.availablePaymentsTableRowCount shouldBe 3

      And("The correct number of columns should be displayed for Available payments")
      dashboardPage.availablePaymentsTableColumnCount shouldBe 4

      And("The Available Payments able headers should be displayed")
      dashboardPage.availablePaymentsTaxYearHeaderText        shouldBe "Tax year"
      dashboardPage.availablePaymentsAmountHeaderText         shouldBe "Amount"
      dashboardPage.availablePaymentsAvailableUntilHeaderText shouldBe "Available until"
      dashboardPage.availablePaymentsStatusHeaderText         shouldBe "Status"

      And("The Available Payments first row should display correct values")
      dashboardPage.availablePaymentsTaxYear(0)        shouldBe "6 April 2023 to 5 April 2024"
      dashboardPage.availablePaymentsAmount(0)         shouldBe "£200"
      dashboardPage.availablePaymentsAvailableUntil(0) shouldBe "5 April 2028"
      dashboardPage.availablePaymentsStatus(0)         shouldBe "Available"
      dashboardPage.availablePaymentsIsAvailable(0)    shouldBe true

      And("The Available Payments second row should display correct values")
      dashboardPage.availablePaymentsTaxYear(1)        shouldBe "6 April 2024 to 5 April 2025"
      dashboardPage.availablePaymentsAmount(1)         shouldBe "£200"
      dashboardPage.availablePaymentsAvailableUntil(1) shouldBe "5 April 2029"
      dashboardPage.availablePaymentsStatus(1)         shouldBe "Available"
      dashboardPage.availablePaymentsIsAvailable(1)    shouldBe true

      And("The Available Payments third row should display correct values")
      dashboardPage.availablePaymentsTaxYear(2)        shouldBe "6 April 2025 to 5 April 2026"
      dashboardPage.availablePaymentsAmount(2)         shouldBe "£200"
      dashboardPage.availablePaymentsAvailableUntil(2) shouldBe "5 April 2030"
      dashboardPage.availablePaymentsStatus(2)         shouldBe "Available"
      dashboardPage.availablePaymentsIsAvailable(2)    shouldBe true

      And("The Available Payments total payments text should be correct")
      dashboardPage.availablePaymentsTotalPaymentsText shouldBe "You have a total of £600 in payments available to accept."

      And("The Available Payments amount should be correct")
      dashboardPage.availablePaymentsTotalPaymentsAmountText shouldBe "£600"

      And("The bank details message should be correct")
      dashboardPage.availablePaymentsBankDetailsMessage shouldBe "To accept these payments, you need to provide us with your bank details."

      And("The Payment history table caption should be correct")
      dashboardPage.paymentHistoryTableCaptionText shouldBe "Payment history"

      And("The Payment History paragraph text should be correct")
      dashboardPage.paymentHistoryParagraphText shouldBe "You do not have any previous payments."

      And("The action button should show Accept payments")
      dashboardPage.actionButtonText shouldBe "Accept payments"

      And("The user click Accept Payments button")
      dashboardPage.clickActionButton()

      And("The user lands on the breakdown page")
      breakdownPage.checkJourneyUrl()

      Then("The page heading should show correct amount")
      breakdownPage.pageHeadingText shouldBe "You're eligible for a total of £600"

      And("The body text should be correct")
      breakdownPage.paragraphBodyText(
        0
      ) shouldBe "These payments are due to you because you did not get tax relief on some or all of your net pay pension contributions."

      And("The first row inset text should contain correct contribution details")
      breakdownPage.verifyStandardPaymentInsetBlock(
        0,
        "6 April 2023 to 5 April 2024",
        "£1000",
        "20%",
        "£200"
      )

      And("The second row inset text should contain correct contribution details")
      breakdownPage.verifyStandardPaymentInsetBlock(
        1,
        "6 April 2024 to 5 April 2025",
        "£1000",
        "20%",
        "£200"
      )

      And("The third row inset text should contain correct contribution details")
      breakdownPage.verifyStandardPaymentInsetBlock(
        2,
        "6 April 2025 to 5 April 2026",
        "£1000",
        "20%",
        "£200"
      )

      And("The validation body text should be correct")
      breakdownPage.paragraphBodyText(
        1
      ) shouldBe "If you think the amounts are wrong, you can call us on 0300 322 9665."

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

      And("The Confirmation page should have the correct title")
      confirmationPage.assertTitle(
        "We've received your bank details - Accept your low earner's pension payment - GOV.UK"
      )
    }

    Scenario(
      "Multiple Payments - Journey with 1 success and three failures"
    ) {

      Given("The user enters the auth details")
      auth.goToAuthorityWizard()
      auth.loginForMultiplePaymentJourney("ER291456D")

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
      dashboardPage.availablePaymentsAmount(0)         shouldBe "£200"
      dashboardPage.availablePaymentsAvailableUntil(0) shouldBe "5 April 2027"
      dashboardPage.availablePaymentsStatus(0)         shouldBe "Available"
      dashboardPage.availablePaymentsIsAvailable(0)    shouldBe true

      And("The Available Payments second row should display correct values")
      dashboardPage.availablePaymentsTaxYear(1)        shouldBe "6 April 2023 to 5 April 2024"
      dashboardPage.availablePaymentsAmount(1)         shouldBe "£200"
      dashboardPage.availablePaymentsAvailableUntil(1) shouldBe "5 April 2028"
      dashboardPage.availablePaymentsStatus(1)         shouldBe "Available"
      dashboardPage.availablePaymentsIsAvailable(1)    shouldBe true

      And("The Available Payments third row should display correct values")
      dashboardPage.availablePaymentsTaxYear(2)        shouldBe "6 April 2024 to 5 April 2025"
      dashboardPage.availablePaymentsAmount(2)         shouldBe "£200"
      dashboardPage.availablePaymentsAvailableUntil(2) shouldBe "5 April 2029"
      dashboardPage.availablePaymentsStatus(2)         shouldBe "Available"
      dashboardPage.availablePaymentsIsAvailable(2)    shouldBe true

      And("The Available Payments fourth row should display correct values")
      dashboardPage.availablePaymentsTaxYear(3)        shouldBe "6 April 2025 to 5 April 2026"
      dashboardPage.availablePaymentsAmount(3)         shouldBe "£200"
      dashboardPage.availablePaymentsAvailableUntil(3) shouldBe "5 April 2030"
      dashboardPage.availablePaymentsStatus(3)         shouldBe "Available"
      dashboardPage.availablePaymentsIsAvailable(3)    shouldBe true

      And("The Available Payments total payments text should be correct")
      dashboardPage.availablePaymentsTotalPaymentsText shouldBe "You have a total of £800 in payments available to accept."

      And("The Available Payments amount should be correct")
      dashboardPage.availablePaymentsTotalPaymentsAmountText shouldBe "£800"

      And("The bank details message should be correct")
      dashboardPage.availablePaymentsBankDetailsMessage shouldBe "To accept these payments, you need to provide us with your bank details."

      And("The Payment history table caption should be correct")
      dashboardPage.paymentHistoryTableCaptionText shouldBe "Payment history"

      And("The Payment History paragraph text should be correct")
      dashboardPage.paymentHistoryParagraphText shouldBe "You do not have any previous payments."

      And("The action button should show Accept payments")
      dashboardPage.actionButtonText shouldBe "Accept payments"

      And("The user click Accept Payments button")
      dashboardPage.clickActionButton()

      And("The user lands on the breakdown page")
      breakdownPage.checkJourneyUrl()

      Then("The page heading should show correct amount")
      breakdownPage.pageHeadingText shouldBe "You're eligible for a total of £800"

      And("The body text should be correct")
      breakdownPage.paragraphBodyText(
        0
      ) shouldBe "These payments are due to you because you did not get tax relief on some or all of your net pay pension contributions."

      And("The first row inset text should contain correct contribution details")
      breakdownPage.verifyStandardPaymentInsetBlock(
        0,
        "6 April 2022 to 5 April 2023",
        "£1000",
        "20%",
        "£200"
      )

      And("The second row inset text should contain correct contribution details")
      breakdownPage.verifyStandardPaymentInsetBlock(
        1,
        "6 April 2023 to 5 April 2024",
        "£1000",
        "20%",
        "£200"
      )

      And("The third row inset text should contain correct contribution details")
      breakdownPage.verifyStandardPaymentInsetBlock(
        2,
        "6 April 2024 to 5 April 2025",
        "£1000",
        "20%",
        "£200"
      )

      And("The fourth row inset text should contain correct contribution details")
      breakdownPage.verifyStandardPaymentInsetBlock(
        3,
        "6 April 2025 to 5 April 2026",
        "£1000",
        "20%",
        "£200"
      )

      And("The validation body text should be correct")
      breakdownPage.paragraphBodyText(
        1
      ) shouldBe "If you think the amounts are wrong, you can call us on 0300 322 9665."

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

      And("The user navigate to confirmation page")
      confirmationPage.checkJourneyUrl()

      And("The Confirmation page should have the correct title")
      confirmationPage.assertTitle(
        "Some of your payments failed to process due to a system error - Accept your low earner's pension payment - GOV.UK"
      )

      And("The Confirmation page should have the correct title")
      confirmationPage.pageHeadingText shouldBe "Some of your payments failed to process due to a system error"

      And("The Confirmation page should have the correct title")
      confirmationPage.failedPaymentsHeadingText shouldBe "Failed payments"

      And("The Confirmation page should have the correct title")
      confirmationPage.successfulPaymentsHeadingText shouldBe "Successful payments"

      And("The Confirmation page should have the correct title")
      confirmationPage.whatYouCanDoNextHeadingText shouldBe "What you can do next"

      And("The Confirmation page Body Paragraph Text should correct")
      confirmationPage.getBodyParagraphText(
        0
      ) shouldBe "To accept them again, view your payments and select 'Accept payments'."

      And("The Confirmation page Body Paragraph Text should correct")
      confirmationPage.getBodyParagraphText(
        1
      ) shouldBe "We'll send the payment to the bank account you provided within 7 working days."

      And("The failed payments table should display the expected rows")
      confirmationPage.failedPaymentsRowCount shouldBe 3

      And("The Failed Payments first row should display correct values")
      confirmationPage.failedPaymentsTaxYear(0)        shouldBe "6 April 2023 to 5 April 2024"
      confirmationPage.failedPaymentsAvailableUntil(0) shouldBe "5 April 2028"
      confirmationPage.failedPaymentsAmount(0)         shouldBe "£200"

      And("The Failed Payments second row should display correct values")
      confirmationPage.failedPaymentsTaxYear(1)        shouldBe "6 April 2024 to 5 April 2025"
      confirmationPage.failedPaymentsAvailableUntil(1) shouldBe "5 April 2029"
      confirmationPage.failedPaymentsAmount(1)         shouldBe "£200"

      And("The Failed Payments third row should display correct values")
      confirmationPage.failedPaymentsTaxYear(2)        shouldBe "6 April 2025 to 5 April 2026"
      confirmationPage.failedPaymentsAvailableUntil(2) shouldBe "5 April 2030"
      confirmationPage.failedPaymentsAmount(2)         shouldBe "£200"

      And("The successful payments table should display the expected row")
      confirmationPage.successfulPaymentsRowCount shouldBe 1

      And("The Successful Payments first row should display correct values")
      confirmationPage.successfulPaymentsTaxYear(0) shouldBe "6 April 2022 to 5 April 2023"
      confirmationPage.successfulPaymentsAmount(0)  shouldBe "£200"

      And("The Print this Page link exists")
      confirmationPage.printThisPageText shouldBe "Print this page"
      confirmationPage.printThisPageHref   should endWith("#")

      And("The View Your Payments link exists")
      confirmationPage.viewYourPaymentsText shouldBe "View your payments"
      confirmationPage.viewYourPaymentsHref   should include("/accept-your-low-earners-pension-payment/payments")

      And("The Feeback link exists")
      confirmationPage.feedbackLinkText should include("Tell us what you think of this service")
      confirmationPage.feedbackLinkHref should not be empty

      And("The report technical problem link exists")
      confirmationPage.reportTechnicalProblemText should include("Is this page not working properly?")
      confirmationPage.reportTechnicalProblemHref should include("contact/report-technical-problem")

      And("the submission date and time should be displayed in the expected format")
      confirmationPage.verifySubmittedDateTimeFormat(confirmationPage.submittedText)
    }
  }
}
