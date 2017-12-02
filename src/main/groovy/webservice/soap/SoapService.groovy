package webservice.soap

import groovy.text.SimpleTemplateEngine
import groovy.util.logging.Slf4j
import model.EndpointService
import wslite.soap.SOAPClient
import wslite.soap.SOAPFaultException
import wslite.soap.SOAPResponse

/**
 * Created by Di on 9/08/17.
 * SoapService
 */

@Slf4j
class SoapService implements EndpointService {
    SOAPClient client
    SOAPResponse soapResponse
    String soapRequest

    void initiateSOAPEndpoint(String service) {
        log.info "Instantiate a SOAP client of $service"
        initiateEndpoint 'SOAP', service
        client = new SOAPClient(endpoint)
    }

    String getPayload(String service, Map binding) {
        def templateEngine = new SimpleTemplateEngine()
        String payloadPath = "src/cucumberTest/resources/payload/webservice/soap/$service/request.xml"
        soapRequest = templateEngine.createTemplate(new File(payloadPath).text).make(binding).toString()
    }

    def sendSOAPRequest() {
        try {
            client.send connectTimeout: 5000, readTimeout: 10000, soapRequest
        } catch (SOAPFaultException sfe) {
            log.info "SOAP Fault Exception: ${sfe.httpResponse.statusCode}"
            sfe.httpResponse
        } catch (Exception e) {
            log.warn 'Exception: ' + e.message
        }
    }
}
