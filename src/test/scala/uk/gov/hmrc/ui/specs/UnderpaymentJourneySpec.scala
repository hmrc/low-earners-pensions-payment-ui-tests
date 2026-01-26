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

class UnderpaymentJourneySpec extends BaseSpec {

  private val auth = Auth
//private val memberDetails = UserDetails

  Feature(
    "As a PAYE Individual I need to claim the low income pension payment and view the status of payment for Underpayment journey"
  ) {

    Scenario(
      "Underpayment Journey - Submit the bank account details and Navigate to confirmation page"
    ) {

      Given("I fill in the auth details")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard()

    }
  }
}
