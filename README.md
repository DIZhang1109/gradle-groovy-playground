# Introduction

[![Build Status](https://travis-ci.org/DIZhang1109/gradle-groovy-playground.svg?branch=master)](https://travis-ci.org/DIZhang1109/gradle-groovy-playground)

This is a test automation framework built based on Gradle and Groovy.

## Dependency

* gradle - Build tool
* groovy - Programming language
* cucumber - BDD tool
* junit/hamcrest - Test framework
* groovy-wslite - Web service
* wiremock - Mock and stub
* jsch - SFTP
* wss4j - Web service security for java
* geb - Groovy web automation
* sl4j - Logging
* snakeYaml - Data structure tool

Please find details in build.gradle

## Test

Unix
```
gradlew cucumberTest -Dcucumber.options='-tags @soap'
```

Windows
```
gradlew.bat cucumberTest
```

## Test Scope

Basically, there are six parts below I've been implemented in the tests:
1. SOAP
2. REST
3. Mock
4. SFTP
5. Wss4j (SOAP)
6. Web automation
 
## TODO

* Integrate Gatling into the framework
* Complete web automation part
