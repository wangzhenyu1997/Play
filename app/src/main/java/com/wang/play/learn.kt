package com.wang.play

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {


    GlobalScope.launch {
        delay(1000)
        println("Kotlin AA")
    }

    println("Hello")
   // delay(1000)
}