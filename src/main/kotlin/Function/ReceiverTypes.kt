package Function

/**
 * https://blog.kotlin-academy.com/programmer-dictionary-extension-receiver-vs-dispatch-receiver-cd154e57e277
 * */

object ReceiverTypes {

    @JvmStatic
    fun main(args: Array<String>) {
        val person = Person()
        //person.uploadToBackend() // Compilation error

        val ball = Ball("Golf ball")
        ball.bounce()

    }

    fun Ball.bounce() {
        //This is an example of Extension receiver
        println("Receiver type is ${this.javaClass}, Reciver object is ${this.name}")
        // prints: Receiver type is Ball, Receiver object is Golf ball

    }

    data class Ball(var name:String)

    class Person {
        fun move() {}
    }
    class Database (val person:Person) {
        fun loadData() {}


        fun doSomething() {
            person.uploadToBackend(); // We can access extension here
        }

        // Dispatch receiver
        fun Person.uploadToBackend() {
            move()
            loadData()
        }
    }



}