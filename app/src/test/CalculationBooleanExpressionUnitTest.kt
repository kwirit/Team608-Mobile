class CalculationBooleanExpressionUnitTest {
    // Простой мок-контекст (если не используется)
    private val dummyContext = object : Context {}

    @Test
    fun `test simple true expression`() {
        val input = "3 > 2"
        val result = calculationBooleanExpression(input, dummyContext)
        assertEquals(Pair(true, SUCCESS.id), result)
    }

    @Test
    fun `test simple false expression`() {
        val input = "3 < 2"
        val result = calculationBooleanExpression(input, dummyContext)
        assertEquals(Pair(false, SUCCESS.id), result)
    }

    @Test
    fun `test equality check`() {
        val input = "5 == 5"
        val result = calculationBooleanExpression(input, dummyContext)
        assertEquals(Pair(true, SUCCESS.id), result)
    }

    @Test
    fun `test not equal`() {
        val input = "5 != 6"
        val result = calculationBooleanExpression(input, dummyContext)
        assertEquals(Pair(true, SUCCESS.id), result)
    }

    @Test
    fun `test logical and`() {
        val input = "(3 < 4) && (5 > 4)"
        val result = calculationBooleanExpression(input, dummyContext)
        assertEquals(Pair(true, SUCCESS.id), result)
    }

    @Test
    fun `test logical or`() {
        val input = "(3 > 4) || (5 > 4)"
        val result = calculationBooleanExpression(input, dummyContext)
        assertEquals(Pair(true, SUCCESS.id), result)
    }

    @Test
    fun `test nested logical operations`() {
        val input = "(3 < 4 || 5 > 6) && (7 != 8)"
        val result = calculationBooleanExpression(input, dummyContext)
        assertEquals(Pair(true, SUCCESS.id), result)
    }

    @Test
    fun `test empty input`() {
        val input = ""
        val result = calculationBooleanExpression(input, dummyContext)
        assertEquals(Pair(false, EMPTY_ARITHMETIC.id), result)
    }

    @Test
    fun `test invalid characters`() {
        val input = "3 > @ 2"
        val result = calculationBooleanExpression(input, dummyContext)
        assertEquals(Pair(false, INVALID_CHARACTERS.id), result)
    }

    @Test
    fun `test type mismatch between boolean and number`() {
        val input = "true == 0"
        val result = calculationBooleanExpression(input, dummyContext)
        assertEquals(Pair(false, TYPE_MISMATCH.id), result)
    }

    @Test
    fun `test division by zero in expression`() {
        val input = "5 / 0 > 0"
        val result = calculationBooleanExpression(input, dummyContext)
        assertEquals(Pair(false, DIVISION_BY_ZERO.id), result)
    }
}
