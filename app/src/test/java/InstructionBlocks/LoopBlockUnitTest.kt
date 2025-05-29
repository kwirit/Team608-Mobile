import com.example.scratchinterpretermobile.Model.AssignmentBlock
import com.example.scratchinterpretermobile.Model.ConditionBlock
import com.example.scratchinterpretermobile.Model.Context
import com.example.scratchinterpretermobile.Model.InitBlock
import com.example.scratchinterpretermobile.Model.InstructionBlock
import com.example.scratchinterpretermobile.Model.Interpreter
import com.example.scratchinterpretermobile.Model.LoopBlock
import com.example.scratchinterpretermobile.Model.UIContext
import org.junit.Test

class LoopBlockUnitTest {
    @Test
    fun test1() {
        val scope = HashMap<String, InstructionBlock>()

        val initBlockAr = InitBlock()
        initBlockAr.assembleIntegerArrayBlock("ar", "5")
        initBlockAr.run()



        val initBlockCounter = InitBlock()
        initBlockCounter.assembleIntegerBlock("count")
        initBlockCounter.run()

        val loopBlock = LoopBlock()
            // -------------------------------------
            val assignmentArBlock = AssignmentBlock()
            assignmentArBlock.assembleElementIntegerArrayBlock("ar", "count", "count+1")
            val assignmentCountBlock = AssignmentBlock()
            assignmentCountBlock.assembleIntegerBlock("count", "count+1")

            val blocksToLoop = mutableListOf<InstructionBlock>()
            blocksToLoop.add(assignmentArBlock)
            blocksToLoop.add(assignmentCountBlock)
            // -------------------------------------
        loopBlock.addScopeToContext()
        loopBlock.processInput("count","5","<",blocksToLoop)
        val error = loopBlock.run()
    }

    @Test
    fun test2() {
        val scope = UIContext.peekScope()

        val initArBlock = InitBlock()
        initArBlock.assembleIntegerArrayBlock("ar", "5")
        initArBlock.run()

        val assignmentArBlock = AssignmentBlock()
        assignmentArBlock.assembleIntegerArrayBlock("ar", "1,3,2,5,4")

        val initIndexBlock = InitBlock()
        initIndexBlock.assembleIntegerBlock("i")
        initIndexBlock.run()

        val loopBlock = LoopBlock()
        // -------------------------------------
            val conditionBlock = ConditionBlock()
            // -------------------------------------
                val initTempBlock = InitBlock()
                initTempBlock.assembleIntegerBlock("temp")
                initTempBlock.run()

                val assignmentTempBlock = AssignmentBlock()
                assignmentTempBlock.assembleIntegerBlock("temp","ar[i]")

                val assignmentElementArray = AssignmentBlock()
                assignmentElementArray.assembleElementIntegerArrayBlock("ar", "i", "ar[i+1]")

                val updateArrayBlock = AssignmentBlock()
                updateArrayBlock.assembleElementIntegerArrayBlock("ar","i+1","temp")

                val blocksToCondition = mutableListOf<InstructionBlock>()
                blocksToCondition.add(initTempBlock)
                blocksToCondition.add(assignmentTempBlock)
                blocksToCondition.add(assignmentElementArray)
                blocksToCondition.add(updateArrayBlock)

                conditionBlock.addThenScopeInContext()
                conditionBlock.processInput("ar[i]","ar[i+1]",">",blocksToCondition, mutableListOf())
            // -------------------------------------

            val assignmentIndexBlock = AssignmentBlock()
            assignmentIndexBlock.assembleIntegerBlock("i", "i+1")

            val blocksToLoop = mutableListOf<InstructionBlock>()
            blocksToLoop.add(conditionBlock)
            blocksToLoop.add(assignmentIndexBlock)
        // -------------------------------------

        loopBlock.addScopeToContext()
        loopBlock.processInput("i+1","5","<",blocksToLoop)
//        loopBlock.run()

        val interpreter = Interpreter()
        val blockToInterpreter = mutableListOf<InstructionBlock>()
        blockToInterpreter.add(initArBlock)
        blockToInterpreter.add(assignmentArBlock)
        blockToInterpreter.add(initIndexBlock)
        blockToInterpreter.add(loopBlock)
        interpreter.setBlocksToRun(blockToInterpreter)

        val error = interpreter.run()
    }
}