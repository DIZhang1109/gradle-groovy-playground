package wssecurity

import org.apache.ws.security.WSEncryptionPart

/**
 * Created by Di on 29/11/17.
 * Signature
 */
class Signature implements WssEntry {
    String keystoreFilePath
    String alias
    String password
    int keyIdentifierType
    String signatureAlgorithm
    String signatureCanonicalization
    String digestAlgorithm
    boolean useSingleCertificate
    List<WSEncryptionPart> parts

    WssEntry.Type type

    Signature() {
        type = WssEntry.Type.Signature
    }
}
