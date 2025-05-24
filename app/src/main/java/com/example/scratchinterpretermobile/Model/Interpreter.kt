package com.example.scratchinterpretermobile.Model

class Interpreter() {
    fun run(blocksToRun: MutableList<InstructionBlock> = mutableListOf()): Int {
        interpreterContext.clear()

        for (block in blocksToRun) {
            block.setContext(interpreterContext)
            val result = block.run();
            if (result != 0) {
                return result
            }
        }
        return 0
    }
}