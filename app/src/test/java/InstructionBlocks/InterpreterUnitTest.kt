import com.example.scratchinterpretermobile.Model.AssignmentBlock
import com.example.scratchinterpretermobile.Model.ConditionBlock
import com.example.scratchinterpretermobile.Model.InitBlock
import com.example.scratchinterpretermobile.Model.InstructionBlock
import com.example.scratchinterpretermobile.Model.Interpreter
import com.example.scratchinterpretermobile.Model.LoopBlock
import com.example.scratchinterpretermobile.Model.UIContext
import org.junit.Test

class InterpreterUnitTest {
    @Test
    fun bubbleSort() {
        val scope = UIContext.peekScope()

        val initArrayBlock = InitBlock()
        initArrayBlock.assembleIntegerArrayBlock("ar", "5")
        initArrayBlock.run()

        val fillArray = AssignmentBlock()
        fillArray.assembleIntegerArrayBlock("ar", "5,4,3,2,1")

        val initIndex_i = InitBlock()
        initIndex_i.assembleIntegerBlock("i")
        initIndex_i.run()

        val loopFirst = LoopBlock()
        //------------------------------
            val initIndex_j = InitBlock()
            initIndex_j.assembleIntegerBlock("j")
            initIndex_j.run()

            val loopSecond = LoopBlock()
            //------------------------------
                val conditionBlock = ConditionBlock()
                //------------------------------
                    val initBuffer = InitBlock()
                    initBuffer.assembleIntegerBlock("buffer")
                    initBuffer.run()

                    val assignmentBuffer = AssignmentBlock()
                    assignmentBuffer.assembleIntegerBlock("buffer", "ar[j]")

                    val updateArrayElement_i = AssignmentBlock()
                    updateArrayElement_i.assembleElementIntegerArrayBlock("ar", "j","ar[j+1]")

                    val updateArrayElement_j = AssignmentBlock()
                    updateArrayElement_j.assembleElementIntegerArrayBlock("ar", "j+1", "buffer")

                    val blocsToCondition = mutableListOf<InstructionBlock>()
                    blocsToCondition.add(initBuffer)
                    blocsToCondition.add(assignmentBuffer)
                    blocsToCondition.add(updateArrayElement_i)
                    blocsToCondition.add(updateArrayElement_j)

                    conditionBlock.addThenScopeInContext()
                    conditionBlock.processInput("ar[j]", "ar[j+1]", ">", blocsToCondition, mutableListOf())
                //------------------------------

                val updateIndex_j = AssignmentBlock()
                updateIndex_j.assembleIntegerBlock("j", "j+1")

                val blocksToSecondLoop = mutableListOf<InstructionBlock>()
                blocksToSecondLoop.add(conditionBlock)
                blocksToSecondLoop.add(updateIndex_j)

                loopSecond.addScopeToContext()
                loopSecond.processInput("j", "5-i-1","<", blocksToSecondLoop)
            //------------------------------

            val updateIndex_i = AssignmentBlock()
            updateIndex_i.assembleIntegerBlock("i", "i+1")

        val blocksToFirstLoop = mutableListOf<InstructionBlock>()
        blocksToFirstLoop.add(initIndex_j)
        blocksToFirstLoop.add(loopSecond)
        blocksToFirstLoop.add(updateIndex_i)

        loopFirst.addScopeToContext()
        loopFirst.processInput("i","5-1", "<",blocksToFirstLoop)
        //------------------------------

        val interpreter = Interpreter()

        val blocksToRun = mutableListOf<InstructionBlock>()
        blocksToRun.add(initArrayBlock)
        blocksToRun.add(fillArray)
        blocksToRun.add(initIndex_i)
        blocksToRun.add(loopFirst)

        interpreter.setBlocksToRun(blocksToRun)
        val error = interpreter.run()
    }
    @Test
    fun someVarInList() {

    }
}