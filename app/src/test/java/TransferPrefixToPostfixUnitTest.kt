import org.junit.Test
import org.junit.After
import org.junit.Assert.*
import com.example.scratchinterpretermobile.Controller.Utils.transferArithmeticPrefixToPostfix
import com.example.scratchinterpretermobile.Model.IntegerArrayBlock
import com.example.scratchinterpretermobile.Model.IntegerBlock
import com.example.scratchinterpretermobile.Model.VarBlock
import com.example.scratchinterpretermobile.Model.UIContext

class TransferPrefixToPostfixUnitTest {
    @Test
    fun test() {
        val scope = hashMapOf<String, VarBlock<*>>()
        scope["i"] = IntegerBlock("i", 2)

        scope["arr"] = IntegerArrayBlock("arr", mutableListOf(10, 20, 30, 40))

        UIContext.pushScope(scope)

        val (result, error) = transferArithmeticPrefixToPostfix(mutableListOf("arr[i+1]"), UIContext)
        assertEquals(0, error)
        assertEquals(mutableListOf("40"), result)
    }

    @After
    fun tearDown() {
        UIContext.clear()
    }

}
