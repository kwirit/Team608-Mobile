package com.example.scratchinterpretermobile.Model

import com.example.scratchinterpretermobile.Controller.Error.CONTEXT_IS_NULL
import com.example.scratchinterpretermobile.Controller.Error.NO_COMPARISON_OPERATOR_SELECTED
import com.example.scratchinterpretermobile.Controller.Error.RUNTIME_ERROR
import com.example.scratchinterpretermobile.Controller.Error.SUCCESS
import com.example.scratchinterpretermobile.Controller.Utils.calculationArithmeticExpression
import com.example.scratchinterpretermobile.Controller.Utils.calculationBooleanExpression
import javax.xml.xpath.XPathExpression

class LoopBlock(
    override var context: Context
) : InstructionBlock {
    private var booleanExpression: String = ""

    private var resultValue: Boolean = false

    private var script: MutableList<InstructionBlock> = mutableListOf()

    private var scope: HashMap<String, VarBlock<*>> = hashMapOf()

    /**
     * Обрабатывает входные данные условия, проверяет оператор сравнения и выполняет сравнение.
     * Удаляет scope из context по
     * @param leftPartCondition Левая часть условия (например, строка или значение для сравнения).
     * @param rightPartCondition Правая часть условия (например, строка или значение для сравнения).
     * @param operator Оператор сравнения (например, ">", "<", "==", "!=" и т.д.).
     * @param blocksToRun Список блоков инструкций, которые будут выполнены при успешной проверке условия.
     *
     * @return Код результата выполнения:
     *   - [SUCCESS.id] — успешное выполнение.
     *   - [NO_COMPARISON_OPERATOR_SELECTED.id] — не выбран оператор сравнения.
     *   - Код ошибки из метода [compare], если сравнение частей условия завершилось с ошибкой.
     */

    /**
     * Добавление scope в context
     * Обязательная функция при заходе в карточку
     */
    fun addScopeToContext() {
        for (item in context.toList()){
            if (item == scope) {
                return
            }
        }
        context.pushScope(scope)
    }

    fun setScript(script:MutableList<InstructionBlock>) {
        this.script = script
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

        var errorCompare = compare()
        if (errorCompare != SUCCESS.id) return errorCompare


        if (errorCompare != SUCCESS.id) {
            context.popScope()
            return errorCompare
        }

        context.popScope()
        return SUCCESS.id
    }

    /**
     * Выполняет вычисления boolean выражения.
     * @return SUCCES.id в случае успеха, код ошибки — в случае неудачи.
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

        var compareError = compare()

        if (compareError != SUCCESS.id){
            context.popScope();
            return compareError
        }

        while (resultValue) {
            context.pushScope(hashMapOf())
            for (block in script) {
                val contextOfBlock = block.context

                block.context = this.context
                val result = block.run();

                block.context = contextOfBlock
                if (result != SUCCESS.id) {
                    context.popScope();
                    return result
                }
            }
            compareError = compare()
            if (compareError != SUCCESS.id){
                context.popScope();
                return compareError
            }

            context.popScope()
        }

        return SUCCESS.id
    }
}