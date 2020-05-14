package Delegation

import kotlin.collections.ArrayList


object MainClassDelegation {
    @JvmStatic
    fun main(args: Array<String>) {
    }
}

object apiSingleton{
}

interface ApiInterface {
     fun getSomething(userid: String,token: String):List<MyData>
}

data class A(val apiInterface:A,
             val userid:String,
             val token:String,
             val errorHandler: ErrorHandler): ApiInterface by apiInterface

data class ErrorHandler(val defaultString: String,
                            val sockettimeOutString:String?=defaultString,
                            val timeOutError:String?=defaultString,
                            val illegalStateString:String?=defaultString,
                            val invalidData:String?=defaultString)


data class MyData(val name:String,val id:Int)

private fun getDummyValues(): ArrayList<MyData> {
    val values=ArrayList<MyData>(10)
    for (i in 1..10){
        values.add(MyData("name"+i,i))
    }
    return values
}

sealed class Failure(val defaultString: String,
                     val sockettimeOutString:String?=defaultString,
                     val timeOutError:String?=defaultString,
                     val illegalStateString:String?=defaultString,
                     val invalidData:String?=defaultString)