package webservice.rest

import groovy.util.logging.Slf4j
import model.EndpointService
import wslite.rest.RESTClient
import wslite.rest.RESTClientException

/**
 * Created by zhangd on 14/08/2017.
 * RestService
 */

@Slf4j
class RestService implements EndpointService {
    RESTClient client
    def restResponse
    def reqParams = [:]

    void initiateRESTEndpoint(String service) {
        log.info "Instantiate a REST client of $service"
        initiateEndpoint 'REST', service
        client = new RESTClient(endpoint)
    }

    def sendRESTRequest() {
        try {
            client.get query: reqParams, connectTimeout: 5000, readTimeout: 10000
        } catch (RESTClientException rce) {
            log.info "REST Fault Exception: ${rce.response.statusCode}"
            rce.response
        } catch (Exception e) {
            log.warn 'Exception: ' + e.message
        }
    }
}
