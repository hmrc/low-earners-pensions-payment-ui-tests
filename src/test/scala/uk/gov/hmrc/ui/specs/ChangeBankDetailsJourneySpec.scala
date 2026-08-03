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

import uk.gov.hmrc.ui.pages.{Auth, BankDetailsPage, BreakdownPage, CheckYourAnswersPage, ConfirmationPage, DashboardPage, StartPage}

class ChangeBankDetailsJourneySpec extends BaseSpec {

  private val auth                 = Auth
  private val startPage            = StartPage
  private val dashboardPage        = DashboardPage
  private val breakdownPage        = BreakdownPage
  private val bankDetailsPage      = BankDetailsPage
  private val checkYourAnswersPage = CheckYourAnswersPage
  private val confirmationPage     = ConfirmationPage

  override def beforeEach(): Unit =
    super.beforeEach()
    Given("The user enters the auth details")
    auth.goToAuthorityWizard()
    auth.loginUsingAuthorityWizard()

    And("The user navigates to the Start Page")
    startPage.checkJourneyUrl()

    And("The user click the Continue button on Start page")
    startPage.continue()

    When("The user navigates to the Dashboard page")
    dashboardPage.checkJourneyUrl()

    And("The user click Accept Payments button")
    dashboardPage.clickActionButton()

    And("The user lands on the breakdown page")
    breakdownPage.checkJourneyUrl()

    And("The user click the Continue link")
    breakdownPage.continue()

    And("The user navigates to the Bank details page")
    bankDetailsPage.checkJourneyUrl()

    And("The user fill in the bank details and click continue")
    bankDetailsPage.submitBankDetails("Melvin Loper", "20-71-06", "44311677", Some("0123456789"))

    And("The user click the Submit button")
    checkYourAnswersPage.checkJourneyUrl()

  Feature(
    "As a PAYE individual I need to able to change the bank account details from the Check Your Answers Page"
  ) {

    Scenario(
      "Check Your Your Answers Page - Click Change Bank Account Name and modify Bank Account Name"
    ) {
      When("The user clicks Change on the Check Your Answers Page")
      CheckYourAnswersPage.clickChangeBankDetails()

      Then("The user should be taken to the bank details page")
      bankDetailsPage.currentUrl should include("change-bank-details")

      When("The user enter the new bank account name")
      bankDetailsPage.enterName("Casandra Wilkinson")

      And("The user click the Continue link")
      bankDetailsPage.continue()

      Then("The user navigates to the Check Your Answers Page")
      checkYourAnswersPage.checkJourneyUrl()

      And("The summary list keys & values should be correct")
      checkYourAnswersPage.verifySummaryList("Casandra Wilkinson", "20-71-06", "44311677", Some("0123456789"))
    }
    Scenario(
      "Check Your Your Answers Page - Click Change Bank Sort Code and modify Bank Sort Code"
    ) {
      When("The user clicks Change on the Check Your Answers Page")
      CheckYourAnswersPage.clickChangeBankDetails()

      Then("The user should be taken to the bank details page")
      bankDetailsPage.currentUrl should include("change-bank-details")

      When("The user enter the new bank sort code")
      bankDetailsPage.enterSortCode("40-51-25")

      And("The user click the Continue link")
      bankDetailsPage.continue()

      Then("The user navigates to the Check Your Answers Page")
      checkYourAnswersPage.checkJourneyUrl()

      And("The summary list keys & values should be correct")
      checkYourAnswersPage.verifySummaryList("Melvin Loper", "40-51-25", "44311677", Some("0123456789"))
    }
    Scenario(
      "Check Your Your Answers Page - Click Change Account Number and modify Bank Account Number"
    ) {
      When("The user clicks Change on the Check Your Answers Page")
      CheckYourAnswersPage.clickChangeBankDetails()

      Then("The user should be taken to the bank details page")
      bankDetailsPage.currentUrl should include("change-bank-details")

      When("The user enter the new bank bank account number")
      bankDetailsPage.enterAccountNumber("54344677")

      And("The user click the Continue link")
      bankDetailsPage.continue()

      Then("The user navigates to the Check Your Answers Page")
      checkYourAnswersPage.checkJourneyUrl()

      And("The summary list keys & values should be correct")
      checkYourAnswersPage.verifySummaryList("Melvin Loper", "20-71-06", "54344677", Some("0123456789"))
    }
    Scenario(
      "Check Your Your Answers Page - Click Building society roll number and modify Building society roll number"
    ) {
      When("The user clicks Change on the Check Your Answers Page")
      CheckYourAnswersPage.clickChangeBankDetails()

      Then("The user should be taken to the bank details page")
      bankDetailsPage.currentUrl should include("change-bank-details")

      When("The user enter the new Building society roll number")
      bankDetailsPage.enterBuildingSocietyRollNumber("1011121314")

      And("The user click the Continue link")
      bankDetailsPage.continue()

      Then("The user navigates to the Check Your Answers Page")
      checkYourAnswersPage.checkJourneyUrl()

      And("The summary list keys & values should be correct")
      checkYourAnswersPage.verifySummaryList("Melvin Loper", "20-71-06", "44311677", Some("1011121314"))

      And("The user click the Submit button")
      checkYourAnswersPage.submit()

      And("The user navigate to confirmation page")
      confirmationPage.checkJourneyUrl()
    }
    Scenario(
      "Check Your Your Answers Page - Click Building society roll number and remove Building society roll number"
    ) {
      When("The user clicks Change on the Check Your Answers Page")
      CheckYourAnswersPage.clickChangeBankDetails()

      Then("The user should be taken to the bank details page")
      bankDetailsPage.currentUrl should include("change-bank-details")

      When("The user removes the Building society roll number")
      bankDetailsPage.clearBuildingSocietyRollNumber

      And("The user click the Continue link")
      bankDetailsPage.continue()

      Then("The user navigates to the Check Your Answers Page")
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
