package com.samuelriesterer.couplesconnect.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.samuelriesterer.couplesconnect.general.C
import com.samuelriesterer.couplesconnect.general.Logger

@Entity(tableName = "TableConfiguration")

class EntityConfiguration (
	@PrimaryKey
	var id: Int,
	var categories: MutableList<Boolean>,
	var subcategories: MutableList<Boolean>,
	var currentSortOrder: Int,
	var currentFilterType: Int
)
/*=======================================================================================================*/ {
	fun copy(): EntityConfiguration {
		Logger.log(C.LOG_I, "", object {}.javaClass.enclosingMethod?.name, ": start")
		val cat: MutableList<Boolean> = mutableListOf<Boolean>().apply { addAll(categories) }
		val sub: MutableList<Boolean> = mutableListOf<Boolean>().apply { addAll(subcategories) }
		return EntityConfiguration(id, cat, sub, currentSortOrder, currentFilterType)
	}
}
