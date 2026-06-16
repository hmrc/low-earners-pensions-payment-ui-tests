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

class BARSLockOutJourneySpec extends BaseSpec {

  private val auth                 = Auth
  private val startPage            = StartPage
  private val dashboardPage        = DashboardPage
  private val breakdownPage        = BreakdownPage
  private val bankDetailsPage      = BankDetailsPage
  private val checkYourAnswersPage = CheckYourAnswersPage
  private val bankDetailsErrorPage = BankDetailsErrorPage
  private val barsLockOutPage      = BARSLockOutPage
  private val confirmationPage     = ConfirmationPage
  private val feedbackPage         = FeedbackPage

  Feature(
    "If a user fails to provide correct details on the third attempt a 24 hour BARS service lock out will be implemented"
  ) {

    Scenario(
      "BARS Lock Out Journey - NINO Locks Out on submitting 3 invalid bank details"
    ) {
      val generatedNino: String = auth.randomNino

      Given("The user enters the auth details")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizardWithNino(generatedNino)

      And("The user navigates to the  Start Page")
      startPage.checkJourneyUrl()

      When("The user click the Continue button on Start Page")
      startPage.continue()

      When("The user click the Continue button on Dashboard page")
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
      bankDetailsPage.submitBankDetails("Test User", "12-34-56", "12345678")

      And("The user navigates to the Check Your Answers Page")
      checkYourAnswersPage.checkJourneyUrl()

      Then("The summary list keys & values should be correct")
      checkYourAnswersPage.verifySummaryList("Test User", "12-34-56", "12345678")

      And("The user click the Submit button")
      checkYourAnswersPage.submit()

      And("The user navigate to confirmation page")
      bankDetailsErrorPage.checkJourneyUrl()

      Then("The Page Heading Text should be correct")
      bankDetailsErrorPage.pageHeadingText shouldBe "We could not verify your bank account details"

      And("The Error Heading Text should be correct")
      bankDetailsErrorPage.errorHeadingText shouldBe "An error occurred while attempting to verify your bank account details. This could be because:"

      And("The Bars Lock Out Page Error Text should be correct")
      bankDetailsErrorPage.verifyAllErrorMessages()

      When("The user click the Try again button")
      bankDetailsErrorPage.tryAgain()

      And("The user navigates to the Bank details Page")
      bankDetailsPage.checkJourneyUrl()

      And("The user click the Continue link")
      bankDetailsPage.continue()

      And("The user navigates to the Check Your Answers Page")
      checkYourAnswersPage.checkJourneyUrl()

      And("The summary list keys & values should be correct")
      checkYourAnswersPage.verifySummaryList("Test User", "12-34-56", "12345678")

      And("The user click the Submit button")
      checkYourAnswersPage.submit()

      And("The user navigate to Bank Details page")
      bankDetailsErrorPage.checkJourneyUrl()

      And("The Bars Lock Out Page Error Text should be correct")
      bankDetailsErrorPage.verifyAllErrorMessages()

      When("The user click the Try again button")
      bankDetailsErrorPage.tryAgain()

      And("The user navigates to the Bank details Page")
      bankDetailsPage.checkJourneyUrl()

      And("The user click the Continue link")
      bankDetailsPage.continue()

      And("The user navigates to the Check Your Answers Page")
      checkYourAnswersPage.checkJourneyUrl()

      And("The summary list keys & values should be correct")
      checkYourAnswersPage.verifySummaryList("Test User", "12-34-56", "12345678")

      And("The user click the Submit button")
      checkYourAnswersPage.submit()

      And("The user navigates to the lockout page")
      barsLockOutPage.checkJourneyUrl()

      Then("The page heading should indicate a lockout")
      barsLockOutPage.pageHeadingText shouldBe "You’ve tried to confirm your bank details too many times"

      And("The lockout message should show a time exactly 24 hours in the future")
      barsLockOutPage.verifyLockoutTimeIs24HoursInFuture()

      And("The user clicks the return to payments")
      barsLockOutPage.returnToPayments

      And("The user sees the IMPORTANT message banner in the Dashboard Page")
      dashboardPage.verifyLockoutBanner()

      And("The action button should show Accept payments")
      dashboardPage.actionButtonText shouldBe "View payments"

      And("The user click Accept Payments button")
      dashboardPage.clickActionButton()

      And("The user lands on the breakdown page")
      breakdownPage.checkJourneyUrl()

      When("The user navigates to the bank details page via bookmark when locked")
      bankDetailsPage.goToPage()

      And("The user will be taken to the lockout page")
      barsLockOutPage.checkJourneyUrl()

      Then("The page heading should indicate a lockout")
      barsLockOutPage.pageHeadingText shouldBe "You’ve tried to confirm your bank details too many times"

      When("The user clicks the signout button")
      barsLockOutPage.signOut()

      Then("The user navigates to the feedback page")
      feedbackPage.assertTitle("Give feedback - GOV.UK")

      Given("The user enters the auth details")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizardWithNino(generatedNino)

      And("The user navigates to the  Start Page")
      startPage.checkJourneyUrl()

      When("The user click the Continue button on Start Page")
      startPage.continue()

      When("The user click the Continue button on Dashboard page")
      dashboardPage.checkJourneyUrl()

      And("The user sees the IMPORTANT message banner in the Dashboard Page")
      dashboardPage.verifyLockoutBanner()

      And("The action button should show Accept payments")
      dashboardPage.actionButtonText shouldBe "View payments"

      And("The user click Accept Payments button")
      dashboardPage.clickActionButton()

      And("The user lands on the breakdown page")
      breakdownPage.checkJourneyUrl()

      When("The user navigates to the bank details page via bookmark when locked")
      bankDetailsPage.goToPage()

      And("The user will be taken to the lockout page")
      barsLockOutPage.checkJourneyUrl()

      Then("The page heading should indicate a lockout")
      barsLockOutPage.pageHeadingText shouldBe "You’ve tried to confirm your bank details too many times"
    }

    Scenario(
      "BARS Lock Out Journey - NINO do not Locks Out on submitting valid bank details on 3rd attempt"
    ) {
      val generatedNino: String = auth.randomNino
      Given("The user enters the auth details")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizardWithNino(generatedNino)

      And("The user navigates to the  Start Page")
      startPage.checkJourneyUrl()

      When("The user click the Continue button on Start Page")
      startPage.continue()

      When("The user click the Continue button on Dashboard page")
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
      bankDetailsPage.submitBankDetails("Test User", "12-34-56", "12345678")

      And("The user navigates to the Check Your Answers Page")
      checkYourAnswersPage.checkJourneyUrl()

      Then("The summary list keys & values should be correct")
      checkYourAnswersPage.verifySummaryList("Test User", "12-34-56", "12345678")

      And("The user click the Submit button")
      checkYourAnswersPage.submit()

      And("The user navigate to confirmation page")
      bankDetailsErrorPage.checkJourneyUrl()

      Then("The Page Heading Text should be correct")
      bankDetailsErrorPage.pageHeadingText shouldBe "We could not verify your bank account details"

      And("The Error Heading Text should be correct")
      bankDetailsErrorPage.errorHeadingText shouldBe "An error occurred while attempting to verify your bank account details. This could be because:"

      And("The Bars Lock Out Page Error Text should be correct")
      bankDetailsErrorPage.verifyAllErrorMessages()

      When("The user click the Try again button")
      bankDetailsErrorPage.tryAgain()

      And("The user navigates to the Bank details Page")
      bankDetailsPage.checkJourneyUrl()

      And("The user click the Continue link")
      bankDetailsPage.continue()

      And("The user navigates to the Check Your Answers Page")
      checkYourAnswersPage.checkJourneyUrl()

      And("The summary list keys & values should be correct")
      checkYourAnswersPage.verifySummaryList("Test User", "12-34-56", "12345678")

      And("The user click the Submit button")
      checkYourAnswersPage.submit()

      And("The user navigate to Bank Details page")
      bankDetailsErrorPage.checkJourneyUrl()

      And("The Bars Lock Out Page Error Text should be correct")
      bankDetailsErrorPage.verifyAllErrorMessages()

      When("The user click the Try again button")
      bankDetailsErrorPage.tryAgain()

      And("The user navigates to the Bank details Page")
      bankDetailsPage.checkJourneyUrl()

      And("The user fill in the bank details and click continue")
      bankDetailsPage.submitBankDetails("Melvin Loper", "20-71-06", "44311677", Some("0123456789"))

      And("The user navigates to the Check Your Answers Page")
      checkYourAnswersPage.checkJourneyUrl()

      And("The summary list keys & values should be correct")
      checkYourAnswersPage.verifySummaryList("Melvin Loper", "20-71-06", "44311677", Some("0123456789"))

      And("The user click the Submit button")
      checkYourAnswersPage.submit()

      And("The user navigate to confirmation page")
      confirmationPage.checkJourneyUrl()
    }
  }
}
