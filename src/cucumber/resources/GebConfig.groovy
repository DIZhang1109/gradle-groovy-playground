import geb.report.ReportState
import geb.report.Reporter
import geb.report.ReportingListener

baseUrl = 'http://gebish.org'

waiting {
    timeout = 10
    retryInterval = 0.5

    includeCauseInMessage = true
}

atCheckWaiting = true

baseNavigatorWaiting = true

unexpectedPages = [PageNotFoundPage, InternalServerErrorPage]

reportsDir = 'build/geb-reports'

reportingListener = new ReportingListener() {
    void onReport(Reporter reporter, ReportState reportState, List<File> reportFiles) {
        reportFiles.each {
            println "[[ATTACHMENT|$it.absolutePath]]"
        }
    }
}