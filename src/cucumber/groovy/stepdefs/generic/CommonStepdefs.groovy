package stepdefs.generic

import cucumber.api.Scenario
import wslite.soap.SOAPResponse

import static cucumber.api.groovy.EN.Given
import static cucumber.api.groovy.EN.When
import static cucumber.api.groovy.Hooks.Before
import static org.junit.Assert.fail

/**
 * Created by Di on 14/10/17.
 * common step definitions for all features
 */

Before { Scenario s ->
    scenario = s
}

Given(~/^there is a (\w+) (\w+) service$/) { String service, String type ->
    serviceName = service
    serviceType = type
    switch (serviceType) {
        case 'SOAP':
            initiateSOAPEndpoint(service)
            break
        case 'REST':
            initiateRESTEndpoint(service)
            break
        case 'SFTP':
            initiateSFTPEndpoint(service)
            break
        default:
            fail('Unknown service')
            break
    }
}

When(~/^I send request$/) { ->
    if (serviceType == 'SOAP') {
        soapResponse = sendSOAPRequest() as SOAPResponse
    } else if (serviceType == 'REST') {
        restResponse = sendRESTRequest()
    } else {
        fail 'Unknown web service type'
    }
}
