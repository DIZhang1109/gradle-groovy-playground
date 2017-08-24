package utility

import com.github.mkolisnyk.cucumber.reporting.CucumberDetailedResults
import com.github.mkolisnyk.cucumber.reporting.CucumberFeatureOverview
import com.github.mkolisnyk.cucumber.reporting.CucumberResultsOverview

/**
 * Created by zhangd on 24/08/2017.
 * This groovy script is used to create various cucumber reports
 */
CucumberResultsOverview cucumberResultsOverview = new CucumberResultsOverview()
cucumberResultsOverview.setSourceFile 'build/cucumber-reports/json-report/cucumber.json'
cucumberResultsOverview.setOutputName 'cucumber-results'
cucumberResultsOverview.setOutputDirectory 'build/cucumber-reports/html-report/'
cucumberResultsOverview.execute(true)

CucumberDetailedResults cucumberDetailedResults = new CucumberDetailedResults()
cucumberDetailedResults.setSourceFile 'build/cucumber-reports/json-report/cucumber.json'
cucumberDetailedResults.setOutputName 'cucumber-results'
cucumberDetailedResults.setOutputDirectory 'build/cucumber-reports/html-report/'
cucumberDetailedResults.execute(true)

CucumberFeatureOverview cucumberFeatureOverview = new CucumberFeatureOverview()
cucumberFeatureOverview.setSourceFile 'build/cucumber-reports/json-report/cucumber.json'
cucumberFeatureOverview.setOutputName 'cucumber-results'
cucumberFeatureOverview.setOutputDirectory 'build/cucumber-reports/html-report/'
cucumberFeatureOverview.execute(true)