package webautomation

import geb.spock.GebSpec
import spock.lang.Narrative
import spock.lang.Title
import webautomation.page.CheckOutPage
import webautomation.page.MenuPage
import webautomation.page.OurPassionPage
import webautomation.page.PassionTeaHomePage

/**
 * Created by Di on 7/01/18.
 * Geb test for Passion Tea website
 */
@Title('Web automation test for Passion Tea website')
@Narrative("A consolidated web functional test")
class PassionTeaSpec extends GebSpec {

    def "smoke test for Passion Tea site"() {
        given: "Navigate to home page"
        to PassionTeaHomePage
        report 'Home page'

        when: "go through each page"
        ourPassionLink.click()
        menuLink.click()
        letsTalkTeaLink.click()
        checkOutLink.click()

        then: "reach Check Out page finally"
        at CheckOutPage
    }

    def "our passion page"() {
        given: "Navigate to Our Passion page"
        to OurPassionPage
        report 'Our Passion page'

        expect: "two headers"
        assert firstHeader.text() == 'Our Passion'
        assert secondHeader.text() == 'The Experts'
    }

    def "menu page"() {
        given: "Navigate to Menu page"
        to MenuPage
        report 'Menu page'

        expect: "three tea kinds"
        assert teas*.text().equals(['Red Tea', 'Green Tea', 'Oolong Tea'])
    }
}
