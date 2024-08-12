package collectionsExample



object CollectionsExample {
    @JvmStatic
    fun main(args:Array<String>){

    }
}


fun getDummyList(){
    val result=ArrayList<NewUser>()
}

fun getDummyAddress(){

}



data class NewUser(val id:Int,val name:String,val addresses:List<Address>)

data class Address(val id:Int,val city:String,val pincode:String)


