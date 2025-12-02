import sbt.*

object Dependencies {

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc" %% "ui-test-runner" % "0.51.0" % Test
  )

}
