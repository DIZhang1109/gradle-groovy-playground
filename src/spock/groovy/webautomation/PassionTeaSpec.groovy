package webautomation

import geb.spock.GebSpec
import spock.lang.Narrative
import spock.lang.Title
import webautomation.page.*

/**
 * Created by Di on 7/01/18.
 * Geb test for Passion Tea website
 */
@Title('Web automation test for Passion Tea website')
@Narrative("A consolidated web functional test")
class PassionTeaSpec extends GebSpec {

    def "smoke test for Passion Tea site"() {
        given: "navigate to home page"
        to PassionTeaHomePage
        report 'Home page'

        when: "go through each page"
        goThroughEachPage()

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
        checkoutGreenTea()

        then: "reach Check Out page"
        at CheckOutPage
    }

    def "send email to the website"() {
        given: "email sending information"
        def emailInfo = new LetsTalkTeaPage.EmailInfo()
        emailInfo.name = name
        emailInfo.email = email
        emailInfo.subject = subject
        emailInfo.message = message

        when: "navigate to Let's Talk Tea page"
        to LetsTalkTeaPage

        then: "submit the form"
        page.submitForm(emailInfo)

        where: 'there are some test data'
        name       | email                 | subject             | message
        'Di Zhang' | 'di.zhang@gmail.com'  | 'Geb is funny'      | 'This is a test message. Please ignore it'
        'Yapi Qi'  | 'yapi.qi@hotmail.com' | 'Selenium is funny' | 'Also another test message. Ignore it.'
    }

    def "check out order"() {
        given: "customer billing information"
        def billInfo = new CheckOutPage.BillInfo()
        billInfo.name = name
        billInfo.email = email
        billInfo.address = address
        billInfo.cardType = cardType
        billInfo.cardNumber = cardNumber
        billInfo.cardHolderName = cardHolderName
        billInfo.verificationCode = verificationCode

        when: "navigate to Let's Talk Tea page"
        to CheckOutPage

        then: "place the order"
        page.placeOrder(billInfo)
        at MenuPage

        where: "there are some test data"
        name       | email                 | address    | cardType     | cardNumber         | cardHolderName | verificationCode
        'Di Zhang' | 'di.zhang@gmail.com'  | 'Shanghai' | 'Visa'       | '1111222233334444' | 'DI ZHANG'     | '123456'
        'Yapi Qi'  | 'yapi.qi@hotmail.com' | 'Taizhou'  | 'Mastercard' | '5555666677778888' | 'YAPI QI'      | '654321'
    }
}
