@all @webservice @MOCK
Feature: Sample feature of a Wiremock service

  @sample
  Scenario Outline: Create mock service and verify it is up and working
    Given I start a mock service
    Then I stop the mock service

    Examples:
      | Test |
      | V1   |
