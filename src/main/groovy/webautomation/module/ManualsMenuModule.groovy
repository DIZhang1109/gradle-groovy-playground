package webautomation.module

import geb.Module

/**
 * Created by Di on 6/01/18.
 * Geb Home Menu module object
 */
class ManualsMenuModule extends Module {
    static content = {
        toggle { $("div.menu a.manuals") }
        linksContainer { $("#manuals-menu") }
        links { linksContainer.find("a") }
    }

    void open() {
        toggle.click()
        waitFor { !linksContainer.hasClass("animating") }
    }
}
