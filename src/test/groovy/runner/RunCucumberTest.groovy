package runner

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

/**
 * Created by Di on 8/08/17.
 * RunCucumberTest
 */
@RunWith(Cucumber.class)
@CucumberOptions(glue = 'src/test/groovy',
        features = 'src/test/resources',
        plugin = ['pretty', 'json:build/cucumber-reports/json-report/cucumber.json'])
class RunCucumberTest {
}
