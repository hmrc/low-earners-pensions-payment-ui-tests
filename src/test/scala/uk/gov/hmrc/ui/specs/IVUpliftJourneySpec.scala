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
import uk.gov.hmrc.ui.pages.IdentityVerificationStub
import uk.gov.hmrc.ui.pages.UserDetails

class IVUpliftJourneySpec extends BaseSpec {
  private val auth        = Auth
  private val ivStub      = IdentityVerificationStub
  private val userDetails = UserDetails

  Feature(
    "As a PAYE individual, I must be able to use the IV uplift feature, so that I can access the service successfully\n"
  ) {

    Scenario("Verify the Positive Uplift Journey") {
      Given(
        "A PAYE individual logs into the LEPP using Cl200 and valid NINO and Identity Verification check is successful"
      )
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizardWithCL200()

      Then("The user should be on Identity Verification Stub Page")
      ivStub.verifyGOVUKPageTitle("Identity Verification - Stubbed Journey configuration")

      And("When the User select Success Radio button he is navigated to Start Page")
      ivStub.submitContinue()
      userDetails.verifyGOVUKPageTitle(
        "Accept your low earner's pension payment - Accept your low earner's pension payment - site.govuk"
      )
    }
  }

  Scenario("Verify the Negative Uplift Journey") {
    Given("A PAYE individual logs into the LEPP using Cl200 and valid NINO but fails Identity Verification check")
    auth.goToAuthorityWizard()
    auth.loginUsingAuthorityWizardWithCL200()

    Then("The user should be on Identity Verification Stub Page")
    ivStub.verifyGOVUKPageTitle("Identity Verification - Stubbed Journey configuration")

    And("When the User select Failed IV Radio button he is navigated to Error Page")
    ivStub.clickRadioButton("Precondition Failed")
    ivStub.submitContinue()
    userDetails.verifyGOVUKPageTitle("There's a problem - Accept your low earner's pension payment - site.govuk")
  }
}
