import java.math.BigDecimal
import java.math.RoundingMode
import java.math.MathContext

// A simple experiment to see the difference between bigdecimal and double

class InvestmentCalculator(
    private val lumpsum: BigDecimal,
    private val lumpsumReturnRate: BigDecimal, // Annual return rate for the lump sum
    private val monthlySip: BigDecimal? = null,
    private val annualSipStepUp: BigDecimal = BigDecimal.ZERO,
    private val sipReturnRate: BigDecimal = BigDecimal("0.15")
) {

    // Method to calculate the future value and year-by-year returns
    fun calculateFutureValueWithYearlyReturns(years: Int): List<Pair<Int, BigDecimal>> {
        var corpus = lumpsum
        val lumpsumMonthlyRate = lumpsumReturnRate.divide(BigDecimal(12), MathContext.DECIMAL128)
        val sipMonthlyRate = sipReturnRate.divide(BigDecimal(12), MathContext.DECIMAL128)
        var currentSip = monthlySip ?: BigDecimal.ZERO
        val yearlyValues = mutableListOf<Pair<Int, BigDecimal>>()

        for (year in 1..years) {
            // Calculate the future value of the existing corpus (lump sum)
            corpus = corpus.multiply(BigDecimal.ONE.add(lumpsumMonthlyRate).pow(12, MathContext.DECIMAL128))

            // If SIP is provided, calculate its contribution
            if (monthlySip != null) {
                val sipFutureValue = currentSip.multiply(
                    BigDecimal.ONE.add(sipMonthlyRate).pow(12, MathContext.DECIMAL128).subtract(BigDecimal.ONE)
                ).divide(sipMonthlyRate, MathContext.DECIMAL128).multiply(BigDecimal.ONE.add(sipMonthlyRate))
                corpus = corpus.add(sipFutureValue)

                // Apply the annual step-up to the SIP
                currentSip = currentSip.multiply(BigDecimal.ONE.add(annualSipStepUp))
            }

            // Store the year and corpus value for that year
            yearlyValues.add(Pair(year, corpus.setScale(2, RoundingMode.HALF_EVEN)))
        }

        return yearlyValues
    }
}

class InvestmentCalculatorUsingDouble(
    private val lumpsum: Double,
    private val lumpsumReturnRate: Double, // Annual return rate for the lump sum
    private val monthlySip: Double? = null,
    private val annualSipStepUp: Double = 0.0,
    private val sipReturnRate: Double = 0.15
) {

    // Method to calculate the future value and year-by-year returns
    fun calculateFutureValueWithYearlyReturns(years: Int): List<Pair<Int, Double>> {
        var corpus = lumpsum
        val lumpsumMonthlyRate = lumpsumReturnRate / 12
        val sipMonthlyRate = sipReturnRate / 12
        var currentSip = monthlySip ?: 0.0
        val yearlyValues = mutableListOf<Pair<Int, Double>>()

        for (year in 1..years) {
            // Calculate the future value of the existing corpus (lump sum)
            corpus *= Math.pow(1 + lumpsumMonthlyRate, 12.0)

            // If SIP is provided, calculate its contribution
            if (monthlySip != null) {
                val sipFutureValue = currentSip * (Math.pow(1 + sipMonthlyRate, 12.0) - 1) / sipMonthlyRate * (1 + sipMonthlyRate)
                corpus += sipFutureValue

                // Apply the annual step-up to the SIP
                currentSip *= (1 + annualSipStepUp)
            }

            // Store the year and corpus value for that year
            yearlyValues.add(Pair(year, corpus))
        }

        return yearlyValues
    }
}

fun main() {
    // Example usage
    val calculator = InvestmentCalculator(
        lumpsum = BigDecimal("1000000000"),          // 100 crore
        lumpsumReturnRate = BigDecimal("0.15"),    // 15% annual return for lump sum
        monthlySip = BigDecimal("50000"),          // 50,000 per month
        annualSipStepUp = BigDecimal("0.10"),      // 10% annual step-up
        sipReturnRate = BigDecimal("0.15")         // 15% annual return for SIP
    )

    val years = 20  // Calculate for 20 years
    val yearlyReturns = calculator.calculateFutureValueWithYearlyReturns(years)

    // Print the year-by-year values
    println("Yearly return values: using bigdecimal")
    for ((year, value) in yearlyReturns) {
        println("Year $year: ₹${value}")
    }


    val calculator2 = InvestmentCalculatorUsingDouble(
        lumpsum = 1000000000.0,          // 100 crore
        lumpsumReturnRate = 0.15,      // 12% annual return for lump sum
        monthlySip = 50000.0,          // 50,000 per month
        annualSipStepUp = 0.10,        // 10% annual step-up
        sipReturnRate = 0.15           // 15% annual return for SIP
    )

    // make sure the values are same in double and bigdecimal

    val yearlyReturns2 = calculator2.calculateFutureValueWithYearlyReturns(years)

    // Print the year-by-year values
    println("Yearly return values: using double")
    for ((year, value) in yearlyReturns2) {
        println("Year $year: ₹${"%.6f".format(value)}")
    }
}
