import org.junit.Test
import org.junit.Assert.*
import com.example.scratchinterpretermobile.Controller.Utils.validateNameVariable

class ValidateNameVariableUnitTest {
    @Test
    fun validName_returnsSuccess() {
        val error = validateNameVariable("a123");
        assertEquals(0, error)
    }

    @Test
    fun startsWithDigit_returnsError101() {
        val error = validateNameVariable("123");
        assertEquals(101, error)
    }

    @Test
    fun startByUnderscores_returnsSuccess() {
        val error = validateNameVariable("_123");
        assertEquals(0, error)
    }

    @Test
    fun invalidCharacters_returnsError103() {
        val error = validateNameVariable("a-b");
        assertEquals(103, error)
    }

    @Test
    fun containsSpace_returnsError102() {
        val error = validateNameVariable("mew mew mew");
        assertEquals(102, error)
    }

    @Test
    fun emptyInput_returnsError104() {
        val error = validateNameVariable("   ")
        assertEquals(104, error)
    }
}
