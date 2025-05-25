import org.junit.Test
import com.example.scratchinterpretermobile.Model.*
import com.example.scratchinterpretermobile.Controller.*
import org.junit.After
import org.junit.Assert.*

class calculationUnitTest {
    @Test
    fun testBasicArithmetic() {
        val scope = hashMapOf<String, VarBlock<*>>()

        scope["x"] = IntegerBlock("x", 10)
        scope["y"] = IntegerBlock("y", 5)
        scope["z"] = IntegerBlock("z", 1)

        UIContext.pushScope(scope)

        val (result, error) = calculationArithmeticExpression("x + y - z")

        assertEquals(14, result)
    }

    @Test
    fun testWithArrayAccess() {
        val scope = hashMapOf<String, VarBlock<*>>()
        scope["i"] = IntegerBlock("i", 2)

        scope["arr"] = IntegerArrayBlock("arr", mutableListOf(10, 20, 30, 40))

        UIContext.pushScope(scope)

        val (result, error) = calculationArithmeticExpression("arr[i + 1]")

        assertEquals(40, result)
    }

    @Test
    fun testNestedExpressions() {
        val scope = hashMapOf<String, VarBlock<*>>()
        scope["a"] = IntegerBlock("a", 10)
        scope["b"] = IntegerBlock("b", 2)
        scope["c"] = IntegerBlock("c", 3)
        UIContext.pushScope(scope)


        val (result, error) = calculationArithmeticExpression("a + b * (c - 1)")

        assertEquals(14, result)
    }

    @Test
    fun testArifInArray() {
        val scope = hashMapOf<String, VarBlock>()
        scope["a"] = IntegerBlock("a", 1)
        scope["i"] = IntegerBlock("i", 2)
        scope["b"] = IntegerBlock("b", 2)
        scope["arr"] = IntegerArrayBlock("arr", mutableListOf(100, 23, 11, 105, 1, 1231, 1554, 5, 64))
        UIContext.pushScope(scope)


        val (result, error) = calculationArithmeticExpression("arr[    1 + i    + a] + arr    [i +     b] * arr[i * i + b]")

        assertEquals(1 + 1 * 1554, result)
    }

    @Test
    fun testArrayInArray() {
        val scope = hashMapOf<String, VarBlock<*>>()
        scope["a"] = IntegerBlock("a", 1)
        scope["i"] = IntegerBlock("i", 2)
        scope["b"] = IntegerBlock("b", 2)
        scope["arr2"] = IntegerArrayBlock("arr2", mutableListOf(1, 2, 3))
        scope["arr"] = IntegerArrayBlock("arr", mutableListOf(100, 23, 11, 105, 1, 1231, 1554, 5, 64))
        UIContext.pushScope(scope)

        val result = getElementFromString("asda[ 1 + (4 + 0) * 123 - (123 - 1000)]")
        assertEquals(listOf("asda[1+(4+0)*123 - (123-1000)"), result)
    }

    @After
    fun tearDown() {
        UIContext.clear()
    }
}