package stepdefs.hook

import utility.XmlComparisonService

import static cucumber.api.groovy.Hooks.World

/**
 * Created by Di on 30/11/17.
 * World hook for XmlComparisonService
 */
World {
    new XmlComparisonService()
}