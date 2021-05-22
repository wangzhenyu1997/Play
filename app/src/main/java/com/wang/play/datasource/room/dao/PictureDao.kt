package com.wang.play.datasource.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wang.play.datasource.room.entity.Picture
import com.wang.play.datasource.room.entity.User


@Dao
interface PictureDao {

    //插入对象，返回值为主键数组
    @Insert
    fun insertPicture(vararg pictures: Picture): LongArray?

    //根据主键更新,返回的是更新的数量
    @Update
    fun updatePicture(vararg pictures: Picture): Int

    //根据主键删除,返回的是删除的数量
    @Delete
    fun deletePicture(vararg pictures: Picture): Int

    //删除,返回的是删除的数量
    @Query("Delete From t_picture")
    fun deleteAllPicture(): Int

    @Query("SELECT * From t_picture ORDER BY id ASC ")
    fun getAllPicture(): LiveData<List<Picture?>?>

}