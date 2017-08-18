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

When(~/^I create a (.+) stub of (.+) with (\d+) (.*) and (.*)$/) { type, name, int status, value, body ->
    mockService.stubService type, name, status, value, body
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
        case 'Post':
            response = restService.postLocalhostRESTResponse()
            break
        case 'Put':
            response = restService.putLocalhostRESTResponse()
            break
    }
}

And(~/^I should get same (\d+) and (.*)$/) { int status, body ->
    def bodyFilePath = (body.length() > 0) ? System.getProperty('user.dir') + '/src/test/resources/__files' + yaml.load(('src/test/config.yml' as File).text).REST."$body" : ''
    def bodyContent = (body.length() > 0) ? new File(bodyFilePath).text : ''

    if (response) {
        MockSampleStepdefsLog.log.info('Response status: ' + response.statusCode)
        MockSampleStepdefsLog.log.info('Response headers: ' + response.headers)
        MockSampleStepdefsLog.log.info('Response content: ' + response.contentAsString)
        assertThat response.statusCode, is(status)
        assertThat response.contentAsString, is(bodyContent)
    }
}

And(~/^I stop the mock service$/) { ->
    mockService.stopMockServer()
}
