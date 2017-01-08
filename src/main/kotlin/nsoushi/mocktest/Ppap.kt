package nsoushi.mocktest

/**
 *
 * @author nsoushi
 */

interface Ppap {

    fun leftHand(sing: Boolean): String

    fun rightHand(sing: Boolean): String

    fun woo(right: String, left: String): String
}

open class PpapImpl constructor(val right: String, val left: String) : Ppap {

    val iHaveA = "I have"

    override fun leftHand(sing: Boolean): String {
        if (sing)
            return "$iHaveA $left."
        else
            return getObj(left)
    }

    override fun rightHand(sing: Boolean): String {
        if (sing)
            return "$iHaveA $right."
        else
            return getObj(right)
    }

    override fun woo(right: String, left: String): String = "%s%s.".format(left, right)

    private fun getObj(obj: String) = obj.split(Regex("\\s+")).get(1)
}

class PpapSong constructor(val ppap: PpapImpl) {

    fun sing(): String {
        return "%s %s woo %s".format(
                ppap.rightHand(true),
                ppap.leftHand(true),
                ppap.woo(ppap.rightHand(false), ppap.leftHand(false)))
    }
}
