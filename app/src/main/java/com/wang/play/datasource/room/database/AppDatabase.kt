package com.wang.play.datasource.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.wang.play.datasource.room.dao.PictureDao
import com.wang.play.datasource.room.dao.UserDao
import com.wang.play.datasource.room.entity.Picture
import com.wang.play.datasource.room.entity.User

@Database(entities = [User::class, Picture::class], version = 3)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun pictureDao(): PictureDao


    companion object {

        private var instance: AppDatabase? = null
        private const val DATABASE_NAME = "app_database.db"

        //懒汉式
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class.java) {
                    if (instance == null) {
                        instance = createInstance(context)
                    }
                }
            }
            return instance
        }

        //创建数据库
        private fun createInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addMigrations(migration_1_2)
                 .addMigrations(migration_2_3)
                .build()
        }

        //版本1升级到版本2
        private val migration_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "create table t_picture (uid INTEGER Primary KEY NOT NULL ,id TEXT NOT NULL," +
                            "desc TEXT,url TEXT)"
                )
            }

        }

        //版本2升级到版本3
        private val migration_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "create table t_temp (uid INTEGER Primary KEY NOT NULL ,id TEXT NOT NULL," +
                            "desc TEXT NOT NULL,url TEXT NOT NULL,type TEXT NOT NULL default 'wei_zhi')"
                )
                database.execSQL(
                    "INSERT INTO t_temp (id,desc,url)" +
                            "SELECT id,desc,url FROM t_picture"
                )
                database.execSQL("DROP TABLE t_picture")
                database.execSQL("ALTER TABLE t_temp RENAME to t_picture")

            }
        }


    }

}

