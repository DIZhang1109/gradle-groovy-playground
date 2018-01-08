package webautomation.page

import geb.Page
import org.openqa.selenium.By

/**
 * Created by Di on 7/01/18.
 * Passion Tea home page
 */
class PassionTeaHomePage extends Page {
    static at = { title == 'Welcome' }

    static content = {
        ourPassionLink { $("a[data-title='Our Passion']") }
        menuLink { $("a[data-title='Menu']") }
        letsTalkTeaLink { $(By.linkText('Let\'s Talk Tea')) }
        checkOutLink { $("a[data-title='Check Out']") }
    }

    void goThroughEachPage() {
        ourPassionLink.click()
        menuLink.click()
        letsTalkTeaLink.click()
        checkOutLink.click()
    }
}
