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

import java.net.URI
import scala.jdk.CollectionConverters.*

trait BasePage extends PageObject {

  protected def driver: WebDriver = Driver.instance
  protected val baseUrl: String   =
    TestEnvironment.url("low-earners-pensions-payment-frontend")

  protected val servicePath: String = new URI(baseUrl).getPath

  def currentUrl: String   = driver.getCurrentUrl
  def currentTitle: String = driver.getTitle

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
    val url = s"$baseUrl/$page"
    fluentWait.until(ExpectedConditions.urlContains(url))
    getCurrentUrl.startsWith(url)

  def goToPage(path: String): Unit =
    val url = s"$baseUrl/$path"
    get(url)

  def reportPageNotWorkingProperly(): Unit =
    click(pageNotWorkingLocator)

  // All rows from tbody
  def getTableRows(tableLocator: By): List[List[String]] =
    driver
      .findElements(tableLocator)
      .asScala
      .map { row =>
        row
          .findElements(By.cssSelector("th, td"))
          .asScala
          .map(_.getText.trim)
          .toList
      }
      .toList

  def getTableRowCount(tableLocator: By): Int =
    getTableRows(tableLocator).size

  def getTableHeaders(tableLocator: By): List[String] =
    driver
      .findElements(tableLocator) // locator should point at the th elements directly
      .asScala
      .map(_.getText.trim)
      .toList

  def getTableColumnCount(tableLocator: By): Int =
    getTableRows(tableLocator).size

  def hasElementInRow(tableLocator: By, rowIndex: Int, elementLocator: By): Boolean =
    driver
      .findElements(tableLocator)
      .asScala
      .apply(rowIndex)
      .findElements(elementLocator)
      .asScala
      .nonEmpty

  def getWindowCount: Int = driver.getWindowHandles.size

  /** Switches to a newly opened tab, executes a block (e.g. get URL, check title, assert content), and safely switches
    * focus back to the original window (optionally closing the new tab).
    */
  def switchToNewTabAndExecute[A](closeTab: Boolean = false)(block: => A): A = {
    val originalWindow = driver.getWindowHandle
    val newTab         = driver.getWindowHandles.asScala
      .find(_ != originalWindow)
      .getOrElse(throw new NoSuchElementException("No new tab found!"))

    driver.switchTo().window(newTab)
    try block // Executes driver.getCurrentUrl, driver.getTitle, or any assertion
    finally {
      if (closeTab) driver.close()
      driver.switchTo().window(originalWindow)
    }
  }

  /** Switches driver context to the newly opened tab and stays there.
    */
  def switchToNewTab(): Unit = {
    val originalWindow = driver.getWindowHandle
    val newTab         = driver.getWindowHandles.asScala
      .find(_ != originalWindow)
      .getOrElse(throw new NoSuchElementException("No new tab handle found!"))

    driver.switchTo().window(newTab)
  }

  /** Optional helper to switch back to the primary (first) window
    */
  def switchToMainWindow(): Unit = {
    val firstWindow = driver.getWindowHandles.asScala.headOption
      .getOrElse(throw new NoSuchElementException("No main window handle found!"))

    driver.switchTo().window(firstWindow)
  }

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
