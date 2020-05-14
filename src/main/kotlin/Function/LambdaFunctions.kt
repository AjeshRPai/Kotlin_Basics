package Function

/**
*  https://blog.kotlin-academy.com/mastering-kotlin-scoped-and-higher-order-functions-23e2dd34d660
 *
 *  @link
 *  The below code is based on the link below
*  https://medium.com/@elye.project/mastering-kotlin-standard-functions-run-with-let-also-and-apply-9cd334b0ef84
* */

object LambdaFunctions {
    @JvmStatic
    fun main(args: Array<String>) {
        differenceofWithandRun("Ajesh")
        runAndlet("Ajesh")
        alsoandlet()
        differenceBetweenAlsoAndApply()
    }

    private fun differenceofWithandRun(string: String?){
        with(string){
            print("with ="+ this?.length)
        }

        //Better Convention
        string?.run {
            println("run ="+length)
        }
    }


    private fun runAndlet(string: String?){
        string?.let{
            //The T.let allow better naming of the converted
            // used variable i.e. you could convert it to some other name.

            nonNullString ->
            print("let "+{nonNullString.length})
        }

        string?.run {
            println("run $length")
        }
    }

    private fun alsoandlet(){
        val original = "abc"

        // Evolve the value and send to the next chain
        original.let {
            println("The original String is $it") // "abc"
            it.reversed() // evolve it as parameter to send to next let
        }.let {
            println("The reverse String is $it") // "cba"
            it.length  // can be evolve to other type
        }.let {
            println("The length of the String is $it") // 3
        }

        // Wrong
        // Same value is sent in the chain (printed answer is wrong)
        original.also {
            println("The original String is $it") // "abc"
            it.reversed() // even if we evolve it, it is useless
        }.also {
            println("The reverse String is ${it}") // "abc"
            it.length  // even if we evolve it, it is useless
        }.also {
            println("The length of the String is ${it}") // "abc"
        }
    }

    private fun differenceBetweenAlsoAndApply(){
        var original:Individual = Individual("Ajesh",28)

        println("Running apply block")
        // Evolve the value and send to the next chain
        original.apply {
            this.name = "Anand"// evolve it as parameter to send to next let
        }.apply {
            this.name = this.name + " Something else "
            this.age = 29  // can be evolve to other type
        }

        println("Person after apply block $original")

        println("Running also block")

        var original2:Individual = Individual("Sachin",29)


        original2.also {
            it.name = "Ajay" // even if we evolve it, it is useless
        }.also {
            it.name = it.name + " Something else "
            it.age = 23  // can be evolve to other type
        }

        println("Person after also block $original2")

    }

    data class Individual(var name:String,var age:Int)

}