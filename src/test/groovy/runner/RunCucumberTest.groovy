package runner

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions
import cucumber.api.CucumberOptions
import org.junit.runner.RunWith

/**
 * Created by Di on 8/08/17.
 * RunCucumberTest
 */
@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(jsonReport = 'build/cucumber-reports/json-report/cucumber.json',
        overviewReport = true,
        detailedReport = true,
        detailedAggregatedReport = true,
        toPDF = true,
        outputFolder = 'build/cucumber-reports/html-report/')
@CucumberOptions(glue = 'src/test/groovy',
        features = 'src/test/resources',
        plugin = ['pretty', 'json:build/cucumber-reports/json-report/cucumber.json'])
class RunCucumberTest {
}
