package model

import org.yaml.snakeyaml.Yaml

/**
 * Created by Di on 11/11/17.
 * model.EndpointService
 */
trait EndpointService {
    Yaml yaml = new Yaml()
    String endpoint

    void initiateEndpoint(String serviceType, String serviceName) {
        def parsedYaml = yaml.load(new File('src/cucumberTest/groovy/test_properties.yml').text)
        Set keySet = (parsedYaml as LinkedHashMap).keySet()
        if (serviceType in keySet) {
            endpoint = parsedYaml."$serviceType"."$serviceName"
        } else {
            endpoint = null
        }
    }
}