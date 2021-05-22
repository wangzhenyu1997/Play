package com.wang.play.datasource.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "t_picture")
data class Picture(
    val id: String,
    val desc: String,
    val url: String,
    @ColumnInfo(defaultValue = "wei_zhi")
    val type: String
){
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0
}