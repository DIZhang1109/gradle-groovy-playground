package stepdefs.webservice.soap

import static cucumber.api.groovy.EN.Given
import static cucumber.api.groovy.EN.Then
import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

/**
 * Created by Di on 9/08/17.
 * us_holiday_dates.feature step definitions
 */

Given(~/^a US holiday (.+) of (\d+)$/) { String holiday, int year ->
    soapRequest = getPayload(serviceName, ['holiday': "Get${holiday.replaceAll('\\s', '')}", 'year': year])
}

Then(~/^I should know (.+) of (\d+) is (.+)$/) { String holiday, int year, String date ->
    writeSOAPResponse(soapResponse.text)
    def soapResponseBody = new XmlSlurper().parseText(soapResponse.text)

    def holidayResponseNode = 'Get' + holiday.replaceAll('\\s', '') + 'Response'
    def holidayResultNode = 'Get' + holiday.replaceAll('\\s', '') + 'Result'
    assertThat soapResponseBody.Body."$holidayResponseNode"."$holidayResultNode".text().take(10), is(year + '-' + date)
}
