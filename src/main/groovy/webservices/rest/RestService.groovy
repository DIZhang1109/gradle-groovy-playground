package webservices.rest

import groovy.util.logging.Slf4j
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
    }

    Response getRealTimeWeatherRESTResponse(city) {
        log.info "Send REST Get request through $city, then return the response"
        response = client.get(query: [city: "$city", key: 'dcfbc7bb58a34c85a0fd91c8d78c9da2', lang: 'en'])
    }

    Response getLocalhostRESTResponse() {
        log.info "Send REST Get request, then return the response"
        response = client.get()
    }

    Response deleteLocalhostRESTResponse() {
        log.info "Send REST Delete request, then return the response"
        response = client.delete()
    }
}
