package wssecurity

/**
 * Created by Di on 29/11/17.
 * Timestamp
 */
class Timestamp implements WssEntry {
    int timeToLive
    boolean millisecondPrecision = true

    WssEntry.Type type

    Timestamp() {
        type = WssEntry.Type.Timestamp
    }
}
