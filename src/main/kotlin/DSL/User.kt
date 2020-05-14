package DSL

data class Person(val name: String,
                  val age: Int,
                  var address: Address? = null)


data class Address(val street: String,
                   val number: Int,
                   var city: String? = null)


data class User(val name:String,
                val age:String,
                var number:String?=null)
