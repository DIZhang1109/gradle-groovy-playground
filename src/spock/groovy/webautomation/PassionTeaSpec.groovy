package webautomation

import geb.spock.GebSpec
import spock.lang.Narrative
import spock.lang.Title
import webautomation.page.CheckOutPage
import webautomation.page.LetsTalkTeaPage
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

    void setupSpec() {
        driver.manage().window().maximize()
    }

    def "smoke test for Passion Tea site"() {
        given: "navigate to home page"
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
        given: "navigate to Our Passion page"
        to OurPassionPage
        report 'Our Passion page'

        expect: "two headers are as expected"
        assert firstHeader.text() == 'Our Passion'
        assert secondHeader.text() == 'The Experts'
    }

    def "menu page"() {
        when: "navigate to Menu page"
        to MenuPage

        then: "three kinds of tea introduction"
        assert teas*.text().equals(['Red Tea', 'Green Tea', 'Oolong Tea'])
    }

    def "reroute to check out page from menu page"() {
        given: "navigate to Menu page"
        to MenuPage

        when: "click Green Tea check out button"
        checkoutButtons[0].click()

        then: "reach Check Out page"
        at CheckOutPage
    }

    def "send email to the website"() {
        given: "navigate to Let's Talk Tea page"
        to LetsTalkTeaPage

        when: "fill in the form"
        name << nameValue
        email << emailValue
        subject << subjectValue
        message << messageValue

        then: "click submit button"
        submitButton.click()

        where: 'there are some test data'
        nameValue  | emailValue            | subjectValue        | messageValue
        'Di Zhang' | 'di.zhang@gmail.com'  | 'Geb is funny'      | 'This is a test message. Please ignore it'
        'Yapi Qi'  | 'yapi.qi@hotmail.com' | 'Selenium is funny' | 'Also another test message. Ignore it.'
    }
}
