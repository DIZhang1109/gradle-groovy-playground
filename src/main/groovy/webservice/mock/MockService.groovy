package webservice.mock

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import groovy.util.logging.Slf4j

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo
import static com.github.tomakehurst.wiremock.client.WireMock.verify
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig

/**
 * Created by zhangd on 16/08/2017.
 * MockService
 */
@Slf4j
class MockService {
    WireMockServer wireMockServer

    void startMockServer() {
        log.info 'Start a mock server'
        wireMockServer = new WireMockServer(wireMockConfig()
                .port(8089))
        wireMockServer.start()

        WireMock.configureFor 'localhost', 8089
        WireMock.reset()
    }

    static void stubService(String name, int statusCode) {
        stubFor get(urlPathEqualTo('/mock'))
                .withQueryParam('name', equalTo("$name"))
                .willReturn(aResponse().withStatus(statusCode).withBody('Hello'))
    }

    static void verifyRequest(String name) {
        verify getRequestedFor(urlPathEqualTo('/mock'))
                .withQueryParam('name', equalTo("$name"))
    }

    void stopMockServer() {
        log.info 'Stop mock service'
        wireMockServer.stop()
    }
}
