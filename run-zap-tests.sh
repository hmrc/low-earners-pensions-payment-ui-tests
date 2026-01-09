#!/bin/bash
BROWSER=$1
ENVIRONMENT=$2

echo " ------------  Running UI Tests in preparation for ZAP Scan --------------------------- "
sbt -Denvironment=local -Dbrowser="${BROWSER:=chrome}" -Dbrowser.option.headless=false -Dzap.proxy=true "testOnly uk.gov.hmrc.ui.specs.*"

