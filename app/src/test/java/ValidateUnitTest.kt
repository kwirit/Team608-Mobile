import org.junit.Test
import org.junit.Assert.*
import com.example.scratchinterpretermobile.Controller.validateNameVariable

class ValidateUnitTest {
    @Test
    fun validName_returnsSuccess() {
        val error = validateNameVariable("a123");
        assertEquals(0, error?.id ?: -1)
    }

    @Test
    fun startsWithDigit_returnsError101() {
        val error = validateNameVariable("123");
        assertEquals(101, error?.id ?: -1)
    }

    @Test
    fun startByUnderscores_returnsSuccess() {
        val error = validateNameVariable("_123");
        assertEquals(0, error?.id ?: -1)
    }

    @Test
    fun invalidCharacters_returnsError103() {
        val error = validateNameVariable("a-b");
        assertEquals(103, error?.id ?: -1)
    }

    @Test
    fun containsSpace_returnsError102() {
        val error = validateNameVariable("mew mew mew");
        assertEquals(102, error?.id ?: -1)
    }

    @Test
    fun emptyInput_returnsError104() {
        val error = validateNameVariable("   ")
        assertEquals(104, error?.id ?: -1)
    }
}
