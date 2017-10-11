package feature.utility

import utility.ScenarioService

import static cucumber.api.groovy.Hooks.World

/**
 * World Hook for Scenario Service
 * Created by Di on 19/09/17.
 */

World {
    new ScenarioService()
}