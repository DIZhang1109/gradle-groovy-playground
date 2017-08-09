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
}
