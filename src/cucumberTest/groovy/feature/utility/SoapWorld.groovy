package feature.utility

import webservices.soap.SoapService

import static cucumber.api.groovy.Hooks.World

/**
 * World Hook for SOAP Service
 * Created by Di on 19/09/17.
 */

World {
    new SoapService()
}