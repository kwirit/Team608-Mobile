package com.example.scratchinterpretermobile.Model

import com.example.scratchinterpretermobile.Controller.Error.CONTEXT_IS_NULL
import com.example.scratchinterpretermobile.Controller.Error.NO_COMPARISON_OPERATOR_SELECTED
import com.example.scratchinterpretermobile.Controller.Error.RUNTIME_ERROR
import com.example.scratchinterpretermobile.Controller.Error.SUCCESS
import com.example.scratchinterpretermobile.Controller.Utils.calculationArithmeticExpression
import com.example.scratchinterpretermobile.Controller.Utils.calculationBooleanExpression

class ConditionBlock(
    override var context: Context
) : InstructionBlock {
    private var booleanExpression: String = ""

    private var resultValue: Boolean = false

    private var trueScript: MutableList<InstructionBlock> = mutableListOf()
    private var falseScript: MutableList<InstructionBlock> = mutableListOf()

    private var trueScope: HashMap<String, VarBlock<*>> = hashMapOf()
    private var falseScope: HashMap<String, VarBlock<*>> = hashMapOf()

    private var trueScopeInContext: Boolean = false
    private var falseScopeInContext: Boolean = false

    fun setTrueScript(script:MutableList<InstructionBlock>) {
        this.trueScript = script
    }
    fun setFalseScript(script: MutableList<InstructionBlock>) {
        this.falseScript = script
    }

    /**
     * Добавление thenScope в context
     * Обязательная функция при заходе в карточку
     */
    fun addTrueScopeInContext() {
        if (falseScopeInContext) {
            context.popScope()
        }
        if (trueScopeInContext) return
        context.pushScope(trueScope)
        trueScopeInContext = true
    }

    /**
     * Добавление falseScope в context
     * Обязательная функция при заходе в карточку
     */
    fun addFalseScopeInContext() {
        if (trueScopeInContext) {
            context.popScope()
        }
        if (falseScopeInContext) return
        context.pushScope(falseScope)
        falseScopeInContext = true
    }

    /**
     * Обрабатывает входные данные условия, проверяет оператор сравнения и выполняет сравнение.
     * Удаляет scope из context по
     * @param booleanExpression boolean выражение.
     * @return Код результата выполнения:
     *   - [SUCCESS.id] — успешное выполнение.
     *   - Код ошибки из метода [compare], если сравнение частей условия завершилось с ошибкой.
     */
    fun assembleBlock(booleanExpression: String): Int {
        this.booleanExpression = booleanExpression

        val errorCompare = compare()

        if(errorCompare != SUCCESS.id) return errorCompare

        return SUCCESS.id
    }

    /**
     * Выполняет вычисления boolean выражения
     * @return 0 в случае успеха, код ошибки — в случае неудачи.
     * В случае успеха изменяет resultValue.
     */
    private fun compare(): Int {
        val (booleanValue, booleanError) = calculationBooleanExpression(booleanExpression, context)
        if (booleanError != 0) return booleanError
        this.resultValue = booleanValue
        return SUCCESS.id
    }

    override fun removeBlock() {}

    override fun run(): Int {
        context ?: return CONTEXT_IS_NULL.id

        context.pushScope(hashMapOf())
        val compareError = compare()
        if (compareError != 0){
            context.popScope();
            return compareError
        }

        val blocksToRun = if (resultValue) trueScript else falseScript

        for (block in blocksToRun) {
            val contextOFBlock = block.context

            block.context = this.context
            val result = block.run();

            block.context = contextOFBlock
            if (result != 0) {
                context.popScope();
                return result
            }
        }
        context.popScope();

        return SUCCESS.id
    }
}