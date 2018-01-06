package wssecurity

import groovy.util.logging.Slf4j
import groovy.xml.XmlUtil
import org.apache.ws.security.WSConstants
import org.apache.ws.security.WSSConfig
import org.apache.ws.security.WSSecurityException
import org.apache.ws.security.components.crypto.Merlin
import org.apache.ws.security.message.DOMCallbackLookup
import org.apache.ws.security.message.WSSecEncrypt
import org.apache.ws.security.message.WSSecHeader
import org.apache.ws.security.message.WSSecSignature
import org.apache.ws.security.message.WSSecTimestamp
import org.apache.ws.security.message.WSSecUsernameToken
import org.w3c.dom.Document
import org.w3c.dom.Element

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMResult
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
        String requestFilePath = "src/cucumber/resources/payload/wssecurity/${type.uncapitalize()}/request.xml"
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
                generateEncryptionToken secHeader, doc, it as Encryption
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

    static void generateEncryptionToken(WSSecHeader secHeader, Document doc, Encryption encryption) {
        Merlin crypto = createCrypto(encryption.keystoreFilePath, encryption.password)

        WSSecEncrypt builder = new WSSecEncrypt()
        builder.setUserInfo encryption.alias
        builder.setKeyIdentifierType encryption.keyIdentifierType
        builder.setEmbeddedKeyName encryption.embeddedKeyname
        (!encryption.embeddedKeyname && !encryption.embeddedKeyPassword) ?: builder.setKey(crypto.getPrivateKey(encryption.embeddedKeyname, encryption.embeddedKeyPassword).getEncoded())
        builder.setSymmetricEncAlgorithm encryption.symmetricEncodingAlgorithm
        builder.setKeyEnc encryption.keyEncryptionAlgorithm
        builder.setEncryptSymmKey encryption.createEncryptedKey
        builder.setParts encryption.parts

        builder.build doc, crypto, secHeader
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

        builder.setCallbackLookup(new BinarySecurityTokenDOMCallbackLookup(doc, builder))
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

    /**
     * This class is copied from SoapUI source code implementation, mainly for the encryption part is BinarySecurityToken
     *
     * This callback class extends the default DOMCallbackLookup class with a hook to return the prepared
     * wsse:BinarySecurityToken
     */
    private static class BinarySecurityTokenDOMCallbackLookup extends DOMCallbackLookup {
        private final WSSecSignature wssSign

        BinarySecurityTokenDOMCallbackLookup(Document doc, WSSecSignature wssSign) {
            super(doc)
            this.wssSign = wssSign
        }

        @Override
        List<Element> getElements(String localname, String namespace) throws WSSecurityException {
            List<Element> elements = super.getElements(localname, namespace)
            if (elements.isEmpty()) {
                // element was not found in DOM document
                if (WSConstants.BINARY_TOKEN_LN == localname && WSConstants.WSSE_NS == namespace) {
                    /* In case the element searched for is the wsse:BinarySecurityToken, return the element prepared by
                     wsee4j. If we return the original DOM element, the digest calculation fails because the element
                     is not yet attached to the DOM tree, so instead return a copy which includes all namespaces */
                    try {
                        DOMResult result = new DOMResult()
                        Transformer transformer = TransformerFactory.newInstance().newTransformer()
                        transformer.transform(new DOMSource(wssSign.getBinarySecurityTokenElement()), result)
                        return Collections.singletonList(((Document) result.getNode()).getDocumentElement())
                    } catch (TransformerException e) {
                        log.warn e.message
                    }
                }
            }
            return elements
        }
    }
}
