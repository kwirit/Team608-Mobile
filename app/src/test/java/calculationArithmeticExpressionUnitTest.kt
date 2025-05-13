import org.junit.Test
import org.junit.Assert.*
import com.example.scratchinterpretermobile.Controller.calculationArithmeticExpression

class calculationArithmeticExpressionUnitTest {
    @Test
    fun test() {
        val (result, error) = calculationArithmeticExpression("10 + 5 - 11 *(321 % 10)")
        assertEquals(4, result)
    }

}
