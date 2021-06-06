package com.wang.play

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {
    launch {
        delay(1000)
        println("AAAA")
    }

    runBlocking {
       launch {
           delay(2000)
           println("BBBB")
       }
       delay(500)
       println("CCCCC")
   }


}