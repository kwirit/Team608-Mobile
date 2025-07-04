package com.example.scratchinterpretermobile.Model

import com.example.scratchinterpretermobile.Controller.Error.CONTEXT_IS_NULL
import com.example.scratchinterpretermobile.Controller.Error.SUCCESS

class Interpreter(
    private var context:Context
) {
    private var script:MutableList<InstructionBlock> = mutableListOf()

    fun getContext(): Context {
        return context
    }

    fun setContext(newContext: Context) {
        context = newContext
    }

    fun setScript(script: MutableList<InstructionBlock>) {
        this.script = script
    }

    fun run(): Int {
        context ?: return CONTEXT_IS_NULL.id

        for (block in script) {
            val contextOfBlock = block.context
            block.context = this.context

            val result = block.run();
            block.context = contextOfBlock

            if (result != SUCCESS.id) return result
        }
        return SUCCESS.id
    }
}