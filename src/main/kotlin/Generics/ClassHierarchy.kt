package Generics

fun firstOne() {
    fun takeIntList(list: List<Int>) {}
    takeIntList(listOf<Any>())
    takeIntList(listOf<Nothing>())
}

fun secondOne() {
    fun takeIntMutableList(list: MutableList<Int>) {}
    takeIntMutableList(mutableListOf<Any>())
    takeIntMutableList(mutableListOf<Nothing>())
}

fun thirdOne() {
    fun takeAnyList(list: List<Any>) {}
    takeAnyList(listOf<Int>())
    takeAnyList(listOf<Nothing>())
}

fun fourthOne() {
    class BoxOut<out T>
    fun takeBoxOutInt(box: BoxOut<Int>) {}
    takeBoxOutInt(BoxOut<Int>())
    takeBoxOutInt(BoxOut<Number>())
    takeBoxOutInt(BoxOut<Nothing>())
}

fun fifthOne() {
    class BoxOut<out T>
    fun takeBoxOutNumber(box: BoxOut<Number>) {}
    takeBoxOutNumber(BoxOut<Int>())
    takeBoxOutNumber(BoxOut<Number>())
    takeBoxOutNumber(BoxOut<Nothing>())
}

fun sixthOne() {
    class BoxOut<out T>
    fun takeBoxOutNothing(box: BoxOut<Nothing>) {}
    takeBoxOutNothing(BoxOut<Int>())
    takeBoxOutNothing(BoxOut<Number>())
    takeBoxOutNothing(BoxOut<Nothing>())
}

fun seventhOne() {
    class BoxOut<out T>
    fun takeBoxOutStar(box: BoxOut<*>) {}
    takeBoxOutStar(BoxOut<Int>())
    takeBoxOutStar(BoxOut<Number>())
    takeBoxOutStar(BoxOut<Nothing>())
}

fun eight() {
    class BoxIn<in T>
    fun takeBoxInInt(box: BoxIn<Int>) {}
    takeBoxInInt(BoxIn<Int>())
    takeBoxInInt(BoxIn<Number>())
    takeBoxInInt(BoxIn<Nothing>())
    takeBoxInInt(BoxIn<Any>())
}
