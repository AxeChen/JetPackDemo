package com.axe.room.dao

import androidx.room.*
import com.axe.room.bean.User
import io.reactivex.Single


@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getUsers(): Single<MutableList<User>>

    @Query("SELECT * FROM user where id=:id")
    fun getUserById(id: Int): User

    @Insert
    fun insertUser(user: User):Long

    @Update
    fun updateUser(user: User):Int

    @Delete
    fun deleteUser(user:User):Int

}