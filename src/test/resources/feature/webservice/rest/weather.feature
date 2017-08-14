@all @webservice @REST
Feature: Get weather results based on the input of location

  @weather
  Scenario Outline: Send REST GET request to retrieve the weather results of a specific location
    Given I have a REST request of Weather
    When I send to the endpoint with <City>
    Then I should know the weather of <City> is between <Min> and <Max>

    Examples:
      | City       | Min | Max |
      | Wellington | 0   | 20  |
