package feature.webservice.rest

import webservices.rest.RestService

import static cucumber.api.groovy.Hooks.World

/**
 * Created by zhangd on 28/08/2017.
 * World hook for RestService
 */

World {
    new RestService()
}