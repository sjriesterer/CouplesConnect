package com.samuelriesterer.couplesconnect.data


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TableFavorites")

class EntityFavorites
	(
	@PrimaryKey
	var id: Int,
	var favorite: Boolean
)