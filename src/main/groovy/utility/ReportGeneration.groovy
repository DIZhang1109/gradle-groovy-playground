package utility

import com.github.mkolisnyk.cucumber.reporting.CucumberDetailedResults
import com.github.mkolisnyk.cucumber.reporting.CucumberFeatureOverview
import com.github.mkolisnyk.cucumber.reporting.CucumberResultsOverview

/**
 * Created by zhangd on 24/08/2017.
 * This groovy script is used to create and beautify various cucumber reports
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

def reportFilePath = System.getProperty('user.dir') + '/build/cucumber-reports/html-report/cucumber-results-agg-test-results.html'
def reportFile = new File(reportFilePath).text.replaceAll('</head>', '<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script><script>\\$(document).ready(function(){\\$(".tip").each(function(n){\\$(this).click(function(n){\\$(this).next().toggle("show")})}),\\$("pre.comment").css("display","none")});</script></head>')
new File(reportFilePath).write(reportFile)