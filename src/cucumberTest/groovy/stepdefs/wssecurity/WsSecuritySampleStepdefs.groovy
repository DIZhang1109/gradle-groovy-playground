package stepdefs.wssecurity

import org.apache.ws.security.WSConstants
import wssecurity.Signature
import wssecurity.Timestamp
import wssecurity.Username
import wssecurity.WssEntry

import static cucumber.api.groovy.EN.*

/**
 * Created by zhangd on 29/11/2017.
 * wsSecuritySample.feature step definitions
 */

Signature signature
Timestamp timestamp
Username username

Given(~/^a SOAP request to be signed$/) { ->
    signature = new Signature()
    signature.keystoreFilePath = 'src/cucumberTest/resources/payload/wssecurity/signature/wss40.jks'
    signature.alias = 'wss40'
    signature.password = 'security'
    signature.keyIdentifierType = WSConstants.BST_DIRECT_REFERENCE
    signature.signatureAlgorithm = WSConstants.RSA_SHA1
    signature.signatureCanonicalization = WSConstants.C14N_EXCL_OMIT_COMMENTS
    signature.digestAlgorithm = WSConstants.SHA1
    signature.useSingleCertificate = true

    timestamp = new Timestamp()
    timestamp.timeToLive = 60000

    username = new Username()
    username.username = 'wernerd'
    username.password = 'verySecret'
    username.passwordType = WSConstants.PASSWORD_TEXT
}

Then(~/^I apply security header (\w+) to it$/) { String type ->
    ArrayList<WssEntry> wssEntries = []

    if (type == 'Encryption') {
        println 'Encryption not implemented yet'
    } else if (type == 'SAML') {
        println 'SAML not implemented yet'
    } else if (type == 'Signature') {
        wssEntries.add signature
    } else if (type == 'Timestamp') {
        wssEntries.add timestamp
    } else if (type == 'Username') {
        wssEntries.add username
    }

    generateSecurityHeader type, wssEntries
}

And(~/^I should get a signed (\w+) SOAP request$/) { String type ->
    writePlainText "Actual request xml:\n$actualRequestXml"
    compareXml type, actualRequestXml, expectedRequestXml
}
