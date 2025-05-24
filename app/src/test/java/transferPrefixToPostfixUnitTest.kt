import org.junit.Test
import org.junit.After
import org.junit.Assert.*
import com.example.scratchinterpretermobile.Controller.transferPrefixToPostfix
import com.example.scratchinterpretermobile.Controller.validateArrayName
import com.example.scratchinterpretermobile.Model.Context
import com.example.scratchinterpretermobile.Model.IntegerArrayBlock
import com.example.scratchinterpretermobile.Model.IntegerBlock
import com.example.scratchinterpretermobile.Model.VarBlock
import com.example.scratchinterpretermobile.Model.mainContext

class transferPrefixToPostfixUnitTest {
    @Test
    fun test() {
        val scope = hashMapOf<String, VarBlock<*>>()
        scope["i"] = IntegerBlock("i", 2)

        scope["arr"] = IntegerArrayBlock("arr", mutableListOf(10, 20, 30, 40))

        mainContext.pushScope(scope)

        val (result, error) = transferPrefixToPostfix(mutableListOf("arr[i+1]"))
        assertEquals(0, error)
        assertEquals(mutableListOf("40"), result)
    }

    @After
    fun tearDown() {
        mainContext.clear()
    }

}
