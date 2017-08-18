@all @webservice @MOCK
Feature: Sample feature of a Wiremock service

  @sample
  Scenario Outline: Create mock service and verify it is up and working
    Given I start a mock service on <Port>
    When I create a <Type> stub of <Name> with <Status> <Value> and <Body>
    Then I <Type> the service through <Port> and <Name>
    And I should get same <Status> and <Body>
    And I stop the mock service

    Examples:
      | Port | Type   | Name          | Status | Value            | Body      |
      | 8089 | Get    | greet         | 200    | text/plain       | greet     |
      | 8090 | Delete | deleteGreet   | 200    |                  |           |
      | 8091 | Get    | greetXML      | 200    | application/xml  | greetXML  |
      | 8092 | Post   | postNothing   | 204    |                  |           |
      | 8093 | Get    | greetJSON     | 200    | application/json | greetJSON |
      | 8094 | Put    | putBadRequest | 403    |                  |           |
