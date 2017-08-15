package feature.webservice.mock

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import static cucumber.api.groovy.EN.Given
import static cucumber.api.groovy.EN.Then

/**
 * Created by zhangd on 16/08/2017.
 * mockSample.feature step definitions
 */

WireMockServer wireMockServer

Given(~/^I start a mock service$/) { ->
    wireMockServer = new WireMockServer(wireMockConfig().port(8089))
    wireMockServer.start()

    WireMock.configureFor 'localhost', 8089
    WireMock.reset()
}

Then(~/^I stop the mock service$/) { ->
    wireMockServer.stop()
}