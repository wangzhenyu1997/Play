package com.wang.play


fun main() {
    var a: Apple? = Apple()//强引用

    println(a)

    a = null

    println(a)

    System.gc()

    Thread.sleep(1000)
    println()
    println()
    println("______________")
    println("______________")
    println("______________")
} 