@webautomation @geb
Feature: Web Automation - Geb manual website
  As a user
  I want to interact with Currency REST service
  so as to know the currency information

  Scenario: Navigate to Geb manual website and assert its title
    Given GEB manual website
    When I visit it
    Then I should know the title is The Book Of Geb
