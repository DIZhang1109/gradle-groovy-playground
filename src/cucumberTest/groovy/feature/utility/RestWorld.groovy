package feature.utility

import webservices.rest.RestService

import static cucumber.api.groovy.Hooks.World

/**
 * World Hook for REST Service
 * Created by Di on 11/10/17.
 */

World {
    new RestService()
}