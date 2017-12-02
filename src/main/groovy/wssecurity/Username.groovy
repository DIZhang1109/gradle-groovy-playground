package wssecurity

/**
 * Created by Di on 29/11/17.
 * Username
 */
class Username implements WssEntry {
    String username
    String password
    boolean nonce = true
    boolean created = true
    String passwordType

    WssEntry.Type type

    Username() {
        type = WssEntry.Type.Username
    }
}
