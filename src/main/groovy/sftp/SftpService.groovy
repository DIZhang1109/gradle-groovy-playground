package sftp

import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.ChannelSftp.LsEntry
import com.jcraft.jsch.JSch
import com.jcraft.jsch.JSchException
import com.jcraft.jsch.Session
import groovy.util.logging.Slf4j
import model.EndpointService
import static org.junit.Assert.fail

/**
 * Created by Di on 11/11/17.
 * SftpService
 */
@Slf4j
class SftpService implements EndpointService {
    Session session
    ChannelSftp channel
    int fileAmount

    void initiateSFTPEndpoint(String service) {
        log.info "Instantiate a SFTP $service service"
        initiateEndpoint 'SFTP', service
    }

    void connectToSFTP() {
        JSch.setConfig 'StrictHostKeyChecking', 'no'
        JSch jsch = new JSch()
        if (endpoint) {
            try {
                session = jsch.getSession('demo', endpoint, 22)
                session.setPassword 'password'
                session.connect 30000
                (session.connected) ? log.info('Connected to SFTP server successfully') : log.info('Error connected to SFTP server')
            } catch (JSchException e) {
                log.error 'Unable to connect to SFTP server: ' + e.toString()
            }
        } else {
            fail 'SFTP server address is not available'
        }
    }

    void connectToSFTPChannel() {
        channel = session.openChannel('sftp') as ChannelSftp
        channel.connect 30000
        (channel.connected) ? log.info('Connected to SFTP channel successfully') : log.info('Error connected to SFTP channel')
    }

    void disconnectFromSFTP() {
        channel.disconnect()
        session.disconnect()
        (!session.connected) ? log.info('Disconnected from SFTP server successfully') : log.info('Error disconnected from SFTP server')
    }

    void listFiles(String destDir) {
        Vector<LsEntry> fileList = channel.ls(destDir)
        fileList.each { file ->
            log.info "File name: ${file.getFilename()}"
        }
        fileAmount = fileList.size()
    }
}
