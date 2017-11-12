package runner

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

/**
 * Created by zhangd on 30/08/2017.
 * RunCucumberTest JUnit runner
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = 'src/cucumberTest/groovy/features',
        glue = 'src/cucumberTest/groovy/stepdefs',
        plugin = ['pretty', 'json:build/cucumber-reports/json-report/cucumber.json', 'usage:build/cucumber-reports/json-report/cucumber-usage.json'])
class RunCucumberTest {
}
