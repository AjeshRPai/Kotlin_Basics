package CompanionObject

object Example {
    @JvmStatic
    fun main(args: Array<String>) {

    }
}

class FirstClass{
    companion object :Function(){

    }

    fun somethingElse(){
        doSomething()
    }
}

class SecondClass{
    companion object:Function() {

    }

    fun somethingElse(){
        doSomething()
    }
}

open class Function{

    fun doSomething(){

    }
}


