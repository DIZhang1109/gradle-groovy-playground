@webautomation @geb
Feature: Web Automation - Geb manual website
  As a user
  I want to interact with Currency REST service
  so as to know the currency information

  Scenario: Navigate to Geb manual website and assert its title
    Given I am on the Geb home page
    Then the first heading on the page is 'What is it?'
    When the link to documentation is clicked
    Then I end up at The Book of Geb
