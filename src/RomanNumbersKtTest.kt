import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RomanNumbersKtTest {

    @Test
    fun moreThanThreeInaRow() {
        assertFalse(isRomanNumber("IIII")) //test each character
        assertFalse(isRomanNumber("XXXX"))
        assertFalse(isRomanNumber("CCCC"))
        assertFalse(isRomanNumber("LXXXX"))//at the end
        assertFalse(isRomanNumber("XXXXV"))//at the beginning

        assertTrue(isRomanNumber("CCCXXXIII"))
        assertTrue(isRomanNumber("XXXIX")) //4, not in a row
    }

    @Test
    fun moreThanOneInaRow() {
        assertFalse(isRomanNumber("LLX"))//at the beginning
        assertFalse(isRomanNumber("VVI"))
        assertFalse(isRomanNumber("XLLV"))//at the middle
        assertFalse(isRomanNumber("XVVI"))
        assertFalse(isRomanNumber("CLL")) //at the end
        assertFalse(isRomanNumber("LVV"))
    }

    @Test
    fun subtraction() {
        assertFalse(isRomanNumber("IL")) //illegal subtraction
        assertFalse(isRomanNumber("IC"))
        assertFalse(isRomanNumber("LC"))

        assertTrue(isRomanNumber("IV"))
        assertTrue(isRomanNumber("XLV")) //at the beginning
        assertTrue(isRomanNumber("XIV")) //at the end
        assertTrue(isRomanNumber("CCXLIV"))//at the beginning, middle and end
    }

    @Test
    fun smallerFollowedByBigger() {
        assertFalse(isRomanNumber("IXC"))
        assertFalse(isRomanNumber("VX"))
        assertFalse(isRomanNumber("XLC"))
        assertFalse(isRomanNumber("IIXX"))
        assertFalse(isRomanNumber("XLXL")) //followed by same value

        assertTrue(isRomanNumber("XXII"))
        assertTrue(isRomanNumber("XLIX"))
        assertTrue(isRomanNumber("XIV"))
        assertTrue(isRomanNumber("CCCXCIX"))
    }

    @Test
    fun illegalCharacters() {
        assertFalse(isRomanNumber("Xt"))
        assertFalse(isRomanNumber("10"))
        assertFalse(isRomanNumber("Vi")) //lowercase i
        assertFalse(isRomanNumber("XX ")) //contains space
    }

}