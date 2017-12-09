@webservice @soap
Feature: Web service security - wss4j
  As a user
  I want to implement security header to Soap request
  so as to know message has been signed

  @wssecurity
  Scenario Outline: Implement web service <Type> security header
    Given a SOAP request to be signed
    When I apply security header <Type> to it
    Then I should get a signed <Type> SOAP request

    Examples:
      | Type       |
      | Encryption |
      | Signature  |
      | Timestamp  |
      | Username   |
      | All        |
