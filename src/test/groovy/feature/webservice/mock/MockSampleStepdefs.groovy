package feature.webservice.mock

import groovy.util.logging.Slf4j
import org.yaml.snakeyaml.Yaml
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
import static org.junit.Assert.fail

/**
 * Created by zhangd on 16/08/2017.
 * mockSample.feature step definitions
 */

MockService mockService
RestService restService
Response response
Yaml yaml

@Slf4j
class MockSampleStepdefsLog {}

Before() {
    mockService = new MockService()
    restService = new RestService()
    yaml = new Yaml()
}

Given(~/^I start a mock service on (\d+)$/) { int port ->
    mockService.startMockServer port
}

When(~/^I create a (.+) stub of (.+) with (\d+) (.*) (.*) and (.*)$/) { type, name, int status, value, params, body ->
    mockService.stubService type, name, status, value, params, body
}

Then(~/^I (\w+) the service through (\d+) and (\w+) (.*) (.*)$/) { type, int port, name, value, params ->
    restService.initiateLocalhost port, name
    switch (type) {
        case 'Get':
            response = restService.getLocalhostRESTResponse(value, params)
            if(!response) {
                fail('No valid response return!!!')
            }
            break
        case 'Delete':
            response = restService.deleteLocalhostRESTResponse(value)
            break
        case 'Post':
            response = restService.postLocalhostRESTResponse(value)
            break
        case 'Put':
            response = restService.putLocalhostRESTResponse(value)
            break
    }
}

And(~/^I should (.+) same (.+) (\d+) (.*) (.*) and (.*)$/) { type, name, int status, value, params, body ->
    MockService.verifyRequest type, name, value, params

    def bodyFilePath = (body.length() > 0) ? System.getProperty('user.dir') + '/src/test/resources/__files' + yaml.load(('src/test/config.yml' as File).text).MOCK."$body" : 'No'
    def bodyContent = (body.length() > 0) ? new File(bodyFilePath).text : ''
    if (response) {
        MockSampleStepdefsLog.log.info 'Response status: ' + response.statusCode
        MockSampleStepdefsLog.log.info 'Response headers: ' + response.headers
        MockSampleStepdefsLog.log.info 'Response content: ' + response.contentAsString
        assertThat response.statusCode, is(status)
        assertThat response.contentAsString, is(bodyContent)
    }
}

And(~/^I stop the mock service$/) { ->
    mockService.stopMockServer()
}