package feature.webservice.soap

import static cucumber.api.groovy.EN.Given
import static cucumber.api.groovy.EN.Then
import static cucumber.api.groovy.EN.When
import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

/**
 * Created by Di on 9/08/17.
 * holiday.feature step definitions
 */

Given(~/^I have a SOAP (.+) request of US Holiday$/) { version ->
    initiateUSHolidaySOAPClient()
    soapVersion = version
}

When(~/^I send to the endpoint with (.+) and (\d+)$/) { holiday, searchYear ->
    soapResponse = (soapVersion == 'V1') ? getUSHolidaySOAPV1Response(getHoliday(holiday), searchYear) : getUSHolidaySOAPV2Response(getHoliday(holiday), searchYear)
}

Then(~/^I should know (.+) of (\d+) is (.+)$/) { holiday, year, date ->
    def responseName = getHoliday(holiday) + 'Response'
    def responseResult = getHoliday(holiday) + 'Result'

    assertThat soapResponse.httpResponse.statusCode, is(200)
    assertThat soapResponse."$responseName"."$responseResult".text().take(4), is(year)
    assertThat soapResponse."$responseName"."$responseResult".text()[5..9], is(date)
}

/**
 * Return request name like 'Get...'
 * @param holiday the feature.webservice name
 * @return request
 */
static String getHoliday(holiday) {
    'Get' + holiday.replaceAll('\\s', '')
}