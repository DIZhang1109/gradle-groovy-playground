package webservices.mock

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import groovy.util.logging.Slf4j

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.delete
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.givenThat
import static com.github.tomakehurst.wiremock.client.WireMock.ok
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig

/**
 * Created by zhangd on 16/08/2017.
 * MockService
 */
@Slf4j
class MockService {
    WireMockServer wireMockServer

    void startMockServer(int port) {
        log.info "Start a mock server on $port"
        wireMockServer = new WireMockServer(wireMockConfig().port(port))
        wireMockServer.start()

        WireMock.configureFor 'localhost', port
        WireMock.reset()
    }

    static void stubGetService(type, name, int status, value, body) {
        log.info "Stub for a Get service with $name, $status, $value and $body"
        switch (type) {
            case 'Get':
                givenThat get(urlEqualTo("/$name"))
                        .willReturn(aResponse()
                        .withStatus(status)
                        .withHeader('Content-Type', "$value")
                        .withBody("$body"))
                break
            case 'Delete':
                givenThat delete(urlEqualTo("/$name"))
                        .willReturn(ok())
        }
    }

    void stopMockServer() {
        log.info 'Stop mock service'
        wireMockServer.stop()
    }
}
