package stepdefs.hook

import utility.UtilityService

import static cucumber.api.groovy.Hooks.World

/**
 * Created by zhangd on 15/10/2017.
 * World hook for UtilityService
 */
World {
    new UtilityService()
}
