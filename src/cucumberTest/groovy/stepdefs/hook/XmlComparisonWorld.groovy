package stepdefs.hook

import utility.XmlComparisonService

import static cucumber.api.groovy.Hooks.World

World {
    new XmlComparisonService()
}