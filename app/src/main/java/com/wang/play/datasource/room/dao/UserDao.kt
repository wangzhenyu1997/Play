package com.wang.play.datasource.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wang.play.datasource.room.entity.User

@Dao
interface UserDao {

    //插入对象，返回值为主键数组
    @Insert
    fun insertUser(vararg users: User): LongArray?

    //根据主键更新,返回的是更新的数量
    @Update
    fun updateUser(vararg users: User): Int

    //根据主键删除,返回的是删除的数量
    @Delete
    fun deleteUser(vararg users: User): Int

    //删除,返回的是删除的数量
    @Query("Delete From t_user")
    fun deleteAllUser():Int

    @Query("SELECT * From t_user ORDER BY uid ASC ")
    fun getAllUsers(): LiveData<List<User?>?>
}
