package DSL

object DSLMaker {


    fun person(block: PersonBuilder.() -> Unit): Person =PersonBuilder().apply(block).build()


    fun user(name:String,age:String,block:User.()->Unit):User=User(name,age).apply(block)


    fun Menu(name:String,age:Int=0){

    }


    class PersonBuilder {
        var name: String = ""
        var age: Int = 0

        private var address: Address? = null

        fun build() = Person(name, age, address)

        fun address(block: AddressBuilder.() -> Unit) {
            address = AddressBuilder().apply(block).build()
        }

        class AddressBuilder {

            var street: String = ""
            var number: Int = 0
            var city: String = ""

            fun build(): Address = Address(street, number, city)

        }
    }

    @JvmStatic
    fun main(args:Array<String>){
        val person=person {
            name="Ajesh"
            age=27
            address{
                city="Kochi"
                number=654
                street="K Street"
            }
        }


        print(person.toString())

        val user= user("ajesh","27"){
            number="this"
        }




        print(user)
    }
}