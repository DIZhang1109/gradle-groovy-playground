package feature.utility

import webservices.mock.MockService

import static cucumber.api.groovy.Hooks.World

/**
 * World Hook for Mock Service
 * Created by Di on 11/10/17.
 */

World {
    new MockService()
}
