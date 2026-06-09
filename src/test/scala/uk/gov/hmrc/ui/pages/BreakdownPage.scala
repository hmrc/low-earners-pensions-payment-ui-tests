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

package uk.gov.hmrc.ui.pages

import org.openqa.selenium.By
import scala.jdk.CollectionConverters.*

object BreakdownPage extends BasePage {

  private val path                   = "breakdown"
  // Page heading
  private val pageHeadingLocator: By =
    By.cssSelector("h1.govuk-heading-l")

  // Body paragraphs
  private val eligibilityBodyTextLocator: By =
    By.cssSelector("p.govuk-body:nth-of-type(1)")

  private val bankDetailsTextLocator: By =
    By.cssSelector("p.govuk-body:nth-of-type(3)")

  private val insetTextBlocksLocator: By =
    By.cssSelector("div.govuk-inset-text")

  private val insetTextHeadingsLocator: By =
    By.cssSelector("div.govuk-inset-text h2.govuk-heading-m")

  private val continueButton: By = By.linkText("Continue")

  def continue(): Unit =
    click(continueButton)

  def checkJourneyUrl(): Unit =
    super.checkJourneyUrl(path)

  def pageHeadingText: String = getText(pageHeadingLocator)

  def eligibilityBodyText: String = getText(eligibilityBodyTextLocator)

  def bankDetailsText: String = getText(bankDetailsTextLocator)

  // Get all inset blocks as a list
  def insetTextBlocks: List[String] =
    driver
      .findElements(insetTextBlocksLocator)
      .asScala
      .map(_.getText.trim)
      .toList

  // Get specific block by index
  def insetTextBlock(index: Int): String =
    insetTextBlocks(index)

  // Get tax year headings
  def taxYearHeadings: List[String] =
    driver
      .findElements(insetTextHeadingsLocator)
      .asScala
      .map(_.getText.trim)
      .toList

  def verifyInsetBlock(
    index: Int,
    taxYear: String,
    contributions: String,
    taxRate: String,
    payment: String
  ): Unit =
    assert(taxYearHeadings(index) == taxYear)
    assert(insetTextBlock(index).contains(s"Your net pay pension contributions: $contributions"))
    assert(insetTextBlock(index).contains(s"Your relevant basic tax rate: $taxRate"))
    assert(insetTextBlock(index).contains(s"Your payment: $payment"))
}
