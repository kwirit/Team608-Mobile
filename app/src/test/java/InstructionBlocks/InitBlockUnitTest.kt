

import com.example.scratchinterpretermobile.Model.InitBlock
import com.example.scratchinterpretermobile.Model.UIContext
import org.junit.Test

class InitBlockUnitTest {
    @Test
    fun test1() {
        val scope = UIContext.peekScope()

        val initBlock1 = InitBlock(UIContext)
        initBlock1.assembleIntegerBlock("var")
        initBlock1.run()
        initBlock1.assembleIntegerArrayBlock("ar", "5")
        initBlock1.run()

        val initBlock2 = InitBlock(UIContext)
        initBlock2.assembleIntegerBlock("ar")
        initBlock2.run()
        initBlock2.assembleIntegerBlock("var")
        initBlock2.run()
    }
}