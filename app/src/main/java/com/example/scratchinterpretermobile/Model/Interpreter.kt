package com.example.scratchinterpretermobile.Model

class Interpreter(
    blocksToRun: MutableList<InstructionBlock> = mutableListOf()
) {
    var blocksToRun: MutableList<InstructionBlock> = mutableListOf()
    // хранит скрипт где каждый элемент скрипта - блок-инструкция

    fun add(index: Int, block: InstructionBlock) {
        blocksToRun.add(index, block);
    }
    fun remove(block: InstructionBlock) {
        blocksToRun.remove(block)
    }
    fun removeByIndex(index: Int) {
        blocksToRun.removeAt(index)
    }

    fun run(): Int {
        for (block in blocksToRun) {
            val result = block.run();
            if (result != 0) {
                return result
            }
        }
        return 0
    }
}