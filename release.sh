#!/bin/sh
if [ $# -lt 1 ]
then
  echo "Usage: release.sh [version-number]"
  exit
fi
jar cf firebase-gwt-$1.jar gwt/
zip -r firebase-objc-$1.zip objc/
