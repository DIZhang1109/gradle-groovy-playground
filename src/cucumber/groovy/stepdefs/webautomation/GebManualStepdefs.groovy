package stepdefs.webautomation

import webautomation.page.GebHomePage
import webautomation.page.TheBookOfGebPage

import static cucumber.api.groovy.EN.Given
import static cucumber.api.groovy.EN.Then
import static cucumber.api.groovy.EN.When

/**
 * Created by Di on 25/12/17.
 * geb-manual.feature step definitions
 */

Given(~/^I am on the Geb home page$/) { ->
    to GebHomePage
}

Then(~/^the first heading on the page is '(.+)'$/) { String expectedHeading ->
    assert page.firstHeading.text() == expectedHeading
}

When(~/^the link to documentation is clicked$/) { ->
    page.manualsMenu.open()

    page.manualsMenu.links[0].click()
}

Then(~/^I end up at The Book of Geb$/) { ->
    at TheBookOfGebPage
}
