package com.samuelriesterer.couplesconnect.data

import androidx.room.*
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface DAODatabase
{
	@Query("SELECT * FROM TableFavorites ORDER BY questionID")
	fun returnFavorites(): MutableList<EntityFavorites>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertFavorites(list: MutableList<EntityFavorites>)

	@Query("UPDATE TableFavorites SET favorite =:value WHERE questionID =:ID ")
	fun updateFavorite(ID: Int, value: Boolean)
}
