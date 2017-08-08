package example

import static cucumber.api.groovy.EN.Given
import static cucumber.api.groovy.EN.Then

/**
 * Created by Di on 8/08/17.
 * shout.feature step definitions
 */

Given(~/^I want to shout (.+)$/) { word ->
    Shout.shout word
}

Then(~/^(.+) will be louder$/) { word ->
    Shout.shoutLouder word
}
