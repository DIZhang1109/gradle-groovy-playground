package utility

import com.github.mkolisnyk.cucumber.reporting.CucumberConsolidatedReport
import com.github.mkolisnyk.cucumber.reporting.CucumberCoverageOverview
import com.github.mkolisnyk.cucumber.reporting.CucumberDetailedResults
import com.github.mkolisnyk.cucumber.reporting.CucumberFeatureOverview
import com.github.mkolisnyk.cucumber.reporting.CucumberResultsOverview
import com.github.mkolisnyk.cucumber.reporting.CucumberSystemInfo
import com.github.mkolisnyk.cucumber.reporting.CucumberUsageReporting

/**
 * Created by zhangd on 24/08/2017.
 * This groovy script is used to create various cucumber reports
 */
String outputDir = 'build/cucumber-reports/html-report/'
String outputName = 'cucumber'
String soureFilePath = 'build/cucumber-reports/json-report/cucumber.json'
String jsonUsageFilePath = 'build/cucumber-reports/json-report/cucumber-usage.json'

new CucumberResultsOverview().with {
    setOutputDirectory outputDir
    setOutputName outputName
    setSourceFile soureFilePath
    execute()
}

new CucumberDetailedResults().with {
    setOutputDirectory outputDir
    setOutputName outputName
    setSourceFile soureFilePath
    execute()
}

new CucumberCoverageOverview().with {
    setOutputDirectory outputDir
    setOutputName outputName
    setSourceFile soureFilePath
    execute()
}

new CucumberFeatureOverview().with {
    setOutputDirectory outputDir
    setOutputName outputName
    setSourceFile soureFilePath
    execute()
}

new CucumberUsageReporting().with {
    setOutputDirectory outputDir
    setOutputName outputName
    setJsonUsageFile jsonUsageFilePath
    execute()
}

new CucumberSystemInfo().with {
    setOutputDirectory outputDir
    setOutputName outputName
    execute()
}

new CucumberConsolidatedReport().with {
    setOutputDirectory outputDir
    setOutputName outputName
    setPdfPageSize 'A4 landscape'
    setSourceFile soureFilePath
    execute(new File('./src/main/resources/consolidated_report.json'), 'html')
}

static beautifyCucumberReport(String path) {
    def reportFile = new File(path).text.replaceAll('</head>', '<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script><script>\\$(document).ready(function(){\\$(".tip").each(function(n){\\$(this).click(function(n){\\$(this).next().toggle("show")})}),\\$("pre.comment").css("display","none")});</script></head>').replaceAll('<br>', '')
    new File(path).write(reportFile)
}

beautifyCucumberReport System.getProperty('user.dir') + "/$outputDir/cucumber-test-results.html"
beautifyCucumberReport System.getProperty('user.dir') + "/$outputDir/cucumber-consolidated-report.html"
