package TypeAlias


typealias inner=InnerModel


object TypeAliasExample {

    var variable=inner()

    @JvmStatic
    fun main(args: Array<String>) {
        println(variable.innerVariable)
    }
}

