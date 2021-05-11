package com.wang.play

data class Person(val a: String, val b: String) {

}

fun main() {

    val a = Person("AA", "BB")
    val b = Person("AA", "BB")
    val c = Person("CC", "DD")
    val d = Person("EE", "FF")

    val apple = mutableSetOf<Person>()

    apple.add(a)
    apple.add(b)
    apple.add(c)
    apple.add(d)

    println(apple)

}