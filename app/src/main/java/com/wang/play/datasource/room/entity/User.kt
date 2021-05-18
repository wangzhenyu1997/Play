package com.wang.play.datasource.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "t_user")
data class User(
    @ColumnInfo(name = "first_name", defaultValue = "Wang")
    var firstName: String,
    @ColumnInfo(name = "last_name", defaultValue = "ZhenYu")
    var lastName: String
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0
}
