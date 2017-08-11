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
 * Created by zhangd on 11/08/2017.
 * holidayService2.feature step definitions
 */

SoapService soapService
SOAPResponse response
def soapVersion

Before() {
    soapService = new SoapService()
}

Given(~/^I have a SOAP (.+) request of Holiday Service 2$/) { version ->
    soapService.initiateHolidayService2SOAPClient()
    soapVersion = version
}

When(~/^I send to the endpoint with (\w+) in SOAP (.+)$/) { name, version ->
    response = soapService.getHolidayService2Response version, name
}

Then(~/^I should get all the countries available$/) { ->
    println response.text

    assertThat response.httpResponse.statusCode, is(200)
}