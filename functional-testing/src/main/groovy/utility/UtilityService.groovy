package utility

import cucumber.api.Scenario
import groovy.json.JsonOutput
import groovy.xml.XmlUtil

/**
 * Created by Di on 16/10/17.
 * Utility Service
 */

class UtilityService {
    String serviceName
    String serviceType
    Scenario scenario

    void writeSOAPResponse(String content) {
        scenario.write 'SOAP Response:' + System.getProperty('line.separator') + XmlUtil.serialize(content)
    }

    void writeRESTResponse(String content) {
        scenario.write 'REST Response:' + System.getProperty('line.separator') + JsonOutput.prettyPrint(content)
    }

    void writePlainText(String content) {
        scenario.write content
    }
}
