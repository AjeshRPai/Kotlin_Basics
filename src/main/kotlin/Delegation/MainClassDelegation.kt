package Delegation

import coroutines.channel.Coffee

// example from advanced kotlin book
interface Creature {
    val attackPower: Int
    val defensePower: Int
    fun attack()
}
class GenericCreature(
    override val attackPower: Int,
    override val defensePower: Int,
) : Creature {
    override fun attack() {
        println("Attacking with $attackPower")
    }
}
class Goblin : Creature by GenericCreature(2, 1)

fun example1() {
    val goblin = Goblin()
    println(goblin.defensePower) // 1
    goblin.attack() // Attacking with 2
}


interface Instrument {
    fun play()
}

class Guitar : Instrument {
    override fun play() {
        println("Strumming the guitar!")
    }
}

class Guitarist : Instrument by Guitar() // Delegating directly to an instance of Guitar


class Piano : Instrument {
    override fun play() {
        println("Playing the piano!")
    }
}

class Pianist(instrument: Instrument) : Instrument by instrument // Delegate passed through constructor

class Musician(private val instrument: Instrument) {
    fun perform() {
        instrument.play()
    }
}

val piano = Piano()
class PianistFromOuter : Instrument by piano // Delegating to a variable from outer scope


class GuitaristWithDefault(val instrument: Instrument = Guitar()) : Instrument by instrument // Default to Guitar


fun example2() {
    val guitar = Guitar()
    val piano = Piano()

    val guitarist = Musician(guitar)
    val pianist = Musician(piano)

    guitarist.perform() // Strumming the guitar!
    pianist.perform()   // Playing the piano!


    fun main() {
        // Guitarist using a direct delegate
        val guitarist = Guitarist()
        guitarist.play() // Output: Strumming the guitar!

        // Pianist using a constructor parameter
        val pianist = Pianist(Guitar()) // Delegating to Guitar
        pianist.play() // Output: Strumming the guitar!

        // Pianist using an outer scope variable
        val pianistFromOuter = PianistFromOuter()
        pianistFromOuter.play() // Output: Playing the piano!

        // Guitarist with a default parameter
        val guitaristWithDefault = GuitaristWithDefault()
        guitaristWithDefault.play() // Output: Strumming the guitar! (default)
    }

}


interface Singer{
    fun sing() {
        println("singing")
    }
}

interface Dancer {
    fun dance() {
        println("dancing")
    }
}

class Vocalist(): Singer

class BreakDancer(): Dancer


class Artist(
    val breakDancer: BreakDancer,
    val vocalist: Vocalist
): Singer by vocalist, Dancer by breakDancer


fun main() {
    val artist = Artist(BreakDancer(), Vocalist())
    artist.sing()
    artist.dance()
}

