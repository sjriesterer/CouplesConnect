package com.samuelriesterer.couplesconnect.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.samuelriesterer.couplesconnect.general.C
import com.samuelriesterer.couplesconnect.general.Logger
import com.samuelriesterer.couplesconnect.general.Settings

@Entity(tableName = "TableConfiguration")

/*=======================================================================================================*/
/* CONSTRUCTOR                                                                                           */
/*=======================================================================================================*/
class EntityConfiguration (
	@PrimaryKey
	var id: Int,
	var categoriesTurnedOn: MutableList<Boolean>,
	var subcategoriesTurnedOn: MutableList<Boolean>,
	var currentSortOrder: Int,
	var currentFilterType: Int
)
/*=======================================================================================================*/
/* METHODS                                                                                               */
/*=======================================================================================================*/ {
	fun copy(): EntityConfiguration {
		Logger.log(C.LOG_I, "EntityConfiguration", object {}.javaClass.enclosingMethod?.name, "start")
		val cat: MutableList<Boolean> = mutableListOf<Boolean>().apply { addAll(categoriesTurnedOn) }
		val sub: MutableList<Boolean> = mutableListOf<Boolean>().apply { addAll(subcategoriesTurnedOn) }
		return EntityConfiguration(id, cat, sub, currentSortOrder, currentFilterType)
	}
	/*=======================================================================================================*/
	fun compare(config2: EntityConfiguration) : Boolean {
		Logger.log(C.LOG_I, "EntityConfiguration", object {}.javaClass.enclosingMethod?.name, "start")

		if(this.currentSortOrder != config2.currentSortOrder || this.currentFilterType != config2.currentFilterType)
			return false
		for(i in this.categoriesTurnedOn.indices) {
			if(this.categoriesTurnedOn[i] != config2.categoriesTurnedOn[i])
				return false
		}
		for(i in this.subcategoriesTurnedOn.indices) {
			if(this.subcategoriesTurnedOn[i] != config2.subcategoriesTurnedOn[i])
				return false
		}
		Logger.log(C.LOG_I, Settings.TAG, object {}.javaClass.enclosingMethod?.name, "Configurations are the same")
		return true
	}
	/*=======================================================================================================*/
	fun print() {
		println("Entity Configuration id = ${this.id}")
		println("Sort order = ${this.currentSortOrder}; Filter type = ${this.currentFilterType}")
		println("Categories:")
		for(i in this.categoriesTurnedOn.indices)
			println("Cat $i : ${Data.categoryNames[i]} = ${this.categoriesTurnedOn[i]}")

		println("Subcategories:")
		for(i in this.subcategoriesTurnedOn.indices)
			println("Cat $i : ${Data.subcategoryNames[i]} = ${this.subcategoriesTurnedOn[i]}")

	}
	/*=======================================================================================================*/
	fun noneSelected() : Boolean {
		Logger.log(C.LOG_I, "EntityConfiguration", object {}.javaClass.enclosingMethod?.name, "start")
		for(i in this.subcategoriesTurnedOn.indices) {
			if(this.subcategoriesTurnedOn[i])
				return false
		}
		return true

	}
}
