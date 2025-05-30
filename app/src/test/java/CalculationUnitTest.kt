import org.junit.Test
import com.example.scratchinterpretermobile.Model.*
import com.example.scratchinterpretermobile.Controller.Utils.calculationArithmeticExpression
import org.junit.After
import org.junit.Assert.*

class CalculationUnitTest {
    @Test
    fun testBasicArithmetic() {
        val scope = hashMapOf<String, VarBlock<*>>()

        scope["x"] = IntegerBlock("x", 10)
        scope["y"] = IntegerBlock("y", 5)
        scope["z"] = IntegerBlock("z", 1)

        UIContext.pushScope(scope)

        val (result, error) = calculationArithmeticExpression("x + y - z", UIContext)

        assertEquals(14, result)
    }

    @Test
    fun testWithArrayAccess() {
        val scope = hashMapOf<String, VarBlock<*>>()
        scope["i"] = IntegerBlock("i", 2)

        scope["arr"] = IntegerArrayBlock("arr", mutableListOf(10, 20, 30, 40))

        UIContext.pushScope(scope)

        val (result, error) = calculationArithmeticExpression("arr[i + 1]", UIContext)

        assertEquals(40, result)
    }

    @Test
    fun testNestedExpressions() {
        val scope = hashMapOf<String, VarBlock<*>>()
        scope["a"] = IntegerBlock("a", 10)
        scope["b"] = IntegerBlock("b", 2)
        scope["c"] = IntegerBlock("c", 3)
        UIContext.pushScope(scope)


        val (result, error) = calculationArithmeticExpression("a + b * (c - 1)", UIContext)

        assertEquals(14, result)
    }

    @Test
    fun testArifInArray() {
        val scope = hashMapOf<String, VarBlock<*>>()
        scope["a"] = IntegerBlock("a", 1)
        scope["i"] = IntegerBlock("i", 2)
        scope["b"] = IntegerBlock("b", 2)
        scope["arr"] = IntegerArrayBlock("arr", mutableListOf(100, 23, 11, 105, 1, 1231, 1554, 5, 64))
        UIContext.pushScope(scope)


        val (result, error) = calculationArithmeticExpression("arr[    1 + i    + a] + arr    [i +     b] * arr[i * i + b]", UIContext)

        assertEquals(1 + 1 * 1554, result)
    }
    @Test
    fun testArrayInIndexArray() {
        val scope = hashMapOf<String, VarBlock<*>>()
        scope["a"] = IntegerBlock("a", 1)
        scope["i"] = IntegerBlock("i", 2)
        scope["b"] = IntegerBlock("b", 2)
        scope["arr"] = IntegerArrayBlock("arr", mutableListOf(100, 200, 300, 400, 500, 600, 700, 800, 900))
        scope["arr2"] = IntegerArrayBlock("arr2", mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8))

        UIContext.pushScope(scope)

        val (result, error) = calculationArithmeticExpression("arr[arr2[arr2[i + 1] + 0 * (100 - 1232131)]]", UIContext)
        assertEquals(0, error)
        assertEquals(400, result)

    }

    @Test
    fun testDeeplyNestedIndex() {
        val scope = hashMapOf<String, VarBlock<*>>().apply {
            this["i"] = IntegerBlock("i", 2)
            this["j"] = IntegerBlock("j", 3)
            this["k"] = IntegerBlock("k", 4)
            this["a"] = IntegerBlock("a", 0)
            this["b"] = IntegerBlock("b", 1)
            this["c"] = IntegerBlock("c", 5)
            this["d"] = IntegerBlock("d", 2)
            this["arr"] = IntegerArrayBlock("arr", mutableListOf(10, 20, 30, 40, 50))
            this["arr2"] = IntegerArrayBlock("arr2", mutableListOf(0, 1, 2, 3, 4))
        }
        UIContext.pushScope(scope)
        val (result, error) = calculationArithmeticExpression("arr[(((i + j) * k) / (a + b + c + d))]", UIContext)

        assertEquals(30, result)
        assertEquals(0, error)
    }


    @Test
    fun testMultipleOperatorsAndBrackets() {
        val scope = hashMapOf<String, VarBlock<*>>().apply {
            this["i"] = IntegerBlock("i", 2)
            this["j"] = IntegerBlock("j", 3)
            this["k"] = IntegerBlock("k", 4)
            this["a"] = IntegerBlock("a", 0)
            this["b"] = IntegerBlock("b", 1)
            this["c"] = IntegerBlock("c", 5)
            this["d"] = IntegerBlock("d", 2)
            this["arr"] = IntegerArrayBlock("arr", mutableListOf(10, 20, 30, 40, 50))
            this["arr2"] = IntegerArrayBlock("arr2", mutableListOf(0, 1, 2, 3, 4))
        }
        UIContext.pushScope(scope)
        val (result, error) = calculationArithmeticExpression("arr[(a + b) * (1 + (0 + 0)) * (c - d)]", UIContext)

        assertEquals(40, result)
        assertEquals(0, error)
    }
    @Test
    fun testUnMinus() {

        val (result, error) = calculationArithmeticExpression("-3", UIContext)
        assertEquals(-3, result)
        assertEquals(0, error)
    }

    @Test
    fun testMinusBeforeBracketsCircle() {

        val (result, error) = calculationArithmeticExpression("10 / -5", UIContext)
        assertEquals(-2, result)
        assertEquals(0, error)
    }

    @After
    fun tearDown() {
        UIContext.clear()
    }
}