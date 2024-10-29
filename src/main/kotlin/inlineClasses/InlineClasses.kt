package inlineClasses

import org.checkerframework.checker.units.qual.Current


data class Transaction(val currency: String, val amount: String)

@JvmInline
value class Amount(
    val value: String
) {
    // Secondary constructor for amount in cents
    constructor(cents: Int, dollar: Int) : this((cents / 100.0 + dollar).toString()) {
        require(cents >= 0) {
            "Cents value must be non-negative"
        }
    }

    fun roundOff() {

    }

    // Property to convert value to Double if needed
    val amount: Double
        get(
        ) = value.toDoubleOrNull() ?: 0.0

    // Adding a digit to the current amount, handling leading zeroes
    fun addDigit(digit: Char): Amount {
        val newValue = if (value == "0") digit.toString() else value + digit
        return Amount(newValue)
    }

    // Adding a decimal point if it's not already present
    fun addDecimalPoint(): Amount {
        return if ('.' in value) this else Amount("$value.")
    }

    // Handling backspace, removing the last character
    fun updateAmountOnBackspace(): Amount {
        val newValue = if (value.length > 1) value.dropLast(1) else "0"
        return Amount(newValue)
    }

    override fun toString(): String = value
}


@JvmInline
value class Box<T>(val value: T) {
    fun unwrap(): T = value

    override fun toString(): String {
        return "Box(value=$value)"
    }
}

// difference between type alias and inline classes

@JvmInline
value class Currency(val code: String) // Represents currency codes like "USD" or "EUR"


fun main() {
    fun addExpense(amount: Amount, currency: Currency) {
        println("Adding expense of ${amount.value} in currency ${currency.code}")
    }

    val expenseAmount = Amount("100.0")
    val expenseCurrency = Currency("USD")

    addExpense(expenseAmount, expenseCurrency) // Valid
    addExpense(Amount("50.0"), Currency("EUR")) // Valid
    // addExpense("100.0", "USD") // Error: Enforces type safety
}




// invalid cases

//class Expense {
//    fun calculate() {
//        @JvmInline
//        value class Amount(val value: Double) // This is not allowed
//    }
//
//    @JvmInline
//    inner value class Currency(val value: Double) // this is not allowed
//}
//
//@JvmInline
//enum value class ExpenseType(val label: String) { // This is not allowed
//    FOOD("Food"),
//    TRAVEL("Travel"),
//    SHOPPING("Shopping")
//}



// invalid case with type alias
//
//fun incorrectAlias() {
//    typealias Amount = String
//    typealias Currency = String
//
//    fun addExpense(amount: Amount, currency: Currency) {
//        println("Adding expense of $amount in currency $currency")
//    }
//
//    addExpense("100.0", "USD") // Valid
//    addExpense("50.0", "EUR")  // Valid
//    addExpense("USD", "100") // Valid but incorrect parameters
//}


