@all @webservice @soap
Feature: Get Black Friday based on year the user entered

  Scenario Outline: Send SOAP request to get the date of Black Friday in a specific year
    Given I have a SOAP request of <Holiday>
    When I send to the endpoint with <Holiday> and <Year>
    Then I should know <Holiday> of <Year> is <Date>

    Examples:
      | Holiday      | Year | Date  |
      | Easter       | 2017 | 04-16 |
      | Labor Day    | 2017 | 09-04 |
      | Black Friday | 2017 | 11-24 |