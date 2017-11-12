package stepdefs.hook

import webservice.soap.SoapService

import static cucumber.api.groovy.Hooks.World

/**
 * Created by zhangd on 28/08/2017.
 * World hook for SoapService
 */

World {
    new SoapService()
}
