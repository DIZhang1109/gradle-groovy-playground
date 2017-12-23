package webservice.soap

import spock.lang.Narrative
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

/**
 * Created by Di on 23/12/17.
 * Unit test for SoapService
 */
@Title("Unit test for SoapService")
@Narrative("Test the methods in SoapService")
@Subject(SoapService)
class SoapServiceSpec extends Specification {

    @Shared
    SoapService soapService

    def setupSpec() {
        soapService = new SoapService()
    }

    def "retrieve Soap service endpoint"() {
        when: "retrieve endpoint"
        soapService.initiateEndpoint(type, name)

        then: "endpoint address should be as expected"
        soapService.endpoint == endpointAddress

        where: "some scenarios"
        type   | name             || endpointAddress
        'SOAP' | 'USHolidayDates' || 'http://www.holidaywebservice.com/Holidays/US/Dates/USHolidayDates.asmx'
        'SOAP' | 'Calculator'     || 'http://www.dneonline.com/calculator.asmx'
        'AIO'  | 'USHolidayDates' || null
        'SOAP' | 'HelloWorld'     || null
        'JAP'  | '123'            || null
    }
}
