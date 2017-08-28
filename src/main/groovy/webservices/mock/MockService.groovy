package webservices.mock

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer
import groovy.util.logging.Slf4j
import org.yaml.snakeyaml.Yaml

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.delete
import static com.github.tomakehurst.wiremock.client.WireMock.deleteRequestedFor
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo
import static com.github.tomakehurst.wiremock.client.WireMock.forbidden
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import static com.github.tomakehurst.wiremock.client.WireMock.givenThat
import static com.github.tomakehurst.wiremock.client.WireMock.noContent
import static com.github.tomakehurst.wiremock.client.WireMock.ok
import static com.github.tomakehurst.wiremock.client.WireMock.post
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor
import static com.github.tomakehurst.wiremock.client.WireMock.put
import static com.github.tomakehurst.wiremock.client.WireMock.putRequestedFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import static com.github.tomakehurst.wiremock.client.WireMock.verify
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig

/**
 * Created by zhangd on 16/08/2017.
 * MockService
 */
@Slf4j
class MockService {
    WireMockServer wireMockServer
    Yaml yaml

    MockService(Yaml yaml) {
        this.yaml = yaml
    }

    void startMockServer(int port) {
        log.info "Start a mock server on $port"
        wireMockServer = new WireMockServer(wireMockConfig()
                .port(port)
                .extensions(new ResponseTemplateTransformer(false)))
        wireMockServer.start()

        WireMock.configureFor 'localhost', port
        WireMock.reset()
    }

    void stubService(type, name, int status, value, params, body) {
        log.info "Stub for a $type service with $name, $status, $value and $body"
        def bodyFilePath = (body.length() > 0) ? yaml.load(('src/test/config.yml' as File).text).MOCK."$body" : ''

        switch (type) {
            case 'Get':
                if (params) {
                    givenThat get(urlEqualTo("/$name?first=$params"))
                            .withBasicAuth('username', 'password')
                            .willReturn(aResponse()
                            .withStatus(status)
                            .withHeader('Content-Type', "$value")
                            .withBody("{{request.query.first}} World!")
                            .withTransformers('response-template'))
                } else {
                    givenThat(get(urlEqualTo("/$name"))
                            .withBasicAuth('username', 'password')
                            .willReturn(aResponse()
                            .withStatus(status)
                            .withHeader('Content-Type', "$value")
                            .withBodyFile(bodyFilePath)))
                }
                break
            case 'Delete':
                givenThat delete(urlEqualTo("/$name"))
                        .willReturn(ok())
                break
            case 'Post':
                givenThat post(urlEqualTo("/$name"))
                        .willReturn(noContent())
                break
            case 'Put':
                givenThat put(urlEqualTo("/$name"))
                        .willReturn(forbidden())
                break
        }
    }

    static void verifyRequest(type, name, value, params) {
        switch (type) {
            case 'Get':
                if (params) {
                    verify getRequestedFor(urlEqualTo("/$name?first=$params"))
                            .withHeader('Content-Type', equalTo(value))
                            .withQueryParam('first', equalTo(params))
                } else {
                    verify getRequestedFor(urlEqualTo("/$name"))
                            .withHeader('Content-Type', equalTo(value))
                }
                break
            case 'Delete':
                verify deleteRequestedFor(urlEqualTo("/$name"))
                        .withHeader('Content-Type', equalTo(value))
                break
            case 'Post':
                verify postRequestedFor(urlEqualTo("/$name"))
                        .withHeader('Content-Type', equalTo(value))
                break
            case 'Put':
                verify putRequestedFor(urlEqualTo("/$name"))
                        .withHeader('Content-Type', equalTo(value))
        }
    }

    void stopMockServer() {
        log.info 'Stop mock service'
        wireMockServer.stop()
    }
}
