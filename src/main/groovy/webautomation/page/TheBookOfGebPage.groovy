package webautomation.page

import geb.Page

/**
 * Created by Di on 6/01/18.
 * The Book of Geb page object
 */
class TheBookOfGebPage extends Page {
    static at = { title.startsWith("The Book Of Geb") }
}
