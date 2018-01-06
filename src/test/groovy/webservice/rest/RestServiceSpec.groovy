package webservice.rest

import spock.lang.Narrative
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

/**
 * Created by Di on 24/12/17.
 * Unit test for RestService
 */
@Title("Unit test for RestService")
@Narrative("Test the methods in RestService")
@Subject(RestService)
class RestServiceSpec extends Specification {

    @Shared
    RestService restService

    def setupSpec() {
        restService = new RestService()
    }

    def "retrieve REST service endpoint"() {
        when: "retrieve endpoint"
        restService.initiateEndpoint(type, name)

        then: "endpoint address should be as expected"
        restService.endpoint == endpointAddress

        where: "some scenarios"
        type   | name         || endpointAddress
        'REST' | 'Weather'    || 'https://free-api.heweather.com/v5/now'
        'REST' | 'Currency'   || 'http://api.fixer.io/latest'
        'REST' | 'Mock'       || 'http://localhost:8089/mock'
        'SOAP' | 'HelloWorld' || null
        'rest' | '123'        || null
    }
}
