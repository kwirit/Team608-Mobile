import com.example.scratchinterpretermobile.Model.AssignmentBlock
import com.example.scratchinterpretermobile.Model.ConditionBlock
import com.example.scratchinterpretermobile.Model.Context
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

        val initArrayBlock = InitBlock(UIContext)
        initArrayBlock.assembleIntegerArrayBlock("ar", "10")
        initArrayBlock.run()

        val fillArray = AssignmentBlock(UIContext)
        fillArray.assembleIntegerArrayBlock("ar", "10,9,8,7,6,5,4,3,2,1")

        val initIndex_i = InitBlock(UIContext)
        initIndex_i.assembleIntegerBlock("i")
        initIndex_i.run()

        val loopFirst = LoopBlock(UIContext)
        //------------------------------
            val initIndex_j = InitBlock(UIContext)
            initIndex_j.assembleIntegerBlock("j")
            initIndex_j.run()

            val loopSecond = LoopBlock(UIContext)
            //------------------------------
                val conditionBlock = ConditionBlock(UIContext)
                //------------------------------
                    val initBuffer = InitBlock(UIContext)
                    initBuffer.assembleIntegerBlock("buffer")
                    initBuffer.run()

                    val assignmentBuffer = AssignmentBlock(UIContext)
                    assignmentBuffer.assembleIntegerBlock("buffer", "ar[j]")

                    val updateArrayElement_i = AssignmentBlock(UIContext)
                    updateArrayElement_i.assembleElementIntegerArrayBlock("ar", "j","ar[j+1]")

                    val updateArrayElement_j = AssignmentBlock(UIContext)
                    updateArrayElement_j.assembleElementIntegerArrayBlock("ar", "j+1", "buffer")

                    val blocsToCondition = mutableListOf<InstructionBlock>()
                    blocsToCondition.add(initBuffer)
                    blocsToCondition.add(assignmentBuffer)
                    blocsToCondition.add(updateArrayElement_i)
                    blocsToCondition.add(updateArrayElement_j)

                    conditionBlock.setTrueScript(blocsToCondition)

                    conditionBlock.addTrueScopeInContext()
                    conditionBlock.assembleBlock("ar[j]", "ar[j+1]", "ar[j+1]")
                //------------------------------

                val updateIndex_j = AssignmentBlock(UIContext)
                updateIndex_j.assembleIntegerBlock("j", "j+1")

                val blocksToSecondLoop = mutableListOf<InstructionBlock>()
                blocksToSecondLoop.add(conditionBlock)
                blocksToSecondLoop.add(updateIndex_j)

                loopSecond.setScript(blocksToSecondLoop)

                loopSecond.addScopeToContext()
                loopSecond.assembleBlock("j", "<","10-i-1")
            //------------------------------

            val updateIndex_i = AssignmentBlock(UIContext)
            updateIndex_i.assembleIntegerBlock("i", "i+1")

        val blocksToFirstLoop = mutableListOf<InstructionBlock>()
        blocksToFirstLoop.add(initIndex_j)
        blocksToFirstLoop.add(loopSecond)
        blocksToFirstLoop.add(updateIndex_i)

        loopFirst.setScript(blocksToFirstLoop)

        loopFirst.addScopeToContext()
        loopFirst.assembleBlock("i","<", "10-1")
        //------------------------------

        val interpreter = Interpreter(Context())

        val blocksToRun = mutableListOf<InstructionBlock>()
        blocksToRun.add(initArrayBlock)
        blocksToRun.add(fillArray)
        blocksToRun.add(initIndex_i)
        blocksToRun.add(loopFirst)

        interpreter.setScript(blocksToRun)
        val error = interpreter.run()
    }
}