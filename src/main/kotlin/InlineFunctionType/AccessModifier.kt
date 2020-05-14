package InlineFunctionType

object AccessModifier{

    @JvmStatic
    fun main(args: Array<String>) {
      higherOrderFunction {
          print("somethin g")
      }
    }

     override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    private val aPrivateMemberVariable = "I'm private"

    @PublishedApi
    internal val anInternalMemberVariable = "I'm internal"

    val aPublicMemberVariable = "I'm public"


    // Accessing of variables inside higher order function

    inline fun higherOrderFunction(aLambda: () -> Unit) {

        //ERROR: can't access this variable in an inline function.
        //Private members of the Class cant be accessed inside Inline function
        //aPrivateMemberVariable.length

        //Can access this variable because it's marked with @PublishedApi and internal
        anInternalMemberVariable.length

        //Can access this variable, it's public
        aPublicMemberVariable.length

    }
}