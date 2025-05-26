import com.example.scratchinterpretermobile.Controller.calculationArithmeticExpression
import com.example.scratchinterpretermobile.Controller.calculationStringExpression
import com.example.scratchinterpretermobile.Model.IntegerArrayBlock
import com.example.scratchinterpretermobile.Model.IntegerBlock
import com.example.scratchinterpretermobile.Model.StringBlock
import com.example.scratchinterpretermobile.Model.UIContext
import com.example.scratchinterpretermobile.Model.VarBlock
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

class CalculationStringExpressionUnitTest {
    @Test
    fun testBasicArithmetic() {
        val scope = hashMapOf<String, VarBlock<*>>()

        scope["x"] = IntegerBlock("x", 10)
        scope["y"] = IntegerBlock("y", 5)
        scope["z"] = IntegerBlock("z", 1)
        scope["s"] = StringBlock("s", "huy")

        UIContext.pushScope(scope)

        val (result, error) = calculationStringExpression("s * (y + z)")

        assertEquals(0, error)
        assertEquals("huyhuyhuyhuyhuyhuy", result)
    }

    @Test
    fun testWithArrayAccess() {
        val scope = hashMapOf<String, VarBlock<*>>()
        scope["i"] = IntegerBlock("i", 2)
        scope["str"] = StringBlock("str", "pupupu")

        scope["arr"] = IntegerArrayBlock("arr", mutableListOf(1, 2, 3, 4))

        UIContext.pushScope(scope)

        val (result, error) = calculationStringExpression("arr[i + 1] * str + \" \" + \"bubu\"")

        assertEquals(0, error)
        assertEquals("pupupupupupupupupupupupu bubu", result)
    }


    @After
    fun tearDown() {
        UIContext.clear()
    }
}