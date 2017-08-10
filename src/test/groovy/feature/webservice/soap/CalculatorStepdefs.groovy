package feature.webservice.soap

import webservices.soap.SoapService
import wslite.soap.SOAPResponse

import static cucumber.api.groovy.EN.Given
import static cucumber.api.groovy.EN.Then
import static cucumber.api.groovy.EN.When
import static cucumber.api.groovy.Hooks.Before
import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

/**
 * Created by zhangd on 10/08/2017.
 * calculator.feature step definitions
 */

SoapService soapService
SOAPResponse response
def soapVersion

Before() {
    soapService = new SoapService()
}

Given(~/^I have a SOAP (.*) request of Calculator$/) { verison ->
    soapService.initiateCalculatorSOAPClient()
    soapVersion = verison
}

When(~/^I (.*) (.+) by (.+)$/) { action, digit1, digit2 ->
    response = (soapVersion == 'V1') ? soapService.getCalculatorSOAPV1Response(action, digit1, digit2) : soapService.getCalculatorSOAPV2Response(action, digit1, digit2)
}

Then(~/^I should get (.+) result: (.+)$/) { action, result ->
    def responseName = "${action}Response"
    def responseResult = "${action}Result"

    assertThat response.httpResponse.statusCode, is(200)
    assertThat response."$responseName"."$responseResult".text(), is(result)
}