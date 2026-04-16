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

import uk.gov.hmrc.ui.pages.Auth
import uk.gov.hmrc.ui.pages.UserDetails

class StandardPaymentJourneySpec extends BaseSpec {

  private val auth        = Auth
  private val userDetails = UserDetails

  Feature(
    "As a PAYE individual I need to claim the low income pension payment and view the status of payment for Standard Payment Journey"
  ) {

    Scenario(
      "Standard Payment Journey - Submit the bank account details and Navigate to confirmation page"
    ) {

      Given("User enters the auth details")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard()

      When("I click the Continue button")
      userDetails.checkJourneyUrl("start")
      userDetails.continue()

      And("I click the Continue link")
      userDetails.checkJourneyUrl("breakdown")
      userDetails.continue()

      And("I fill in the bank details")
      userDetails.checkJourneyUrl("bank-details")
      userDetails.enterName("Teddy Sherringham")
      userDetails.enterSortCode("55-00-33")
      userDetails.enterAccountNumber("12345678")
      userDetails.enterBuildingSocietyRollNumber("0123456789")
      userDetails.continue()

      And("I click the Submit button")
      userDetails.checkJourneyUrl("check-your-answers")
      userDetails.continue()
    }
  }
}
