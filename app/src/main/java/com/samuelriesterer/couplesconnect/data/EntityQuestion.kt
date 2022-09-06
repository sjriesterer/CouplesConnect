package com.samuelriesterer.couplesconnect.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TableQuestion")

class EntityQuestion	(
	@PrimaryKey
	var id: Int,
	var category: Int,
	var subcategory: Int,
	var topic: Int,
	var question: String
)