package com.wang.play.data.main

data class testResponse(
    val `data`: List<Data>,
    val errorCode: Int,
    val errorMsg: String
) {
    data class Data(
        val children: List<Any>,
        val courseId: Int,
        val id: Int,
        val name: String,
        val order: Int,
        val parentChapterId: Int,
        val userControlSetTop: Boolean,
        val visible: Int
    )
}

