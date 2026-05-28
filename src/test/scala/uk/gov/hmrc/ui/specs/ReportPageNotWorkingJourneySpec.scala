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

class ReportPageNotWorkingJourneySpec extends BaseSpec {
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

    And("The user navigates to the Start Page")
    startPage.checkJourneyUrl()

  Feature(
    "As a PAYE individual I need to report when the page is not working properly"
  ) {

    Scenario(
      "Start Page - Report page not working properly"
    ) {
      When("The user reports page not working properly")
      startPage.reportPageNotWorkingProperly()

      Then("A new tab should open")
      startPage.getWindowCount shouldBe 2

      And("The new tab should have the correct title")
      startPage.newTabTitle shouldBe "Get help with a technical problem – Contact HMRC – GOV.UK"

      And("The new tab should have the correct URL")
      startPage.newTabUrl should include("contact/report-technical-problem")
    }

    Scenario(
      "Dashboard Page - Report page not working properly"
    ) {
      And("The user click the Continue button on Start Page")
      startPage.continue()

      And("The user navigates to the Dashboard Page")
      dashboardPage.checkJourneyUrl()

      When("The user reports page not working properly")
      dashboardPage.reportPageNotWorkingProperly()

      Then("A new tab should open")
      dashboardPage.getWindowCount shouldBe 2

      And("The new tab should have the correct title")
      dashboardPage.newTabTitle shouldBe "Get help with a technical problem – Contact HMRC – GOV.UK"

      And("The new tab should have the correct URL")
      dashboardPage.newTabUrl should include("contact/report-technical-problem")
    }

    Scenario(
      "Breakdown Page - Report page not working properly"
    ) {
      And("The user click the Continue button on Start Page")
      startPage.continue()

      And("The user click the Continue button on Dashboard page")
      dashboardPage.checkJourneyUrl()

      And("The user click Accept Payments button")
      dashboardPage.acceptPayments()

      And("The user navigates to the Breakdown Page")
      breakdownPage.checkJourneyUrl()

      When("The user reports page not working properly")
      breakdownPage.reportPageNotWorkingProperly()

      Then("A new tab should open")
      breakdownPage.getWindowCount shouldBe 2

      And("The new tab should have the correct title")
      breakdownPage.newTabTitle shouldBe "Get help with a technical problem – Contact HMRC – GOV.UK"

      And("The new tab should have the correct URL")
      breakdownPage.newTabUrl should include("contact/report-technical-problem")
    }

    Scenario(
      "Bank Details Page - Report page not working properly"
    ) {
      And("The user click the Continue button on Start Page")
      startPage.continue()

      And("The user click the Continue button on Dashboard page")
      dashboardPage.checkJourneyUrl()

      And("The user click Accept Payments button")
      dashboardPage.acceptPayments()

      And("The user lands on the breakdown page")
      breakdownPage.checkJourneyUrl()

      And("The user click the Continue link")
      breakdownPage.continue()

      And("The user navigates to the Bank Details Page")
      bankDetailsPage.checkJourneyUrl()

      When("The user reports page not working properly")
      bankDetailsPage.reportPageNotWorkingProperly()

      Then("A new tab should open")
      bankDetailsPage.getWindowCount shouldBe 2

      And("The new tab should have the correct title")
      bankDetailsPage.newTabTitle shouldBe "Get help with a technical problem – Contact HMRC – GOV.UK"

      And("The new tab should have the correct URL")
      bankDetailsPage.newTabUrl should include("contact/report-technical-problem")
    }

    Scenario(
     "Check Your Answers Page - Report page not working properly"
    ) {
      And("The user click the Continue button on Start Page")
      startPage.continue()

      And("The user click the Continue button on Dashboard page")
      dashboardPage.checkJourneyUrl()

      And("The user click Accept Payments button")
      dashboardPage.acceptPayments()

      And("The user lands on the breakdown page")
      breakdownPage.checkJourneyUrl()

      And("The user click the Continue link")
      breakdownPage.continue()

      And("The user fill in the bank details")
      bankDetailsPage.checkJourneyUrl()
      bankDetailsPage.enterName("Melvin Loper")
      bankDetailsPage.enterSortCode("20-71-06")
      bankDetailsPage.enterAccountNumber("44311677")
      bankDetailsPage.enterBuildingSocietyRollNumber("0123456789")

      And("The user click the Continue link")
      bankDetailsPage.continue()

      And("The user navigates to the Check Your Answers Page")
      checkYourAnswersPage.checkJourneyUrl()

      When("The user reports page not working properly")
      checkYourAnswersPage.reportPageNotWorkingProperly()

      Then("A new tab should open")
      checkYourAnswersPage.getWindowCount shouldBe 2

      And("The new tab should have the correct title")
      checkYourAnswersPage.newTabTitle shouldBe "Get help with a technical problem – Contact HMRC – GOV.UK"

      And("The new tab should have the correct URL")
      checkYourAnswersPage.newTabUrl should include("contact/report-technical-problem")
    }

    Scenario(
      "Confirmation Page - Report page not working properly"
    ) {
      And("The user click the Continue button on Start Page")
      startPage.continue()

      And("The user click the Continue button on Dashboard page")
      dashboardPage.checkJourneyUrl()

      And("The user click Accept Payments button")
      dashboardPage.acceptPayments()

      And("The user lands on the breakdown page")
      breakdownPage.checkJourneyUrl()

      And("The user click the Continue link")
      breakdownPage.continue()

      And("The user fill in the bank details")
      bankDetailsPage.checkJourneyUrl()
      bankDetailsPage.enterName("Melvin Loper")
      bankDetailsPage.enterSortCode("20-71-06")
      bankDetailsPage.enterAccountNumber("44311677")
      bankDetailsPage.enterBuildingSocietyRollNumber("0123456789")

      And("The user click the Continue link")
      bankDetailsPage.continue()

      And("The user navigates to the Check Your Answers Page")
      checkYourAnswersPage.checkJourneyUrl()

      And("The user click the Submit button")
      checkYourAnswersPage.submit()

      And("The user navigates to the Confirmation page")
      confirmationPage.checkJourneyUrl()

      When("The user reports page not working properly")
      confirmationPage.reportPageNotWorkingProperly()

      Then("A new tab should open")
      confirmationPage.getWindowCount shouldBe 2

      And("The new tab should have the correct title")
      confirmationPage.newTabTitle shouldBe "Get help with a technical problem – Contact HMRC – GOV.UK"

      And("The new tab should have the correct URL")
      confirmationPage.newTabUrl should include("contact/report-technical-problem")
    }
  }
}