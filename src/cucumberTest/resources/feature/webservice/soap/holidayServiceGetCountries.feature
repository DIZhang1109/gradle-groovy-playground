@all @webservice @SOAP
Feature: GetCountriesAvailable service is available in HolidayService2

  @holidayService2
  Scenario Outline: Get Countries Available
    Given I have a SOAP <Version> request of Holiday Service 2
    When I send to the endpoint with <Request> in SOAP <Version>
    Then I should get all the countries available

    Examples:
      | Version | Request               |
      | V1      | GetCountriesAvailable |