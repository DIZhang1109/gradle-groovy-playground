package webservices.soap

import wslite.soap.SOAPClient
import wslite.soap.SOAPResponse

/**
 * Created by Di on 9/08/17.
 * SoapService
 */
class SoapService {
    SOAPClient client

    void initiateSOAPClient() {
        client = new SOAPClient('http://www.holidaywebservice.com/Holidays/US/Dates/USHolidayDates.asmx')
    }

    SOAPResponse getSOAPResponse(name, searchYear) {
        client.send(SOAPAction: "http://www.27seconds.com/Holidays/US/Dates/$name") {
            body {
                "$name"('xmlns': 'http://www.27seconds.com/Holidays/US/Dates/') {
                    year(searchYear)
                }
            }
        }
    }

//    SOAPResponse getSOAPResponse() {
//        client.send(
//            """<?xml version="1.0" encoding="utf-8"?>
//                <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
//                  <soap:Body>
//                    <GetEaster xmlns="http://www.27seconds.com/Holidays/US/Dates/">
//                      <year>2017</year>
//                    </GetEaster>
//                  </soap:Body>
//                </soap:Envelope>"""
//        )
//    }
}
