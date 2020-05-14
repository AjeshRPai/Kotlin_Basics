package InlineFunctionType


/***
 * https://android.jlelse.eu/inline-noinline-crossinline-what-do-they-mean-b13f48e113c2
 * https://stackoverflow.com/questions/44471284/when-to-use-an-inline-function-in-kotlin/44471411#44471411
 * https://medium.com/@tferreirap/kotlin-quick-look-at-inline-noinline-and-crossinline-e62e8833db1f
 * **/

object InlineFunctionTypes {
    @JvmStatic
    fun main(args: Array<String>) {
       //higherOrerFunction2 { callInsideHigherFunction() }
    }


    inline fun higherOrderFunction2(noinline dontInlineLambda: () -> Unit, aLambda2: () -> Unit) {
        dontInlineLambda()//won't be inlined.
        //cannot return from noinline function
        aLambda2.invoke()
    }

    inline fun crossinlinehigherOrderFunction(crossinline aLambda:
                                              () -> Unit) {
         normalFunction {
            aLambda() //must mark aLambda as crossinline to use in this context.
        }



    }

    fun normalFunction(aLambda: () -> Unit) {
        return

    }

    fun callingFunction() {
        //inline functions can return inside here but normal functions cannot return
        /*normalFunction {
            return  //Error. Can't return from here.
        }*/

        //Can return


    }





    fun callingFunction2() {
        higherOrderFunction2 ({
            print("Non-local control flow")
            //return //Can't return here since this lambda is noinline

        }, {})

    }

}