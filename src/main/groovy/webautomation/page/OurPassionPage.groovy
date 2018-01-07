package webautomation.page

import geb.Page

/**
 * Created by Di on 7/01/18.
 * Our Passion page object
 */
class OurPassionPage extends Page {
    static url = 'our-passion.html'

    static at = { title == 'Our Passion' }

    static content = {
        firstHeader { $('h1') }
        secondHeader { $('h2.editor_h1') }
    }
}
