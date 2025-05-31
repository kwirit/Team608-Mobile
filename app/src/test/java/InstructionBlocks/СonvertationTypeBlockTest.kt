
import com.example.scratchinterpretermobile.Model.AssignmentBlock
import com.example.scratchinterpretermobile.Model.Context
import com.example.scratchinterpretermobile.Model.InitBlock
import com.example.scratchinterpretermobile.Model.InstructionBlock
import com.example.scratchinterpretermobile.Model.Interpreter
import com.example.scratchinterpretermobile.Model.UIContext
import com.example.scratchinterpretermobile.Model.СonvertationTypeBlock
import org.junit.Test

class СonvertationTypeBlockTest {
    @Test
    fun testConvertIntToString() {
        val scope = UIContext.peekScope()

        val initInt = InitBlock(UIContext)
        initInt.assembleIntegerBlock("int")
        initInt.run()

        val assignmentInt = AssignmentBlock(UIContext)
        assignmentInt.assembleIntegerBlock("int", "9")

        val initStr = InitBlock(UIContext)
        initStr.assembleStringBlock("str")
        initStr.run()

        val assignmentStr = AssignmentBlock(UIContext)
        assignmentStr.assembleStringBlock("str", """8""")

        val converterBlock = СonvertationTypeBlock(UIContext)
        converterBlock.assembleBlock("String", "str","Int","int")

        val script = mutableListOf<InstructionBlock>()
        script.add(initInt)
        script.add(assignmentInt)
        script.add(initStr)
        script.add(assignmentStr)
        script.add(converterBlock)

        val interpreter = Interpreter(Context())
        interpreter.setScript(script)
        val error = interpreter.run()
    }
}