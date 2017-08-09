package feature.webservice.soap

import webservices.soap.SoapService
import wslite.soap.SOAPResponse

import static cucumber.api.groovy.EN.Given
import static cucumber.api.groovy.EN.Then
import static cucumber.api.groovy.EN.When
import static cucumber.api.groovy.Hooks.Before
import static org.junit.Assert.assertThat
import static org.hamcrest.CoreMatchers.is

/**
 * Created by Di on 9/08/17.
 * holidaySoap.feature step definitions
 */

SoapService soapService
SOAPResponse response

Before() {
    soapService = new SoapService()
}

Given(~/^I have a SOAP request of (.*)$/) { holiday ->
    soapService.initiateSOAPClient()
}

When(~/^I send to the endpoint with (.+) and (.+)$/) { holiday, searchYear ->
    response = soapService.getSOAPResponse getHoliday(holiday), searchYear
}

Then(~/^I should know (.+) of (.+) is (.*)$/) { holiday, year, date ->
    println response
    def responseName = getHoliday(holiday) + 'Response'
    def responseResult = getHoliday(holiday) + 'Result'

    assertThat response.httpResponse.statusCode, is(200)
    assertThat response."$responseName"."$responseResult".text().take(4), is(year)
    assertThat response."$responseName"."$responseResult".text()[5..9], is(date)
}

/**
 * Return request name like 'Get...'
 * @param holiday the feature.webservice name
 * @return request
 */
static String getHoliday(holiday) {
    'Get' + holiday.replaceAll('\\s', '')
}