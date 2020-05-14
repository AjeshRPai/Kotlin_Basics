package InterfaceFunctions

interface TestintInterface {

     fun firstFunction(){
       println("Printing first function")
    }

    fun secondFunction(){
        println("Print Second function")
    }

}


interface  TestInterface2{

    //If u change the name of the functions to
    // the same as in the TestInterface then the compiler
    // will give an error saying that u will have to
    // implement both of the functions

    fun firstFunctionis(){
        println("Printing first function")
    }

    fun secondFunctionis(){
        println("Print Second function")
    }


}