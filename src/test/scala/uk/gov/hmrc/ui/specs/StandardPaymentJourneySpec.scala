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

import uk.gov.hmrc.ui.pages.{Auth, UserDetails}

class StandardPaymentJourneySpec extends BaseSpec {

  private val auth = Auth
  private val memberDetails = UserDetails

  Feature("As a PAYE individual I need to claim the low income pension payment and view the status of payment for Standard Payment Journey") {

    Scenario(
      "Standard Payment Journey - Submit the bank account details and Navigate to confirmation page"
    ) {

      Given("I fill in the auth details for enrolmentID with value enrolmentValue")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("")

      When("I click the Start now link")
      memberDetails.checkJourneyUrl("start")
      memberDetails.startNow()

      And("I click the View Payment link")
      memberDetails.checkJourneyUrl("dashboard")
      memberDetails.viewPayment()

      And("I click the Continue link")
      memberDetails.checkJourneyUrl("breakdown")
      memberDetails.continue()

      And("I fill in the bank details")
      memberDetails.checkJourneyUrl("bank-details")
      memberDetails.enterName("Teddy", "Sherringham")
      memberDetails.enterSortCode("55-00-33")
      memberDetails.enterAccountNumber("12345678")
      memberDetails.enterBuildingSocietyRollNumber("0123456789")

      And("I click the Submit button")
      memberDetails.checkJourneyUrl("check-your-answers")
      memberDetails.submit()

      Then("I should be on Confirmation page")
      memberDetails.checkJourneyUrl("results")

    }
  }
}