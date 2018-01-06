package stepdefs.webautomation

import geb.Browser

import static cucumber.api.groovy.EN.Given
import static cucumber.api.groovy.EN.Then
import static cucumber.api.groovy.EN.When

/**
 * Created by Di on 25/12/17.
 * geb-manual.feature step definitions
 */

Given(~/^GEB manual website$/) { ->
    Browser.drive {
        go('http://gebish.org')

        assert title == 'Geb - Very Groovy Browser Automation'
    }
}

When(~/^I visit it$/) { ->
    Browser.drive {
        $("div.menu a.manuals").click()
        waitFor { !$("#manuals-menu").hasClass("animating") }

        $("#manuals-menu a")[0].click()
    }
}

Then(~/^I should know the title is (.+)$/) { String pageTitle ->
    Browser.drive {
        assert title.startsWith(pageTitle)
    }
}
