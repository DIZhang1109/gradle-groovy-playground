package feature.utility

import utility.ScenarioService
import webservices.mock.MockService
import webservices.rest.RestService
import webservices.soap.SoapService

import static cucumber.api.groovy.Hooks.World

/**
 * World Hook
 * Created by Di on 19/09/17.
 */
CustomWorld.mixin MockService, RestService, SoapService, ScenarioService

World {
    new CustomWorld()
}