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

class InEligibleJourneySpec extends BaseSpec {

  private val auth = Auth
  private val inEligiblePage = InEligiblePage

  Feature(
    "As a Ineligible PAYE individual I won't be able to access the service"
  ) {

    Scenario(
      "Ineligible page will be displayed and the user can return to PTA Page"
    ) {

      Given("The user enters the auth details")
      auth.goToAuthorityWizard()
      auth.loginForInEligibleAccountJourney()

      When("The user will be navigated to the Ineligible Page")
      inEligiblePage.checkJourneyUrl()

      Then("The Page Heading Text should be correct")
      inEligiblePage.pageHeadingText shouldBe "You cannot use this service"

      And("The new tab should have the correct title")
      inEligiblePage.assertTitle(inEligiblePage.pageHeadingText + " - Accept your low earner's pension payment - GOV.UK")

      And("The Page Text should be correct")
      inEligiblePage.paragraphBodyText shouldBe "You are not eligible for the low earner's pension payment."

      And("The Return To PTA Page Text should be correct")
      inEligiblePage.returnToPTAText shouldBe "Return to your personal tax account"

      When("The user clicks the return to PTA link")
      inEligiblePage.clickReturnToPtaLink()

      Then("The user will be navigated to the PTA Page")
      inEligiblePage.goToPTAPageFromInEligiblePageandValidate()
    }
  }
}