package com.samuelriesterer.couplesconnect.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TableConfiguration")

class EntityConfiguration (
	@PrimaryKey
	var id: Int,
	var categories: MutableList<Boolean>,
	var subcategories: MutableList<Boolean>,
	var currentSortOrder: Int,
	var currentFilterType: Int
)