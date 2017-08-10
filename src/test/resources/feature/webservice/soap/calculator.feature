@all @webservice @SOAP
  Feature: Get math results based on the input of calculator

  @calculator
  Scenario Outline: Send SOAP request to get the math results of calculator
    Given I have a SOAP <Version> request of Calculator
    When I <Action> <Number 1> by <Number 2>
    Then I should get <Action> result: <Result>

    Examples:
      | Version | Action   | Number 1 | Number 2 | Result |
      | V1      | Add      | 1        | 29       | 30     |
      | V2      | Subtract | 6        | 5        | 1      |
      | V1      | Multiply | 38       | 5        | 190    |
      | V2      | Divide   | 100      | 2        | 50     |
