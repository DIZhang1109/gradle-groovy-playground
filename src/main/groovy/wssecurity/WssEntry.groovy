package wssecurity

/**
 * Created by Di on 29/11/17.
 * WssEntry
 */
interface WssEntry {
    enum Type {
        Encryption, SAML, Signature, Timestamp, Username
    }

    def getType()
}