@webservice @soap
Feature: SOAP example - US Holiday dates
  As a user
  I want to interact with USHolidayDates SOAP service
  so as to know national holidays dates in US

  Background:
    Given there is a USHolidayDates SOAP service

  @usholidaydates
  Scenario Outline: Get the date of a US Holiday <Holiday> in <Year>
    Given a US holiday <Holiday> of <Year>
    When I send request
    Then I should know <Holiday> of <Year> is <Date>

    Examples:
      | Holiday                     | Year | Date  |
      | Easter                      | 2017 | 04-16 |
      | Labor Day                   | 2015 | 09-07 |
      | Black Friday                | 2016 | 11-25 |
      | Memorial Day                | 2014 | 05-26 |
      | George Washingtons Birthday | 2013 | 02-22 |
      | Saint Patricks Day          | 2012 | 03-17 |