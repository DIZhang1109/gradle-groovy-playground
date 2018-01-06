@webservice @soap
Feature: SOAP example - Calculator
  As a user
  I want to interact with Calculator SOAP service
  so as to know the math result of two numbers

  Background:
    Given there is a Calculator SOAP service

  @calculator
  Scenario Outline: Get the math results of calculator
    Given <Number 1> <Action> <Number 2>
    When I send request
    Then I should know the math result of <Action> is <Result>

    Examples:
      | Action   | Number 1 | Number 2 | Result |
      | Add      | 1        | 29       | 30     |
      | Subtract | 6        | 5        | 1      |
      | Multiply | 38       | 5        | 190    |
      | Divide   | 100      | 2        | 50     |
