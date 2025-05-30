
import com.example.scratchinterpretermobile.Model.AssignmentBlock
import com.example.scratchinterpretermobile.Model.ConditionBlock
import com.example.scratchinterpretermobile.Model.Context
import com.example.scratchinterpretermobile.Model.InitBlock
import com.example.scratchinterpretermobile.Model.InstructionBlock
import com.example.scratchinterpretermobile.Model.Interpreter
import com.example.scratchinterpretermobile.Model.UIContext
import com.example.scratchinterpretermobile.Model.VarBlock
import org.junit.Assert.assertEquals
import org.junit.Test

class AssignmentBlockUnitTest {
    @Test
    fun test1() {
        val scope = UIContext.peekScope()

        val initBlock = InitBlock(UIContext)
        initBlock.assembleIntegerArrayBlock("ar", "7")
        initBlock.run()

        val assignmentBlock = AssignmentBlock(UIContext)
        assignmentBlock.assembleIntegerArrayBlock("ar", "1,2,3,4,5,6,7")
        assignmentBlock.assembleElementIntegerArrayBlock("ar", "3", "3")
        assignmentBlock.run()

    }

    @Test
    fun test2() {
        val scope = UIContext.peekScope()

        val initBlock = InitBlock(UIContext)
        initBlock.assembleIntegerBlock("var")
        initBlock.run()

        val assignmentBlock = AssignmentBlock(UIContext)
        assignmentBlock.assembleIntegerBlock("var", "3")
        assignmentBlock.run()

        assignmentBlock.assembleIntegerBlock("var", "7")
        assignmentBlock.run()

        //
        assignmentBlock.assembleIntegerArrayBlock("ar", "1,2,3,4,5")
        assignmentBlock.run()

        //
        initBlock.assembleIntegerArrayBlock("ar", "5")
        initBlock.run()

        assignmentBlock.assembleIntegerArrayBlock("ar", "1,2,3,4,5")
        assignmentBlock.run()

        assignmentBlock.assembleElementIntegerArrayBlock("ar", "3", "-3")
        assignmentBlock.run()

    }

    @Test
    fun assignmentBlockToBlockInCondition() {
        val scope = UIContext.peekScope()

        val init_j = InitBlock(UIContext)
        init_j.assembleIntegerBlock("j")
        init_j.run()

        val conditionBlock = ConditionBlock(UIContext)
        //--------------------
            val init_i = InitBlock(UIContext)
            init_i.assembleIntegerBlock("i")
            init_i.run()

            val assignment_i = AssignmentBlock(UIContext)
            assignment_i.assembleIntegerBlock("i", "7")

            val scriptCondition = mutableListOf<InstructionBlock>()
            scriptCondition.add(init_i)
            scriptCondition.add(assignment_i)

            conditionBlock.setTrueScript(scriptCondition)
            conditionBlock.assembleBlock("0", "<", "1")
        //--------------------

        conditionBlock.removeBlock()

        val assignment_j = AssignmentBlock(UIContext)
        assignment_j.assembleIntegerBlock("j", "i")

        val script = mutableListOf<InstructionBlock>()
        script.add(init_j)
        script.add(assignment_j)

        val interpreter = Interpreter(Context())
        interpreter.setScript(script)

        val error = interpreter.run()
    }

    @Test
    fun assignmentElementArrayBlockFromUI() {
        val scope = UIContext.peekScope()

        val init = InitBlock(UIContext)
        init.assembleIntegerArrayBlock("ar", "5")
        init.run()

        val assignmentBlock = AssignmentBlock(UIContext)
        assignmentBlock.assembleElementIntegerArrayBlock("ar", "3", "3")
        assignmentBlock.run()

        assignmentBlock.assembleElementIntegerArrayBlock("ar", "4", "4")
        assignmentBlock.run()

        assignmentBlock.assembleElementIntegerArrayBlock("ar", "5", "5")
        assignmentBlock.run()

        assignmentBlock.assembleElementIntegerArrayBlock("ar", "3", "g")
        assignmentBlock.run()

        assignmentBlock.assembleElementIntegerArrayBlock("ar", "2", "2")
        assignmentBlock.run()
    }

    @Test
    fun assignmentElementArrayBlockFromUI2(){
        val scope = UIContext.peekScope()

        val init = InitBlock(UIContext)
        init.assembleIntegerArrayBlock("ar", "5")
        init.run()

        val assignmentBlock = AssignmentBlock(UIContext)
        assignmentBlock.assembleElementIntegerArrayBlock("ar", "2","2")
        assignmentBlock.run()

        assignmentBlock.assembleElementIntegerArrayBlock("ar", "3", "3")
        assignmentBlock.run()

        val arrayBlock = UIContext.getVar("ar") as? VarBlock<MutableList<Int>>
        if(arrayBlock != null) {
            val arrayValueString:String = arrayBlock!!.getValue().joinToString(separator = ",")
            assignmentBlock.assembleIntegerArrayBlock(arrayBlock.getName(), arrayValueString)
//            assignmentBlock.run()
        }


//        assignmentBlock.assembleIntegerArrayBlock("ar", "0,0,2,3,0,5")
//        assignmentBlock.run()

        val script = mutableListOf<InstructionBlock>()
        script.add(init)
        script.add(assignmentBlock)

        val interpreter = Interpreter(Context())
        interpreter.setScript(script)

        val error = interpreter.run()
    }
}