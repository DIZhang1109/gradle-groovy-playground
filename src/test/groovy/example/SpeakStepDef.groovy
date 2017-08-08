package example

import static cucumber.api.groovy.EN.Given
import static cucumber.api.groovy.EN.Then

/**
 * Created by Di on 9/08/17.
 * speak.feature step definitions
 */

Given(~/^I want to speak (.+)$/) { word ->
    Speak.speak word
}

Then(~/^(.+) will be show$/) { word ->
    Speak.speakQuiet word
}
