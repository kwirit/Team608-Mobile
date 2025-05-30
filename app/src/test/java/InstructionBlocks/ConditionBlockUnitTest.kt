package InstructionBlocks

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

        val conditionBlock = ConditionBlock()
        val initBlock = InitBlock()
        val assignmentBlock = AssignmentBlock()
        initBlock.assembleIntegerBlock("var")
        initBlock.run()
        assignmentBlock.assembleIntegerBlock("var", "3")


        // IF
        //--------------------
        val initBlockIF = InitBlock()
        val assignmentBlockIF = AssignmentBlock()
        initBlockIF.assembleIntegerArrayBlock("ar", "7")
        initBlockIF.run()
        assignmentBlockIF.assembleIntegerArrayBlock("ar", "1,2,3,4,5,6,7")
        initBlockIF.removeBlocksFromContext()

        val ifList = mutableListOf<InstructionBlock>()
        ifList.add(initBlockIF)
        ifList.add(assignmentBlockIF)

        //ELSE
        val initBlockELSE = InitBlock()
        val assignmentBlockELSE = AssignmentBlock()
        initBlockELSE.assembleIntegerBlock("abc")
        initBlockELSE.run()
        assignmentBlockELSE.assembleIntegerBlock("var", "7")
        initBlockELSE.removeBlocksFromContext()

        val elseList = mutableListOf<InstructionBlock>()
        elseList.add(initBlockELSE)
        elseList.add(assignmentBlockELSE)
        //--------------------

        conditionBlock.addThenScopeInContext()
        conditionBlock.processInput("1","0","<",ifList, elseList)
        val error = conditionBlock.run()
    }
}