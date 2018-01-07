package webautomation.page

import geb.Page

/**
 * Created by Di on 7/01/18.
 * Let's Talk Tea page object
 */
class LetsTalkTeaPage extends Page {
    static url = 'let-s-talk-tea.html'

    static at = { title == 'Let\'s Talk Tea' }

    static content = {
        name { $('input', name: 'name') }
        email { $('input', name: 'email') }
        subject { $('input', name: 'subject') }
        message { $('textarea', name: 'message') }
        submitButton { $('input', value: 'Submit') }
    }
}
