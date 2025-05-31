
import com.example.scratchinterpretermobile.Model.AssignmentBlock
import com.example.scratchinterpretermobile.Model.ConditionBlock
import com.example.scratchinterpretermobile.Model.InitBlock
import com.example.scratchinterpretermobile.Model.InstructionBlock
import com.example.scratchinterpretermobile.Model.UIContext
import org.junit.Test

class ConditionBlockUnitTest {
    @Test
    fun test1() {
        val scope = UIContext.peekScope()

        val conditionBlock = ConditionBlock(UIContext)
        val initBlock = InitBlock(UIContext)
        val assignmentBlock = AssignmentBlock(UIContext)
        initBlock.assembleIntegerBlock("var")
        initBlock.run()
        assignmentBlock.assembleIntegerBlock("var", "3")


        // IF
        //--------------------
        val initBlockIF = InitBlock(UIContext)
        val assignmentBlockIF = AssignmentBlock(UIContext)
        initBlockIF.assembleIntegerArrayBlock("ar", "7")
        initBlockIF.run()
        assignmentBlockIF.assembleIntegerArrayBlock("ar", "1,2,3,4,5,6,7")
        initBlockIF.removeBlocksFromContext()

        val ifList = mutableListOf<InstructionBlock>()
        ifList.add(initBlockIF)
        ifList.add(assignmentBlockIF)

        //ELSE
        val initBlockELSE = InitBlock(UIContext)
        val assignmentBlockELSE = AssignmentBlock(UIContext)
        initBlockELSE.assembleIntegerBlock("abc")
        initBlockELSE.run()
        assignmentBlockELSE.assembleIntegerBlock("var", "7")
        initBlockELSE.removeBlocksFromContext()

        val elseList = mutableListOf<InstructionBlock>()
        elseList.add(initBlockELSE)
        elseList.add(assignmentBlockELSE)
        //--------------------

        conditionBlock.setTrueScript(ifList)
        conditionBlock.setFalseScript(elseList)

        conditionBlock.addTrueScopeInContext()
//        conditionBlock.assembleBlock(")
        val error = conditionBlock.run()
    }
}