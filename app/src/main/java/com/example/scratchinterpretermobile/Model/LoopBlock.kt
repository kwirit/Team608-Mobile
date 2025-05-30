package com.example.scratchinterpretermobile.Model

import com.example.scratchinterpretermobile.Controller.Error.NO_COMPARISON_OPERATOR_SELECTED
import com.example.scratchinterpretermobile.Controller.Error.SUCCESS
import com.example.scratchinterpretermobile.Controller.Utils.calculationArithmeticExpression

class LoopBlock(
    override var context: Context
) : InstructionBlock {
//    override var context: Context = UIContext
    override var runResult: Int = SUCCESS.id

    private var leftPartCondition: String = ""
    private var rightPartCondition: String = ""
    private var operator: String = "=="

    private var resultValue: Boolean = false

    private var script: MutableList<InstructionBlock> = mutableListOf()

    private var scope: HashMap<String, VarBlock<*>> = hashMapOf()


    /**
     * Добавление scope в context
     * Обязательная функция при заходе в карточку
     */
    fun addScopeToContext() {
        context.pushScope(scope)
    }

    /**
     * Добавление scope в context
     * Обязательная функция при выходе из карточку
     */
    fun removeScopeToContext() {
        context.popScope()
    }

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

    fun setScript(script:MutableList<InstructionBlock>) {
        this.script = script
    }

    fun assembleBlock(leftPartCondition: String, operator: String, rightPartCondition: String): Int {
        this.leftPartCondition = leftPartCondition
        this.rightPartCondition = rightPartCondition
        this.operator = operator

        if (this.operator == "Выбрать оператор") return NO_COMPARISON_OPERATOR_SELECTED.id

        var errorCompare = compare()

        if (errorCompare != SUCCESS.id) {
//            context.popScope()
            return errorCompare
        }

//        context.popScope()
        return SUCCESS.id
    }

    /**
     * Выполняет сравнение двух арифметических выражений на основе заданного оператора.
     * @return SUCCES.id в случае успеха, код ошибки — в случае неудачи.
     * В случае успеха изменяет resultValue.
     */
    private fun compare(): Int {
        val (valueLeftPart, errorLeft) = calculationArithmeticExpression(leftPartCondition, context)
        if (errorLeft != SUCCESS.id) {
            return errorLeft;
        }

        val (valueRightPart, errorRight) = calculationArithmeticExpression(rightPartCondition, context)
        if (errorRight != SUCCESS.id) {
            return errorRight
        }

        resultValue = when (operator) {
            "==" -> valueLeftPart == valueRightPart
            "<=" -> valueLeftPart <= valueRightPart
            "<" -> valueLeftPart < valueRightPart
            ">=" -> valueLeftPart >= valueRightPart
            ">" -> valueLeftPart > valueRightPart
            "!=" -> valueLeftPart != valueRightPart
            else -> false
        }

        return SUCCESS.id
    }

    override fun removeBlock() {}

    override fun run(): Int {
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