@all @webservice @MOCK
Feature: Sample feature of a Wiremock service

  @sample
  Scenario Outline: Create mock service and verify it is up and working
    Given I start a mock service on <Port>
    When I create a <Type> stub of <Name> with <Status> <Value> <Params> and <Body>
    Then I <Type> the service through <Port> and <Name> <Value> <Params>
    And I should <Type> same <Name> <Status> <Value> <Params> and <Body>
    And I stop the mock service

    Examples:
      | Port | Type   | Name                  | Status | Value            | Params | Body      |
      | 8089 | Get    | greet                 | 200    | text/plain       |        | greet     |
      | 8090 | Delete | deleteGreet           | 200    |                  |        |           |
      | 8091 | Get    | greetXML              | 200    | application/xml  |        | greetXML  |
      | 8092 | Post   | postNothing           | 204    |                  |        |           |
      | 8093 | Get    | greetJSON             | 200    | application/json |        | greetJSON |
      | 8094 | Put    | putBadRequest         | 403    |                  |        |           |
      | 8095 | Get    | greetResponseTemplate | 200    | text/plain       | Hello  | greet     |
