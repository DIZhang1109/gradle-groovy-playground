package webservices.rest

import groovy.util.logging.Slf4j
import wslite.http.HTTPClientException
import wslite.http.auth.HTTPBasicAuthorization
import wslite.rest.RESTClient
import wslite.rest.Response

/**
 * Created by zhangd on 14/08/2017.
 * RestService
 */
@Slf4j
class RestService {
    RESTClient client
    def response

    void initiateWeatherRESTClient() {
        log.info 'Instantiate a new RESTClient of Weather'
        client = new RESTClient('https://free-api.heweather.com/v5/now')
    }

    void initiateLocalhost(port, name) {
        log.info "Instantiate a new RESTClient of http:localhost:$port/$name"
        client = new RESTClient("http://localhost:$port/$name")
        client.authorization = new HTTPBasicAuthorization('username', 'password')
    }

    Response getRealTimeWeatherRESTResponse(city) {
        log.info "Send REST Get request through $city, then return the response"
        response = client.get(query: [city: "$city", key: 'dcfbc7bb58a34c85a0fd91c8d78c9da2', lang: 'en'])
    }

    Response getLocalhostRESTResponse(value) {
        log.info 'Send REST Get request, then return the response'
        try {
            response = client.get(headers: ['Content-Type': value])
        } catch (HTTPClientException hce) {
            log.warn "Exception: $hce"
        }
    }

    Response deleteLocalhostRESTResponse(value) {
        log.info 'Send REST Delete request, then return the response'
        try {
            response = client.delete(headers: ['Content-Type': value])
        } catch (HTTPClientException hce) {
            log.warn "Exception: $hce"
        }
    }

    Response postLocalhostRESTResponse(value) {
        log.info 'Send REST Post request, then return the response'
        try {
            response = client.post(headers: ['Content-Type': value])
        } catch (HTTPClientException hce) {
            log.warn "Exception: $hce"
        }
    }

    Response putLocalhostRESTResponse(value) {
        log.info 'Send REST Put request, then return the response'
        try {
            response = client.put(headers: ['Content-Type': value])
        } catch (HTTPClientException hce) {
            log.warn "Exception: $hce"
        }
    }
}
