@webservice @rest
Feature:
  As a user
  I want to interact with Weather REST service
  so as to know the weather result of a specific location

  Background:
    Given there is a Weather REST service

  @weather
  Scenario Outline: Get the weather results of a <City>
    Given a <City> to look up
    When I send request
    Then I should know the current temperature is between <Min>°C and <Max>°C

    Examples:
      | City       | Min | Max |
      | Wellington | 10  | 25  |
      | Shanghai   | 0   | 15  |
      | Auckland   | 10  | 30  |