package webservice.soap

import groovy.text.SimpleTemplateEngine
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

    def "retrieve SOAP service endpoint"() {
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

    def "retrieve SOAP request payload"() {
        when: "get actual payload"
        soapService.getPayload(service, map)

        then: "get SOAP request payload"
        soapService.soapRequest == payload

        where: "some scenarios"
        service          | map                                           || payload
        'Calculator'     | ['action': 'Add', 'num1': 20, 'num2': 50]     || new SimpleTemplateEngine().createTemplate(new File("src/cucumber/resources/payload/webservice/soap/$service/request.xml").text).make(map).toString()
        'haha'           | ['action': 'Multiple']                        || null
        'Calculator'     | ['action': 'Subtract', 'num1': 30, 'num2': 2] || new SimpleTemplateEngine().createTemplate(new File("src/cucumber/resources/payload/webservice/soap/$service/request.xml").text).make(map).toString()
        'USHolidayDates' | ['holiday': "GetChristmasDay", 'year': 2017]  || new SimpleTemplateEngine().createTemplate(new File("src/cucumber/resources/payload/webservice/soap/$service/request.xml").text).make(map).toString()
        'hehe'           | [:]                                           || null
        'USHolidayDates' | ['holiday': "GetBlackFriday", 'year': 2001]   || new SimpleTemplateEngine().createTemplate(new File("src/cucumber/resources/payload/webservice/soap/$service/request.xml").text).make(map).toString()
    }
}
