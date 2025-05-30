import com.example.scratchinterpretermobile.Controller.Error.DIVISION_BY_ZERO
import com.example.scratchinterpretermobile.Controller.Error.EMPTY_ARITHMETIC
import com.example.scratchinterpretermobile.Controller.Error.INVALID_CHARACTERS
import com.example.scratchinterpretermobile.Controller.Error.SUCCESS
import com.example.scratchinterpretermobile.Controller.Error.TYPE_MISMATCH
import com.example.scratchinterpretermobile.Controller.Utils.calculationBooleanExpression
import com.example.scratchinterpretermobile.Controller.Utils.calculationStringExpression
import com.example.scratchinterpretermobile.Model.IntegerArrayBlock
import com.example.scratchinterpretermobile.Model.IntegerBlock
import com.example.scratchinterpretermobile.Model.StringBlock
import com.example.scratchinterpretermobile.Model.UIContext
import com.example.scratchinterpretermobile.Model.VarBlock
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CalculationBooleanExpressionUnitTests {
    @Test
    fun testSimpleTrueExpression() {
        val input = "3 > 2"
        val result = calculationBooleanExpression(input, UIContext)
        assertEquals(Pair(true, SUCCESS.id), result)
    }

    @Test
    fun testSimpleFalseExpression() {
        val input = "3 < 2"
        val result = calculationBooleanExpression(input, UIContext)
        assertEquals(Pair(false, SUCCESS.id), result)
    }

    @Test
    fun testEqualityCheck() {
        val input = "5 == 5"
        val result = calculationBooleanExpression(input, UIContext)
        assertEquals(Pair(true, SUCCESS.id), result)
    }

    @Test
    fun testNotEqual() {
        val input = "5 != 6"
        val result = calculationBooleanExpression(input, UIContext)
        assertEquals(Pair(true, SUCCESS.id), result)
    }

    @Test
    fun testLogicalAnd() {
        val input = "(3 < 4) && (5 > 4)"
        val result = calculationBooleanExpression(input, UIContext)
        assertEquals(Pair(true, SUCCESS.id), result)
    }

    @Test
    fun testLogicalOr() {
        val input = "(3 > 4) || (5 > 4)"
        val result = calculationBooleanExpression(input, UIContext)
        assertEquals(Pair(true, SUCCESS.id), result)
    }

    @Test
    fun testNestedLogicalOperations() {
        val input = "(3 < 4 || 5 > 6) && (7 != 8)"
        val result = calculationBooleanExpression(input, UIContext)
        assertEquals(Pair(true, SUCCESS.id), result)
    }

    @Test
    fun testEmptyInput() {
        val input = ""
        val result = calculationBooleanExpression(input, UIContext)
        assertEquals(Pair(false, EMPTY_ARITHMETIC.id), result)
    }

    @Test
    fun testInvalidCharacters() {
        val input = "3 > @ 2"
        val result = calculationBooleanExpression(input, UIContext)
        assertTrue(result.second != 0)
    }

    @Test
    fun testTypeMismatchBetweenBooleanAndNumber() {
        val input = "true == 0"
        val result = calculationBooleanExpression(input, UIContext)
        assertTrue(result.second != 0)
    }

    @Test
    fun testDivisionByZeroInExpression() {
        val input = "5 / 0 > 0"
        val result = calculationBooleanExpression(input, UIContext)
        assertEquals(Pair(false, DIVISION_BY_ZERO.id), result)
    }

    @Test
    fun testVar() {
        val scope = hashMapOf<String, VarBlock<*>>()

        scope["x"] = IntegerBlock("x", 10)
        scope["y"] = IntegerBlock("y", 5)
        scope["z"] = IntegerBlock("z", 1)

        UIContext.pushScope(scope)

        val (result, error) = calculationBooleanExpression("!(x > 5) || (x + y + z > 15) && !true", context = UIContext)

        assertEquals(0, error)
        assertEquals(false, result)
    }

    @Test
    fun testVarOr() {
        val scope = hashMapOf<String, VarBlock<*>>()

        scope["x"] = IntegerBlock("x", 10)
        scope["y"] = IntegerBlock("y", 5)
        scope["z"] = IntegerBlock("z", 1)
        scope["arr"] = IntegerArrayBlock("arr", mutableListOf(1, 2, 3, 4, 5))

        UIContext.pushScope(scope)

        val (result, error) = calculationBooleanExpression("x + y + arr[z] > 15 || (5 * 10 > 49 && z < 0)", context = UIContext)

        assertEquals(0, error)
        assertEquals(true, result)
    }
}