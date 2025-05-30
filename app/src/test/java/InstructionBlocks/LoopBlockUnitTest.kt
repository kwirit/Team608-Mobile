import com.example.scratchinterpretermobile.Model.AssignmentBlock
import com.example.scratchinterpretermobile.Model.ConditionBlock
import com.example.scratchinterpretermobile.Model.Context
import com.example.scratchinterpretermobile.Model.InitBlock
import com.example.scratchinterpretermobile.Model.InstructionBlock
import com.example.scratchinterpretermobile.Model.IntegerBlock
import com.example.scratchinterpretermobile.Model.Interpreter
import com.example.scratchinterpretermobile.Model.LoopBlock
import com.example.scratchinterpretermobile.Model.UIContext
import com.example.scratchinterpretermobile.Model.VarBlock
import org.junit.Test

class LoopBlockUnitTest {
    @Test
    fun test1() {
//        val scope = HashMap<String, InstructionBlock>()

        val initBlockAr = InitBlock(UIContext)
        initBlockAr.assembleIntegerArrayBlock("ar", "5")
        initBlockAr.run()



        val initBlockCounter = InitBlock(UIContext)
        initBlockCounter.assembleIntegerBlock("count")
        initBlockCounter.run()

        val loopBlock = LoopBlock(UIContext)
            // -------------------------------------
            val assignmentArBlock = AssignmentBlock(UIContext)
            assignmentArBlock.assembleElementIntegerArrayBlock("ar", "count", "count+1")
            val assignmentCountBlock = AssignmentBlock(UIContext)
            assignmentCountBlock.assembleIntegerBlock("count", "count+1")

            val blocksToLoop = mutableListOf<InstructionBlock>()
            blocksToLoop.add(assignmentArBlock)
            blocksToLoop.add(assignmentCountBlock)
            // -------------------------------------
        loopBlock.setScript(blocksToLoop)

//        loopBlock.addScopeToContext()
        loopBlock.assembleBlock("count","<","5")
        val error = loopBlock.run()
    }

    @Test
    fun test2() {
//        val scope = UIContext.peekScope()

        val initArBlock = InitBlock(UIContext)
        initArBlock.assembleIntegerArrayBlock("ar", "5")
        initArBlock.run()

        val assignmentArBlock = AssignmentBlock(UIContext)
        assignmentArBlock.assembleIntegerArrayBlock("ar", "1,3,2,5,4")

        val initIndexBlock = InitBlock(UIContext)
        initIndexBlock.assembleIntegerBlock("i")
        initIndexBlock.run()

        val loopBlock = LoopBlock(UIContext)
        // -------------------------------------
            val conditionBlock = ConditionBlock(UIContext)
            // -------------------------------------
                val initTempBlock = InitBlock(UIContext)
                initTempBlock.assembleIntegerBlock("temp")
                initTempBlock.run()

                val assignmentTempBlock = AssignmentBlock(UIContext)
                assignmentTempBlock.assembleIntegerBlock("temp","ar[i]")

                val assignmentElementArray = AssignmentBlock(UIContext)
                assignmentElementArray.assembleElementIntegerArrayBlock("ar", "i", "ar[i+1]")

                val updateArrayBlock = AssignmentBlock(UIContext)
                updateArrayBlock.assembleElementIntegerArrayBlock("ar","i+1","temp")

                val blocksToCondition = mutableListOf<InstructionBlock>()
                blocksToCondition.add(initTempBlock)
                blocksToCondition.add(assignmentTempBlock)
                blocksToCondition.add(assignmentElementArray)
                blocksToCondition.add(updateArrayBlock)

                conditionBlock.setTrueScript(blocksToCondition)

                conditionBlock.addTrueScopeInContext()
                conditionBlock.assembleBlock("ar[i]",">","ar[i+1]")
            // -------------------------------------

            val assignmentIndexBlock = AssignmentBlock(UIContext)
            assignmentIndexBlock.assembleIntegerBlock("i", "i+1")

            val blocksToLoop = mutableListOf<InstructionBlock>()
            blocksToLoop.add(conditionBlock)
            blocksToLoop.add(assignmentIndexBlock)
        // -------------------------------------

        loopBlock.setScript(blocksToLoop)

//        loopBlock.addScopeToContext()
        loopBlock.assembleBlock("i+1","<","5")
//        loopBlock.run()

        val interpreter = Interpreter(Context())
        val blockToInterpreter = mutableListOf<InstructionBlock>()
        blockToInterpreter.add(initArBlock)
        blockToInterpreter.add(assignmentArBlock)
        blockToInterpreter.add(initIndexBlock)
        blockToInterpreter.add(loopBlock)

        interpreter.setScript(blockToInterpreter)
        val error = interpreter.run()
    }

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
                    conditionBlock.assembleBlock("ar[j]", ">", "ar[j+1]")
                //------------------------------

                val updateIndex_j = AssignmentBlock(UIContext)
                updateIndex_j.assembleIntegerBlock("j", "j+1")

                val blocksToSecondLoop = mutableListOf<InstructionBlock>()
                blocksToSecondLoop.add(conditionBlock)
                blocksToSecondLoop.add(updateIndex_j)

                loopSecond.setScript(blocksToSecondLoop)

//                loopSecond.addScopeToContext()
                loopSecond.assembleBlock("j", "<","10 - i - 1")
            //------------------------------

            val updateIndex_i = AssignmentBlock(UIContext)
            updateIndex_i.assembleIntegerBlock("i", "i + 1")

            val blocksToFirstLoop = mutableListOf<InstructionBlock>()
            blocksToFirstLoop.add(initIndex_j)
            blocksToFirstLoop.add(loopSecond)
            blocksToFirstLoop.add(updateIndex_i)

            loopFirst.setScript(blocksToFirstLoop)

//            loopFirst.addScopeToContext()
            loopFirst.assembleBlock("i","<", "10 - 1")
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

    @Test
    fun testAssignmentInLoop() {
        val context = UIContext

        val init_i = InitBlock(UIContext)
        init_i.assembleIntegerBlock("i")
        init_i.run()

        val loop = LoopBlock(UIContext)
//        loop.addScopeToContext()
        //----------------------------
            val list = UIContext.getListVarBlock()

            val assignmentBlock = AssignmentBlock(UIContext)
            assignmentBlock.assembleIntegerBlock("i", "i+1")

            val init_j = InitBlock(UIContext)
            init_j.assembleIntegerBlock("j")
            init_j.run()

            assignmentBlock.assembleIntegerBlock("j", "j+1")

            val script = mutableListOf<InstructionBlock>()
            script.add(assignmentBlock)
            script.add(init_j)

            loop.setScript(script)
            loop.assembleBlock("i","<","5")
        //----------------------------

        val interpreter = Interpreter(Context())
        interpreter.run()
    }

    @Test
    fun contextTest() {
        val scope = UIContext.peekScope()
        scope!!.put("1", IntegerBlock("i", 1))
        scope!!.put("1", IntegerBlock("i", 1))
        scope!!.put("1", IntegerBlock("i", 1))
        scope!!.put("2", IntegerBlock("i", 1))
        scope!!.put("3", IntegerBlock("i", 1))
        scope!!.put("4", IntegerBlock("i", 1))
    }
}