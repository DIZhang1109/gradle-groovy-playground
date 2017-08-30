package runner

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

/**
 * Created by zhangd on 30/08/2017.
 * RunCucumberTest JUnit runner
 */
@RunWith(Cucumber.class)
@CucumberOptions(glue = 'src/cucumberTest/groovy',
        features = 'src/cucumberTest/resources',
        plugin = ['pretty', 'json:build/cucumber-reports/json-report/cucumber.json'])
class RunCucumberTest {
}
