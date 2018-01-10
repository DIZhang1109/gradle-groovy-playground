package sftp

import spock.lang.Narrative
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

/**
 * Created by Di on 24/12/17.
 * Unit test for SftpService
 */
@Title("Unit test for SftpService")
@Narrative("Test the methods in RestService")
@Subject(SftpService)
class SftpServiceSpec extends Specification {

    @Shared
    SftpService sftpService

    def setupSpec() {
        sftpService = new SftpService()
    }

    def "connect to sftp server"() {
        given: "initiate sftp server address"
        sftpService.initiateEndpoint(type, name)

        when: "connect to it"
        sftpService.connectToSFTP()

        then: "it should be connected"
        sftpService.session.connected == status

        where: "some scenarios"
        type   | name     || status
        'SFTP' | 'Sample' || true
    }
}
