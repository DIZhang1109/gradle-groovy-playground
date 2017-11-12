package stepdefs.webservice.soap

import static cucumber.api.groovy.EN.Given
import static cucumber.api.groovy.EN.Then
import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

/**
 * Created by zhangd on 10/08/2017.
 * calculator.feature step definitions
 */

Given(~/^(\d+) (\w+) (\d+)$/) { int num1, String action, int num2 ->
    soapRequest = getPayload(serviceName, ['action': action, 'num1': num1, 'num2': num2])
}

Then(~/^I should know the math result of (\w+) is (\d+)$/) { String action, int result ->
    writeSOAPResponse(soapResponse.text)
    def soapResponseBody = new XmlSlurper().parseText(soapResponse.text)

    def actionResponseNode = action + 'Response'
    def actionResultNode = action + 'Result'
    assertThat soapResponseBody.Body."$actionResponseNode"."$actionResultNode".text(), is(result as String)
}
