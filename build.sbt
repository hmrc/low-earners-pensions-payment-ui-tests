lazy val root = (project in file("."))
  .settings(
    name := "low-earners-pensions-payment-ui-tests",
    version := "0.1.0",
    scalaVersion := "3.3.4",
    libraryDependencies ++= Dependencies.test,
    scalafmtOnCompile := true,
    (Compile / compile) := ((Compile / compile) dependsOn (Compile / scalafmtSbtCheck, Compile / scalafmtCheckAll)).value
  )
