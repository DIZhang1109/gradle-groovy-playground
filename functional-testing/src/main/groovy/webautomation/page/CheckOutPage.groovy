package webautomation.page

import geb.Page

/**
 * Created by Di on 7/01/18.
 * Check out page object
 */
class CheckOutPage extends Page {
    static url = 'check-out.html'

    static at = { title == 'Check Out' }

    static content = {
        email { $('input', id: 'email') }
        name { $('input', id: 'name') }
        address { $('textarea', id: 'address') }

        cardTypeDropdown { $('select', id: 'card_type') }
        cardNo { $('input', id: 'card_number') }
        cardHolderName { $('input', id: 'cardholder_name') }
        verificationCode { $('input', id: 'verification_code') }

        placeOrderButton { $('button') }
    }

    def placeOrder = { BillInfo billInfo ->
        email << billInfo.email
        name << billInfo.name
        address << billInfo.address

        cardTypeDropdown.click()
        cardTypeDropdown.find('option').find { it.value() == billInfo.cardType }.click()
        cardNo << billInfo.cardNumber
        cardHolderName << billInfo.cardHolderName
        verificationCode << billInfo.verificationCode

        placeOrderButton.click(MenuPage)
    }

    static class BillInfo {
        String email
        String name
        String address
        String cardType
        String cardNumber
        String cardHolderName
        String verificationCode
    }
}
