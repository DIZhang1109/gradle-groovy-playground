import geb.report.ReportState
import geb.report.Reporter
import geb.report.ReportingListener
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver

baseUrl = 'http://www.practiceselenium.com/'

environments {

    firefox {
        driver = { new FirefoxDriver() }
    }

    chrome {
        driver = { new ChromeDriver() }
    }

    // This is a simple web driver test in docker-selenium
    remote {
        driver = {
            def remoteWebDriverServerUrl = new URL('http://localhost:4444/wd/hub')
            new RemoteWebDriver(remoteWebDriverServerUrl, DesiredCapabilities.chrome())
        }
    }
}

waiting {
    timeout = 10
    retryInterval = 0.5

    includeCauseInMessage = true
}

atCheckWaiting = true

baseNavigatorWaiting = true

reportsDir = 'build/geb-reports'

reportingListener = new ReportingListener() {
    void onReport(Reporter reporter, ReportState reportState, List<File> reportFiles) {
        reportFiles.each {
            println "[[ATTACHMENT|$it.absolutePath]]"
        }
    }
}