package stepdefs.webservice.rest

import groovy.json.JsonSlurper
import wslite.rest.Response

import static cucumber.api.groovy.EN.Given
import static cucumber.api.groovy.EN.Then
import static org.hamcrest.Matchers.both
import static org.hamcrest.Matchers.greaterThan
import static org.hamcrest.Matchers.lessThan
import static org.junit.Assert.assertThat

/**
 * Created by Di on 20/10/17.
 * currency.feature step definitions
 */

Given(~/^I want to exchange (\w+) with (\w+)$/) { String baseCurrency, String destCurrency ->
    reqParams = [base: baseCurrency, symbols: destCurrency]
}

Then(~/^I should know exchange rate of (\w+) is between (.+) and (.+)$/) { String destCurrency, BigDecimal min, BigDecimal max ->
    writeRESTResponse((restResponse as Response).contentAsString)

    def jsonResponse = new JsonSlurper().parseText((restResponse as Response).contentAsString)
    BigDecimal result = jsonResponse.rates."$destCurrency" as BigDecimal
    assertThat 'Out of range!!!', result, both(greaterThan(min)) & lessThan(max)
}