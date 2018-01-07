package webautomation.page

import geb.Page

/**
 * Created by Di on 7/01/18.
 * Menu page object
 */
class MenuPage extends Page {
    static url = 'menu.html'

    static at = { title == 'Menu' }

    static content = {
        teas { $('strong') }
    }
}
