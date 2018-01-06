package webautomation.page

import geb.Page
import webautomation.module.ManualsMenuModule

/**
 * Created by Di on 6/01/18.
 * Geb Home page object
 */
class GebHomePage extends Page {
    static url = "http://gebish.org"

    static at = { title == "Geb - Very Groovy Browser Automation" }

    static content = {
        headings { $('h1') }
        firstHeading { headings[0] }
        manualsMenu { module(ManualsMenuModule) }
    }
}
