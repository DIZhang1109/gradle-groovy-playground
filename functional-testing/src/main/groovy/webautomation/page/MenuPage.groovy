package webautomation.page

import geb.Page
import org.openqa.selenium.By

/**
 * Created by Di on 7/01/18.
 * Menu page object
 */
class MenuPage extends Page {
    static url = 'menu.html'

    static at = { title == 'Menu' }

    static content = {
        teas { $('strong') }
        checkoutButtons(to: CheckOutPage) { $(By.linkText('Check Out')) }
    }

    void checkoutGreenTea() {
        checkoutButtons[0].click()
    }
}
