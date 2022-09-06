package com.samuelriesterer.couplesconnect.data

import androidx.room.*
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface DAODatabase
{
	/* Favorites */
	@Query("SELECT * FROM TableFavorites ORDER BY id")
	fun returnFavorites(): MutableList<EntityFavorites>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertFavorites(list: MutableList<EntityFavorites>)

	@Query("UPDATE TableFavorites SET favorite =:value WHERE id =:ID ")
	fun updateFavorite(ID: Int, value: Boolean)

	/* Configuration */
	@Query("SELECT * FROM TableConfiguration WHERE id =:ID")
	fun returnConfiguration(ID: Int): EntityConfiguration

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertConfiguration(entity: EntityConfiguration)

	/* Questions */
	@Query("SELECT * FROM TableQuestion ORDER BY id")
	fun returnQuestions(): MutableList<EntityQuestion>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertQuestions(list: MutableList<EntityQuestion>)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertQuestion(question: EntityQuestion)

	@Query("DELETE FROM TableQuestion where id =:ID")
	fun deleteQuestion(ID: Int)
}
