package stepdefs.webservice.rest

import groovy.json.JsonSlurper
import wslite.rest.Response

import static cucumber.api.groovy.EN.Given
import static cucumber.api.groovy.EN.Then
import static org.hamcrest.Matchers.both
import static org.hamcrest.Matchers.greaterThan
import static org.hamcrest.Matchers.lessThan
import static org.junit.Assert.assertThat
import static org.junit.Assert.fail

/**
 * Created by zhangd on 14/08/2017.
 * weather.feature step definitions
 */

Given(~/^a (.+) to look up$/) { String city ->
    reqParams = [city: city, key: 'dcfbc7bb58a34c85a0fd91c8d78c9da2', lang: 'en']
}

Then(~/^I should know the current temperature is between (\d+)°C and (\d+)°C$/) { int min, int max ->
    writeRESTResponse((restResponse as Response).contentAsString)

    def jsonResponse = new JsonSlurper().parseText((restResponse as Response).contentAsString)
    def status = jsonResponse.HeWeather5[0].status
    if (status == 'unknown city') {
        fail 'Invalid city'
    } else if (status == 'invalid key') {
        fail 'Invalid user key'
    } else if (status == 'ok') {
        int actualTemp = (jsonResponse.HeWeather5[0].now.tmp) as int
        assertThat actualTemp, both(greaterThan(min)) & lessThan(max)
    } else {
        fail 'Unknown status'
    }
}