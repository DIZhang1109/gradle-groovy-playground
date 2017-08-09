package webservices.soap

import wslite.soap.SOAPClient
import wslite.soap.SOAPResponse
import wslite.soap.SOAPVersion

/**
 * Created by Di on 9/08/17.
 * SoapService
 */
class SoapService {
    SOAPClient client

    void initiateSOAPClient() {
        client = new SOAPClient('http://www.holidaywebservice.com/Holidays/US/Dates/USHolidayDates.asmx')
    }

    SOAPResponse getSOAPV1Response(name, searchYear) {
        client.send(SOAPAction: "http://www.27seconds.com/Holidays/US/Dates/$name") {
            body {
                "$name"('xmlns': 'http://www.27seconds.com/Holidays/US/Dates/') {
                    year(searchYear)
                }
            }
        }
    }

    SOAPResponse getSOAPV2Response(name, searchYear) {
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
}
