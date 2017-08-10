package webservices.soap

import groovy.util.logging.Slf4j
import wslite.soap.SOAPClient
import wslite.soap.SOAPResponse
import wslite.soap.SOAPVersion

/**
 * Created by Di on 9/08/17.
 * SoapService
 */

@Slf4j
class SoapService {
    SOAPClient client

    void initiateUSHolidaySOAPClient() {
        log.info 'Instantiate a new SOAPClient of US Holiday'
        client = new SOAPClient('http://www.holidaywebservice.com/Holidays/US/Dates/USHolidayDates.asmx')
    }

    void initiateCalculatorSOAPClient() {
        log.info 'Instantiate a new SOAPClient of Calculator'
        client = new SOAPClient('http://www.dneonline.com/calculator.asmx')
    }

    SOAPResponse getUSHolidaySOAPV1Response(name, searchYear) {
        log.info "Send SOAP 1.1 request through $name and $searchYear, then return the response"
        client.send(SOAPAction: "http://www.27seconds.com/Holidays/US/Dates/$name") {
            body {
                "$name"('xmlns': 'http://www.27seconds.com/Holidays/US/Dates/') {
                    year(searchYear)
                }
            }
        }
    }

    SOAPResponse getUSHolidaySOAPV2Response(name, searchYear) {
        log.info "Send SOAP 1.2 request through $name and $searchYear, then return the response"
        client.send(SOAPVersion.V1_2,
                """<?xml version="1.0" encoding="utf-8"?>
                    <soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
                      <soap12:Body>
                        <$name xmlns="http://www.27seconds.com/Holidays/US/Dates/">
                          <year>$searchYear</year>
                        </$name>
                      </soap12:Body>
                    </soap12:Envelope>""")
    }

    SOAPResponse getCalculatorSOAPV1Response(action, digit1, digit2) {
        log.info "Send SOAP 1.1 request through $digit1 and $digit2, then return the response"
        client.send(SOAPAction: "http://tempuri.org/$action") {
            body {
                "$action"('xmlns': 'http://tempuri.org/') {
                    intA(digit1)
                    intB(digit2)
                }
            }
        }
    }

    SOAPResponse getCalculatorSOAPV2Response(action, digit1, digit2) {
        log.info "Send SOAP 1.1 request through $digit1 and $digit2, then return the response"
        client.send(SOAPVersion.V1_2,
                """<?xml version="1.0" encoding="utf-8"?>
                    <soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
                      <soap12:Body>
                        <$action xmlns="http://tempuri.org/">
                          <intA>$digit1</intA>
                          <intB>$digit2</intB>
                        </$action>
                      </soap12:Body>
                    </soap12:Envelope>""")
    }
}
