import org.junit.Test
import org.junit.Assert.*
import com.example.scratchinterpretermobile.Controller.getElementFromString

class getElementFromStringUnitTest {
    @Test
    fun validName_returnsSuccess() {
        val result = getElementFromString("a + 232 - 12+b * (5 % 10 (2 + 1) / ad)");
        assertEquals(listOf("a", "+", "232", "-", "12", "+", "b", "*", "(", "5", "%", "10", "(", "2", "+", "1", ")", "/", "ad", ")"), result)
    }

}
