package feature.webservice.mock

import groovy.util.logging.Slf4j
import org.yaml.snakeyaml.Yaml

import static cucumber.api.groovy.EN.*
import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat
import static org.junit.Assert.fail

/**
 * Created by zhangd on 16/08/2017.
 * mockSample.feature step definitions
 */

@Slf4j
class MockSampleStepdefsLog {}

Given(~/^I start a mock service on (\d+)$/) { int port ->
    startMockServer port
}

When(~/^I create a (.+) stub of (.+) with (\d+) (.*) (.*) and (.*)$/) { type, name, int status, value, params, body ->
    stubService type, name, status, value, params, body
}

Then(~/^I (\w+) the service through (\d+) and (\w+) (.*) (.*)$/) { type, int port, name, value, params ->
    initiateLocalhost port, name
    switch (type) {
        case 'Get':
            response = getLocalhostRESTResponse(value, params)
            if (!response) {
                fail('No valid response return!!!')
            }
            break
        case 'Delete':
            response = deleteLocalhostRESTResponse(value)
            break
        case 'Post':
            response = postLocalhostRESTResponse(value)
            break
        case 'Put':
            response = putLocalhostRESTResponse(value)
            break
    }
}

And(~/^I should (.+) same (.+) (\d+) (.*) (.*) and (.*)$/) { type, name, int status, value, params, body ->
    verifyRequest type, name, value, params

    def bodyFilePath = (body.length() > 0) ? System.getProperty('user.dir') + '/src/test/resources/__files' + new Yaml().load(('src/cucumberTest/resources/config.yml' as File).text).MOCK."$body" : 'No'
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
    stopMockServer()
}