package com.example.scratchinterpretermobile.Model

import com.example.scratchinterpretermobile.Controller.Error.NO_COMPARISON_OPERATOR_SELECTED
import com.example.scratchinterpretermobile.Controller.Error.SUCCESS
import com.example.scratchinterpretermobile.Controller.calculationArithmeticExpression

class ConditionBlock(
) : InstructionBlock {
    override var context: Context = UIContext

    private var operator: String = "=="
    private var leftPartCondition: String = ""
    private var rightPartCondition: String = ""

    private var resultValue: Boolean = false

    private var thenBlock: MutableList<InstructionBlock> = mutableListOf()
    private var elseBlock: MutableList<InstructionBlock> = mutableListOf()

    private var elseScope: HashMap<String, VarBlock<*>> = hashMapOf()
    private var thenScope: HashMap<String, VarBlock<*>> = hashMapOf()

    private var elseScopeInContext: Boolean = false
    private var thenScopeInContext: Boolean = false

    fun setThenBlock(thenBlocks:MutableList<InstructionBlock>) {
        this.thenBlock = thenBlocks
    }
    fun setElseBlock(elseBlocks: MutableList<InstructionBlock>) {
        this.elseBlock = elseBlocks
    }

    fun addElseScopeInContext() {
        if (thenScopeInContext) {
            context.popScope()
        }
        if (elseScopeInContext) return
        context.pushScope(elseScope)
        elseScopeInContext = true
    }
    fun addThenScopeInContext() {
        if (elseScopeInContext) {
            context.popScope()
        }
        if (thenScopeInContext) return
        context.pushScope(thenScope)
        thenScopeInContext = true
    }

    /**
     * Обрабатывает входные данные условия, проверяет оператор сравнения и выполняет сравнение.
     * Удаляет scope из context по
     * @param leftPartCondition Левая часть условия (например, строка или значение для сравнения).
     * @param rightPartCondition Правая часть условия (например, строка или значение для сравнения).
     * @param operator Оператор сравнения (например, ">", "<", "==", "!=", "<=", ">=").
     * @param thenBlock Список блоков, которые будут выполнены при успешной проверке условия.
     * @param elseBlock Список блоков, которые будут выполнены при успешной проверку условия.
     *
     * @return Код результата выполнения:
     *   - [SUCCESS.id] — успешное выполнение.
     *   - [NO_COMPARISON_OPERATOR_SELECTED.id] — не выбран оператор сравнения.
     *   - Код ошибки из метода [compare], если сравнение частей условия завершилось с ошибкой.
     */
    fun processInput(leftPartCondition: String, rightPartCondition: String, operator: String, thenBlock: MutableList<InstructionBlock>, elseBlock: MutableList<InstructionBlock>): Int {
        this.leftPartCondition = leftPartCondition
        this.rightPartCondition = rightPartCondition
        this.operator = operator
        this.thenBlock = thenBlock
        this.elseBlock = elseBlock

        if (this.operator == "Выбрать оператор") return NO_COMPARISON_OPERATOR_SELECTED.id

        val errorCompare = compare()

        if(errorCompare != SUCCESS.id) {
            context.popScope()
            return errorCompare
        }

        context.popScope()
        return SUCCESS.id
    }

    /**
     * Выполняет сравнение двух арифметических выражений на основе заданного оператора.
     * @return 0 в случае успеха, код ошибки — в случае неудачи.
     * В случае успеха изменяет resultValue.
     */
    private fun compare(): Int {
        val (valueLeftPart, errorLeft) = calculationArithmeticExpression(leftPartCondition)
        if (errorLeft != SUCCESS.id) {
            return errorLeft;
        }

        val (valueRightPart, errorRight) = calculationArithmeticExpression(rightPartCondition)
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

    override fun removeBlock() {
        return
    }

    override fun run(): Int {
        context.pushScope(hashMapOf())
        val compareError = compare()
        if (compareError != 0){
            context.popScope();
            return compareError
        }

        val blocksToRun = if (resultValue) thenBlock else elseBlock

        for (block in blocksToRun) {
            val result = block.run();
            if (result != 0) {
                context.popScope();
                return result
            }
        }
        context.popScope();
        return 0
    }
}