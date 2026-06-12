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

package uk.gov.hmrc.ui.pages

import uk.gov.hmrc.selenium.component.PageObject
import uk.gov.hmrc.selenium.webdriver.Driver
import java.time.Duration
import org.openqa.selenium.support.ui.{ExpectedConditions, FluentWait, Wait}
import uk.gov.hmrc.configuration.TestEnvironment
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import scala.jdk.CollectionConverters._

trait BasePage extends PageObject {

  protected def driver: WebDriver  = Driver.instance
  private val dashboardUrl: String =
    TestEnvironment.url("low-earners-pensions-payment-frontend")

  private val pageNotWorkingLocator: By = By.linkText("Is this page not working properly? (opens in new tab)")
  private val backButtonLocator: By     = By.linkText("Back")
  private val signOutLocator: By        = By.linkText("Sign out")

  def fluentWait: Wait[WebDriver] = new FluentWait[WebDriver](Driver.instance)
    .withTimeout(Duration.ofSeconds(3))
    .pollingEvery(Duration.ofMillis(200))

  def waitForElement(by: By): Unit =
    new FluentWait(Driver.instance).until(ExpectedConditions.presenceOfElementLocated(by))

  def assertTitle(expectedPageTitle: String): Unit =
    fluentWait.until(ExpectedConditions.titleIs(expectedPageTitle))

  def checkJourneyUrl(page: String): Unit =
    val url = s"$dashboardUrl/$page"
    fluentWait.until(ExpectedConditions.urlContains(url))
    getCurrentUrl.startsWith(url)

  def goToPage(path: String): Unit =
    val url = s"$dashboardUrl/$path"
    get(url)

  // All rows from tbody
  def getTableRows(tableLocator: By): List[List[String]] =
    driver
      .findElements(tableLocator)
      .asScala
      .map { row =>
        row
          .findElements(By.tagName("td"))
          .asScala
          .map(_.getText.trim)
          .toList
      }
      .toList

  def getTableRowCount(tableLocator: By): Int =
    getTableRows(tableLocator).size

  def getTableColumnCount(tableLocator: By): Int =
    getTableRows(tableLocator).headOption
      .map(_.size)
      .getOrElse(0)

  def hasElementInRow(tableLocator: By, rowIndex: Int, elementLocator: By): Boolean =
    driver
      .findElements(tableLocator)
      .asScala
      .apply(rowIndex)
      .findElements(elementLocator)
      .asScala
      .nonEmpty

  def clickLinkAndVerifyNewTab(locator: By): Unit =
    val originalWindow = driver.getWindowHandle
    driver.findElement(locator).click()

    // Wait for new tab to open
    fluentWait.until(ExpectedConditions.numberOfWindowsToBe(2))

    // Switch to new tab
    driver.getWindowHandles.asScala
      .filterNot(_ == originalWindow)
      .foreach(driver.switchTo().window)

    // Switch back to original tab
    driver.switchTo().window(originalWindow)

  def getWindowCount: Int = driver.getWindowHandles.size

  def reportPageNotWorkingProperly(): Unit =
    clickLinkAndVerifyNewTab(pageNotWorkingLocator)

  def newTabUrl: String =
    val originalWindow = driver.getWindowHandle
    val newTab         = driver.getWindowHandles.asScala
      .filterNot(_ == originalWindow)
      .head
    driver.switchTo().window(newTab)
    val url            = driver.getCurrentUrl
    driver.switchTo().window(originalWindow)
    url

  def newTabTitle: String =
    val originalWindow = driver.getWindowHandle
    val newTab         = driver.getWindowHandles.asScala
      .filterNot(_ == originalWindow)
      .head
    driver.switchTo().window(newTab)
    val title          = driver.getTitle
    driver.switchTo().window(originalWindow)
    title

  def goBackToPreviousPage(): Unit =
    click(backButtonLocator)

  def clearField(locator: By): Unit =
    driver.findElement(locator).clear()

  def isElementPresent(locator: By): Boolean =
    try
      driver.findElement(locator)
      true
    catch case _: NoSuchElementException => false

  def setText(locator: By, textValue: String): Unit = {
    clearField(locator)
    sendKeys(locator, textValue)
  }

  def signOut(): Unit =
    click(signOutLocator)
}
