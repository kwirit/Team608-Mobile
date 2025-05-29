import com.example.scratchinterpretermobile.Controller.Error.INVALID_FORMAT
import com.example.scratchinterpretermobile.Model.IntegerArrayBlock
import com.example.scratchinterpretermobile.Model.IntegerBlock
import com.example.scratchinterpretermobile.Model.PrintBlock
import com.example.scratchinterpretermobile.Model.StringBlock
import com.example.scratchinterpretermobile.Model.UIContext
import com.example.scratchinterpretermobile.Model.VarBlock
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

class PrintBlockUnitTest {
    @Test
    fun testBasicArithmetic() {
        val scope = hashMapOf<String, VarBlock<*>>()

        scope["x"] = IntegerBlock("x", 10)
        scope["y"] = IntegerBlock("y", 5)
        scope["z"] = IntegerBlock("z", 1)
        UIContext.pushScope(scope)

        var printBlock = PrintBlock();
        printBlock.updateOutput("x + y, y + z, x + z, x + y + z")

        val result = printBlock.run()
        assertEquals(0, result)
        assertEquals("1561116", printBlock.consoleOutput)
    }

    @Test
    fun testErrorNotSeparator() {
        val scope = hashMapOf<String, VarBlock<*>>()

        scope["x"] = IntegerBlock("x", 10)
        scope["y"] = IntegerBlock("y", 5)
        scope["z"] = IntegerBlock("z", 1)
        scope["arr"] = IntegerArrayBlock("arr", mutableListOf(4, 3, 2, 1))
        UIContext.pushScope(scope)


        var printBlock = PrintBlock();
        printBlock.updateOutput("x + y y + z, x + z, arr")

        val result = printBlock.run()

        assertEquals(702, result)
        assertEquals("", printBlock.consoleOutput)
    }

    @Test
    fun testArray() {
        val scope = hashMapOf<String, VarBlock<*>>()

        scope["x"] = IntegerBlock("x", 10)
        scope["y"] = IntegerBlock("y", 5)
        scope["z"] = IntegerBlock("z", 1)
        scope["arr"] = IntegerArrayBlock("arr", mutableListOf(4, 3, 2, 1))
        UIContext.pushScope(scope)


        var printBlock = PrintBlock();
        printBlock.updateOutput("\"It`s my array: \", arr")

        val result = printBlock.run()

        assertEquals(0, result)
        assertEquals("It`s my array: 4 3 2 1", printBlock.consoleOutput)
    }

    @Test
    fun testPrintStringOnly() {
        val scope = hashMapOf<String, VarBlock<*>>()
        UIContext.pushScope(scope)

        val printBlock = PrintBlock()
        printBlock.updateOutput("\"Hello, world!\"")

        val result = printBlock.run()

        assertEquals(0, result)
        assertEquals("Hello, world!", printBlock.consoleOutput)
    }

    @Test
    fun testUnclosedQuoteError() {
        val scope = hashMapOf<String, VarBlock<*>>()
        UIContext.pushScope(scope)

        val printBlock = PrintBlock()
        printBlock.updateOutput("\"This is an unclosed string")

        val result = printBlock.run()

        assertEquals(INVALID_FORMAT.id, result)
        assertEquals("", printBlock.consoleOutput)
    }

    @Test
    fun testComplexArithmeticExpression() {
        val scope = hashMapOf<String, VarBlock<*>>()

        scope["a"] = IntegerBlock("a", 5)
        scope["b"] = IntegerBlock("b", 3)
        UIContext.pushScope(scope)

        val printBlock = PrintBlock()
        printBlock.updateOutput("a * (b + 2) - a / b")

        val result = printBlock.run()

        assertEquals(0, result)
        assertEquals("24", printBlock.consoleOutput)
    }

    @Test
    fun testMixedOutputWithVariablesAndStrings() {
        val scope = hashMapOf<String, VarBlock<*>>()

        scope["name"] = StringBlock("name", "Alice")
        scope["age"] = IntegerBlock("age", 25)
        UIContext.pushScope(scope)

        val printBlock = PrintBlock()
        printBlock.updateOutput("\"Name: \", name, \", Age: \", age")

        val result = printBlock.run()

        assertEquals(0, result)
        assertEquals("Name: Alice, Age: 25", printBlock.consoleOutput)
    }

    @Test
    fun testMultipleElementsWithSpaceAndComma() {
        val scope = hashMapOf<String, VarBlock<*>>()

        scope["x"] = IntegerBlock("x", 1)
        scope["y"] = IntegerBlock("y", 2)
        scope["z"] = IntegerBlock("z", 3)
        UIContext.pushScope(scope)

        val printBlock = PrintBlock()
        printBlock.updateOutput("x , y , z")

        val result = printBlock.run()

        assertEquals(0, result)
        assertEquals("123", printBlock.consoleOutput)
    }
    @Test
    fun testStringExpressionWithConcatenation() {
        val scope = hashMapOf<String, VarBlock<*>>()

        scope["x"] = IntegerBlock("x", 7)
        scope["y"] = IntegerBlock("y", 3)
        UIContext.pushScope(scope)

        val printBlock = PrintBlock()
        printBlock.updateOutput("\"Result: \", (x + y)")

        val result = printBlock.run()

        assertEquals(0, result)
        assertEquals("Result: 10", printBlock.consoleOutput)
    }

    @Test
    fun testArrayElementInArithmeticExpression() {
        val scope = hashMapOf<String, VarBlock<*>>()

        scope["arr"] = IntegerArrayBlock("arr", mutableListOf(5, 3, 2))
        UIContext.pushScope(scope)

        val printBlock = PrintBlock()
        printBlock.updateOutput("arr[0] + arr[1], arr[2] * 2")

        val result = printBlock.run()

        assertEquals(0, result)
        assertEquals("84", printBlock.consoleOutput) // 5+3=8; 2*2=4
    }

    @Test
    fun testForFixBug() {
        val scope = hashMapOf<String, VarBlock<*>>()

        scope["arr"] = IntegerArrayBlock("arr", mutableListOf(5, 3, 2))
        UIContext.pushScope(scope)

        val printBlock = PrintBlock()
        printBlock.updateOutput("arr[0] + arr[1], arr[2] * 2")

        val result = printBlock.run()

        assertEquals(0, result)
        assertEquals("84", printBlock.consoleOutput) // 5+3=8; 2*2=4
    }

    @After
    fun tearDown() {
        UIContext.clear()
    }
}