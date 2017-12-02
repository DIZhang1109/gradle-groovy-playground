package wssecurity

import groovy.util.logging.Slf4j
import groovy.xml.XmlUtil
import org.apache.ws.security.WSSConfig
import org.apache.ws.security.components.crypto.Merlin
import org.apache.ws.security.message.WSSecHeader
import org.apache.ws.security.message.WSSecSignature
import org.apache.ws.security.message.WSSecTimestamp
import org.apache.ws.security.message.WSSecUsernameToken
import org.w3c.dom.Document

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import java.nio.charset.StandardCharsets
import java.security.KeyStore

/**
 * Created by Di on 29/11/17.
 * WsSecurityService
 */
@Slf4j
class WsSecurityService {
    String expectedRequestXml
    String actualRequestXml

    /**
     * Convert an SOAP Envelope as a String to a org.w3c.dom.Document.
     *
     * @param requestFilePath
     * @return
     */
    Document toSOAPPart(String type) {
        String requestFilePath = "src/cucumberTest/resources/payload/wssecurity/${type.uncapitalize()}/request.xml"
        expectedRequestXml = new File(requestFilePath.replaceAll('request', 'expectedRequest')).text
        InputStream inputStream = new ByteArrayInputStream(new File(requestFilePath).text.getBytes())
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance()
        factory.setNamespaceAware true
        DocumentBuilder builder = factory.newDocumentBuilder()
        builder.parse inputStream
    }

    void generateSecurityHeader(String type, ArrayList<WssEntry> wssEntries) {
        Document doc = toSOAPPart(type)

        WSSecHeader secHeader = new WSSecHeader()
        secHeader.insertSecurityHeader doc

        wssEntries.each {
            if (it.getType() == WssEntry.Type.Encryption) {
                log.info 'Encryption'
            } else if (it.getType() == WssEntry.Type.SAML) {
                log.info 'SAML'
            } else if (it.getType() == WssEntry.Type.Signature) {
                generateSignatureToken secHeader, doc, it as Signature
            } else if (it.getType() == WssEntry.Type.Timestamp) {
                generateTimestampToken secHeader, doc, it as Timestamp
            } else if (it.getType() == WssEntry.Type.Username) {
                generateUsernameToken secHeader, doc, it as Username
            }
        }

        actualRequestXml = XmlUtil.serialize(docToString(doc))
    }

    static void generateSignatureToken(WSSecHeader secHeader, Document doc, Signature signature) {
        Merlin crypto = createCrypto(signature.keystoreFilePath, signature.password)

        WSSecSignature builder = new WSSecSignature()
        builder.setUserInfo signature.alias, signature.password
        builder.setKeyIdentifierType signature.keyIdentifierType
        builder.setSignatureAlgorithm signature.signatureAlgorithm
        builder.setSigCanonicalization signature.signatureCanonicalization
        builder.setDigestAlgo signature.digestAlgorithm
        builder.setUseSingleCertificate signature.useSingleCertificate
        builder.setParts signature.parts

        builder.build doc, crypto, secHeader
    }

    private static Merlin createCrypto(String keystoreFilePath, String keystorePassword) {
        WSSConfig.init()
        Merlin crypto = new Merlin()

        FileInputStream is = new FileInputStream(keystoreFilePath)
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType())
        keystore.load is, keystorePassword.toCharArray()
        crypto.setKeyStore keystore
        crypto
    }

    static void generateTimestampToken(WSSecHeader secHeader, Document doc, Timestamp timestamp) {
        WSSConfig config = new WSSConfig()
        config.setPrecisionInMilliSeconds timestamp.millisecondPrecision

        WSSecTimestamp builder = new WSSecTimestamp(config)
        builder.setTimeToLive timestamp.timeToLive

        builder.build doc, secHeader
    }

    static void generateUsernameToken(WSSecHeader secHeader, Document doc, Username usernameToken) {
        WSSecUsernameToken builder = new WSSecUsernameToken()
        builder.setUserInfo usernameToken.username, usernameToken.password
        (!usernameToken.nonce) ?: builder.addNonce()
        (!usernameToken.created) ?: builder.addCreated()
        builder.setPasswordType usernameToken.passwordType

        builder.build doc, secHeader
    }

    static String docToString(Document doc) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        DOMSource source = new DOMSource(doc.getDocumentElement())
        StreamResult result = new StreamResult(baos)
        TransformerFactory transFactory = TransformerFactory.newInstance()
        Transformer transformer = transFactory.newTransformer()
        transformer.transform source, result
        new String(baos.toByteArray(), StandardCharsets.UTF_8)
    }
}
