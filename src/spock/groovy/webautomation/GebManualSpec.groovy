package webautomation

import geb.spock.GebSpec
import spock.lang.Narrative
import spock.lang.Title
import webautomation.page.GebHomePage
import webautomation.page.TheBookOfGebPage

/**
 * Created by Di on 6/01/18.
 * Geb test for Geb Manual website
 */
@Title("Web automation test for Geb manual website")
@Narrative("The simpliest test for Geb")
class GebManualSpec extends GebSpec {

    def "can access The Book of Geb via homepage"() {
        given: "Navigate to Geb home page"
        to GebHomePage

        when: "Click the menu and open the first link"
        manualsMenu.open()
        manualsMenu.links[0].click()
        report 'Home page'

        then: "At the Geb manual page"
        at TheBookOfGebPage
    }
}
