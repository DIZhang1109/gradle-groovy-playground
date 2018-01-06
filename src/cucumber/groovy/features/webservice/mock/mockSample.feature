@webservice @mock
Feature: Mock and Stub example
  As a user
  I want to interact with localhost MOCK service
  so as to know the mock service is okay

  Background:
    Given there is a Mock REST service

  @sample
  Scenario Outline: Verify <Name> mock service is running okay
    Given a <Name> request of <Status>
    When I send request
    Then verify the request of <Name>
    And <Status> is expected

    Examples:
      | Name           | Status |
      | greet          | 200    |
      | notFound       | 404    |
      | internalError  | 500    |
      | notImplemented | 501    |
