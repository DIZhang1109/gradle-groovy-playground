package feature.webservice.rest

import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import webservices.rest.RestService
import wslite.rest.Response

import static cucumber.api.groovy.EN.Given
import static cucumber.api.groovy.EN.Then
import static cucumber.api.groovy.EN.When
import static cucumber.api.groovy.Hooks.Before
import static org.hamcrest.CoreMatchers.*
import static org.hamcrest.Matchers.both
import static org.hamcrest.Matchers.greaterThan
import static org.hamcrest.Matchers.lessThan
import static org.junit.Assert.assertThat
import static org.junit.Assert.fail

/**
 * Created by zhangd on 14/08/2017.
 * weather.feature step definitions
 */

RestService restService
Response response

@Slf4j
class WeatherStepdefsLog {}

Before() {
    restService = new RestService()
}

Given(~/^I have a REST request of Weather$/) { ->
    restService.initiateWeatherRESTClient()
}

When(~/^I send to the endpoint with (\w+)$/) { city ->
    response = restService.getRealTimeWeatherRESTResponse(city)
}

Then(~/^I should know the weather of (.+) is between (\d+) and (\d+)$/) { city, int min, int max ->
    def jsonResponse = new JsonSlurper().parseText(response.contentAsString)
    WeatherStepdefsLog.log.info 'Response JSON: ' + jsonResponse

    def status = jsonResponse.HeWeather5.status.toString().replaceAll('\\[', '').replaceAll('\\]', '')
    if (status == 'unknown city') {
        WeatherStepdefsLog.log.warn "$city is not valid, please try again"
        fail 'Invalid city'
    } else if (status == 'invalid key') {
        WeatherStepdefsLog.log.warn "User key is not valid, please try again"
        fail 'Invalid user key'
    } else if (status == 'ok') {
        def actualCity = jsonResponse.HeWeather5.basic.city.toString().replaceAll('\\[', '').replaceAll('\\]', '').capitalize()
        def actualTemp = jsonResponse.HeWeather5.now.tmp.toString().replaceAll('\\[', '').replaceAll('\\]', '').toInteger()
        WeatherStepdefsLog.log.info 'Real time weather: ' + actualTemp

        assertThat response.statusCode, is(200)
        assertThat actualCity, is(city)
        assertThat actualTemp, both(greaterThan(min)).and(lessThan(max))
    } else {
        WeatherStepdefsLog.log.warn 'Unknown status, please try again'
        fail 'Unknown status'
    }
}