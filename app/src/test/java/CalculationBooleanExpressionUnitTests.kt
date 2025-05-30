import com.example.scratchinterpretermobile.Controller.Error.SUCCESS
import com.example.scratchinterpretermobile.Controller.Utils.calculationBooleanExpression
import com.example.scratchinterpretermobile.Model.UIContext
import org.junit.Assert.assertEquals
import org.junit.Test

class CalculationBooleanExpressionUnitTests {
    @Test
    fun testSimpleTrueExpression() {
        val input = "3 > 2"
        val result = calculationBooleanExpression(input, UIContext)
        assertEquals(Pair(true, SUCCESS.id), result)
    }
}