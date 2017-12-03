package stepdefs.wssecurity

import org.apache.ws.security.WSConstants
import wssecurity.Encryption
import wssecurity.Signature
import wssecurity.Timestamp
import wssecurity.Username
import wssecurity.WssEntry

import static cucumber.api.groovy.EN.*

/**
 * Created by zhangd on 29/11/2017.
 * wsSecuritySample.feature step definitions
 */

Encryption encryption
Signature signature
Timestamp timestamp
Username usernameToken

Given(~/^a SOAP request to be signed$/) { ->
    encryption = new Encryption()
    encryption.with {
        keystoreFilePath = 'src/cucumberTest/resources/payload/wssecurity/encryption/wss40.jks'
        alias = 'wss40'
        password = 'security'
        keyIdentifierType = WSConstants.BST_DIRECT_REFERENCE
        symmetricEncodingAlgorithm = WSConstants.TRIPLE_DES
        keyEncryptionAlgorithm = WSConstants.KEYTRANSPORT_RSAOEP
        createEncryptedKey = true
    }

    signature = new Signature()
    signature.with {
        keystoreFilePath = 'src/cucumberTest/resources/payload/wssecurity/signature/wss40.jks'
        alias = 'wss40'
        password = 'security'
        keyIdentifierType = WSConstants.BST_DIRECT_REFERENCE
        signatureAlgorithm = WSConstants.RSA_SHA1
        signatureCanonicalization = WSConstants.C14N_EXCL_OMIT_COMMENTS
        digestAlgorithm = WSConstants.SHA1
        useSingleCertificate = true
    }

    timestamp = new Timestamp()
    timestamp.timeToLive = 60000

    usernameToken = new Username()
    usernameToken.with {
        username = 'wernerd'
        password = 'verySecret'
        passwordType = WSConstants.PASSWORD_TEXT
    }
}

Then(~/^I apply security header (\w+) to it$/) { String type ->
    ArrayList<WssEntry> wssEntries = []

    if (type == 'Encryption') {
        wssEntries.add encryption
    } else if (type == 'SAML') {
        println 'SAML not implemented yet'
    } else if (type == 'Signature') {
        wssEntries.add signature
    } else if (type == 'Timestamp') {
        wssEntries.add timestamp
    } else if (type == 'Username') {
        wssEntries.add usernameToken
    } else if (type == 'All') {
        wssEntries.with {
            add encryption
            add signature
            add timestamp
            add usernameToken
        }
    }

    generateSecurityHeader type, wssEntries
}

And(~/^I should get a signed (\w+) SOAP request$/) { String type ->
    writePlainText "Actual request xml:\n$actualRequestXml"
    compareXml type, actualRequestXml, expectedRequestXml
}
