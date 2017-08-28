# Introduction

![Build Status](https://travis-ci.org/DIZhang1109/gradle-groovy-playground.svg?branch=master)

This is a webservice test automation framework built based on Gradle and Groovy.

## Dependency

* Gradle - Build tool
* Groovy - Programming lang
* Cucumber - BDD tool
* Cucumber Pico - Dependency Injection (As I use Groovy, this library is not actually useful here)
* Cucumber Report - Beautiful Cucumber report
* JUnit - Main test tool
* Hamcrest
* wslite - Web Service tool
* Wiremock - Mock and Stub
* sl4j - Logging
* SnakeYAML - Data structure tool

Please find details in build.gradle

## Test

Unix
```
gradlew cucumberTest -Dcucumber.options='--tags @all'
```

Windows
```
gradlew.bat cucumberTest -Dcucumber.options="--tags @all"
```

## Test Scope

Basically, there are three parts I've been implemented in the tests:
1. SOAP
2. REST
3. Mock

The first two kinds of test are sending request, expecting response and assert the contents.
Mock test is to create local mock service firstly and then send request to it, verify if response is as expected.
 
## TODO

* Integrate JMeter into the framework
* Integrate Selenium/UI into the framework
* Optimize gradle build
* Optimize framework structure