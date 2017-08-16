package feature.webservice.mock

import groovy.util.logging.Slf4j
import webservices.mock.MockService
import webservices.rest.RestService
import wslite.rest.Response

import static cucumber.api.groovy.EN.And
import static cucumber.api.groovy.EN.Given
import static cucumber.api.groovy.EN.Then
import static cucumber.api.groovy.EN.When
import static cucumber.api.groovy.Hooks.Before
import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

/**
 * Created by zhangd on 16/08/2017.
 * mockSample.feature step definitions
 */

MockService mockService
RestService restService
Response response

@Slf4j
class MockSampleStepdefsLog {}

Before() {
    mockService = new MockService()
    restService = new RestService()
}

Given(~/^I start a mock service on (\d+)$/) { int port ->
    mockService.startMockServer port
}

When(~/^I create a (.+) stub of (.+) with (\d+) (.*) and (.*)$/) { type, name, int status, value, body ->
    MockService.stubGetService type, name, status, value, body
}

Then(~/^I (\w+) the service through (\d+) and (\w+)$/) { type, int port, name ->
    restService.initiateLocalhost port, name
    switch (type) {
        case 'Get':
            response = restService.getLocalhostRESTResponse()
            break
        case 'Delete':
            response = restService.deleteLocalhostRESTResponse()
            break
    }
}

And(~/^I should get same (\d+) and (.*)$/) { int status, body ->
    MockSampleStepdefsLog.log.info('Response status: ' + response.statusCode)
    MockSampleStepdefsLog.log.info('Response headers: ' + response.headers)
    MockSampleStepdefsLog.log.info('Response content: ' + response.contentAsString)
    assertThat response.statusCode, is(status)
    assertThat response.contentAsString, is(body)
}

And(~/^I stop the mock service$/) { ->
    mockService.stopMockServer()
}
