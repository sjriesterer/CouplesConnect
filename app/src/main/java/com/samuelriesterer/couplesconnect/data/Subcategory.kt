package com.samuelriesterer.couplesconnect.data

import android.graphics.drawable.Drawable
import com.samuelriesterer.couplesconnect.general.C
import com.samuelriesterer.couplesconnect.general.Logger

class Subcategory(var id: Int, var category: Int, var heading: String, var icon: Drawable?, var color: Int)

{
	fun copy() : Subcategory{
		Logger.log(C.LOG_I, "Subcategory", object {}.javaClass.enclosingMethod?.name, "start")
		return Subcategory(this.id, this.category, this.heading, this.icon, this.color)
	}
}