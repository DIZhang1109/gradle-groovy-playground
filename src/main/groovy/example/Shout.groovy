package example

/**
 * Created by Di on 8/08/17.
 * Shout
 */
class Shout {
    static void shout(word) {
        println word
    }

    static void shoutLouder(word) {
        println word * 3 + '!'
    }
}
