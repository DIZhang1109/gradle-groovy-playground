package stepdefs.webservice.mock

import wslite.http.HTTPResponse
import wslite.rest.Response

import static cucumber.api.groovy.EN.*
import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

/**
 * Created by zhangd on 16/08/2017.
 * mockSample.feature step definitions
 */

Given(~/^a (\w+) request of (\d+)$/) { String name, int statusCode ->
    startMockServer()
    stubService name, statusCode
    reqParams = [name: "$name"]
}

Then(~/^verify the request of (\w+)$/) { String name ->
    verifyRequest name
}

And(~/^(\d+) is expected$/) { int statusCode ->
    int actualStatusCode = (restResponse instanceof Response) ? (restResponse as Response).statusCode : (restResponse as HTTPResponse).statusCode
    assertThat actualStatusCode, is(statusCode)
    stopMockServer()
}
