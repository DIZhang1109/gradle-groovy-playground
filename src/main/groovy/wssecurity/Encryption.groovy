package wssecurity

import org.apache.ws.security.WSEncryptionPart

/**
 * Created by Di on 2/12/17.
 * Encryption
 */
class Encryption implements WssEntry {
    String keystoreFilePath
    String alias
    String password
    int keyIdentifierType
    String embeddedKeyname
    String embeddedKeyPassword
    String symmetricEncodingAlgorithm
    String keyEncryptionAlgorithm
    boolean createEncryptedKey
    List<WSEncryptionPart> parts

    WssEntry.Type type

    Encryption() {
        type = WssEntry.Type.Encryption
    }
}
