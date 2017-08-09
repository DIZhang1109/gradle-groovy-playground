@all @webservice
Feature: Get US Holiday based on year the user entered

  @SOAP
  Scenario Outline: Send SOAP request to get the date of US Holiday in a specific year
    Given I have a SOAP <Version> request of <Holiday>
    When I send to the endpoint with <Holiday> and <Year>
    Then I should know <Holiday> of <Year> is <Date>

    Examples:
      | Version | Holiday      | Year | Date  |
      | V1      | Easter       | 2017 | 04-16 |
      | V2      | Labor Day    | 2017 | 09-04 |
      | V1      | Black Friday | 2017 | 11-24 |
      | V2      | Memorial Day | 2017 | 05-29 |