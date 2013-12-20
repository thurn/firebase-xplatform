#!/bin/sh
if [ $# -lt 1 ]
then
  echo "Usage: release.sh [version-number]"
  exit
fi
zip -r firebase-objc-$1.zip firebase-objc/
