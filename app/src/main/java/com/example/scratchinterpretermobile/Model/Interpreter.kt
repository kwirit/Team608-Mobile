package com.example.scratchinterpretermobile.Model

import com.example.scratchinterpretermobile.Controller.Error.SUCCESS

class Interpreter() {
    private var context = Context()

    fun getContext(): Context {
        return context
    }

    fun setContext(newContext: Context) {
        context = newContext
    }

    fun run(blocksToRun: MutableList<InstructionBlock> = mutableListOf()): Int {

        for (block in blocksToRun) {
            val contextOfBlock = block.context
            block.context = this.context

            val result = block.run();
            block.context = contextOfBlock

            if (result != SUCCESS.id) {
                return result
            }
        }
        return SUCCESS.id
    }
}