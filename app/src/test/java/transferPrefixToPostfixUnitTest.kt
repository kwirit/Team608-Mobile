import org.junit.Test
import org.junit.Assert.*
import com.example.scratchinterpretermobile.Controller.transferPrefixToPostfix

class transferPrefixToPostfixUnitTest {
    @Test
    fun test() {
        val result = transferPrefixToPostfix(
            listOf(
                "a", "+", "b", "-", "69", "*", "(", "1000", "-", "7", ")").toMutableList()
        );
        assertEquals(listOf("a", "b", "+", "1000", "7", "-", "69", "*", "-"), result)
    }

}
