package com.wang.play


fun main() {

    val temp = { a: String, b: Int ->
        "$a  ${b.toString()}"
    }


    show("aa", temp)
}


private fun show(a: String, b: (String, Int) -> String) {
    println(b(a, 2))
}

