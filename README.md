# Android testing with UiAutomator

## In this project we use:

## [Kotlin](https://kotlinlang.org/docs/reference/)

## [UiAutomator](https://developer.android.com/training/testing/ui-automator)

## [Gradle](https://gradle.org/)

## [Junit](https://junit.org/junit4/)

## [Hamcrest](http://www.vogella.com/tutorials/Hamcrest/article.html)

## [Fork](https://github.com/shazam/fork)

## [JenkinsJob](https://j.metromile.com/view/QA-mobile/job/AndroidUiAutomator/configure)

# Description:

 All test will be running randomly on real devices, time of running can be decreased by adding new phone. So the time of running can be really fast.

Each fail test will be rerun on another phone to make sure if the problem is not with the device or any outside conditions was caused by fail.

'Fork' tool helps to make this possible everything is written in build.gradle in app package (not in metromile) with Groovy (not best language). Also for each fail test, we have a video record to investigate why that bug happened

Also, we use the Page Object model. So each screen is a separate object where provide all possible interaction with that screen



