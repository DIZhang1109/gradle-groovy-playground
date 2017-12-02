package stepdefs.hook

import wssecurity.WsSecurityService

import static cucumber.api.groovy.Hooks.World

/**
 * Created by Di on 30/11/17.
 * World hook for WsSecurityService
 */
World {
    new WsSecurityService()
}