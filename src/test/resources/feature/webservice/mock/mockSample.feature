@all @webservice @MOCK
Feature: Sample feature of a Wiremock service

  @sample
  Scenario Outline: Create mock service and verify it is up and working
    Given I start a mock service on <Port>
    When I create a get stub of <Name> with <Status> <Value> and <Body>
    Then I call the service through <Port> and <Name>
    And I should get same <Status> and <Body>
    And I stop the mock service

    Examples:
      | Port | Name  | Status | Value      | Body         |
      | 8089 | greet | 200    | text/plain | Hello World! |
