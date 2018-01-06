package stepdefs.sftp

import static org.junit.Assert.assertThat
import static cucumber.api.groovy.EN.And
import static cucumber.api.groovy.EN.Given
import static cucumber.api.groovy.EN.Then
import static cucumber.api.groovy.EN.When
import static org.hamcrest.Matchers.is

Given(~/^a (\w+) SFTP server$/) { String name ->
    connectToSFTP()
}

When(~/^I SSH login to the server$/) { ->
    connectToSFTPChannel()
}

And(~/^navigate to (.+)$/) { String destDir ->
    listFiles destDir
}

Then(~/^I should know the amount of files is (\d+)$/) { int amount ->
    assertThat amount, is(fileAmount)
    disconnectFromSFTP()
}
