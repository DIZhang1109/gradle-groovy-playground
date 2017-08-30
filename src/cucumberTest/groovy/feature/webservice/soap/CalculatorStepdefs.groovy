package feature.webservice.soap

import static cucumber.api.groovy.EN.Given
import static cucumber.api.groovy.EN.Then
import static cucumber.api.groovy.EN.When
import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

/**
 * Created by zhangd on 10/08/2017.
 * calculator.feature step definitions
 */

Given(~/^I have a SOAP (.+) request of Calculator$/) { verison ->
    initiateCalculatorSOAPClient()
    soapVersion = verison
}

When(~/^I (\w+) (\d+) by (\d+)$/) { action, digit1, digit2 ->
    soapResponse = (soapVersion == 'V1') ? getCalculatorSOAPV1Response(action, digit1, digit2) : getCalculatorSOAPV2Response(action, digit1, digit2)
}

Then(~/^I should get (.+) result: (\d+)$/) { action, result ->
    def responseName = "${action}Response"
    def responseResult = "${action}Result"

    assertThat soapResponse.httpResponse.statusCode, is(200)
    assertThat soapResponse."$responseName"."$responseResult".text(), is(result)
}