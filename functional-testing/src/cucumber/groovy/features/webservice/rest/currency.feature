@webservice @rest
Feature: REST example - Currency
  As a user
  I want to interact with Currency REST service
  so as to know the currency information

  Background:
    Given there is a Currency REST service

  @currency
  Scenario Outline: Get the exchange rate of <Base Currency> to <Dest Currency>
    Given I want to exchange <Base Currency> with <Dest Currency>
    When I send request
    Then I should know exchange rate of <Dest Currency> is between <Min> and <Max>

    Examples:
      | Base Currency | Dest Currency | Min | Max |
      | NZD           | CNY           | 4.0 | 4.8 |
      | USD           | NZD           | 1.0 | 1.8 |
      | SGD           | CNY           | 4.5 | 5.0 |
