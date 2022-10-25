package com.samuelriesterer.couplesconnect.data

import android.graphics.drawable.Drawable
import com.samuelriesterer.couplesconnect.general.C
import com.samuelriesterer.couplesconnect.general.Logger

class Category(var id: Int, var heading: String, var icon: Drawable?, var color: Int)

{
	fun copy() : Category{
		Logger.log(C.LOG_I, "Category", object {}.javaClass.enclosingMethod?.name, "start")
		return Category(this.id, this.heading, this.icon, this.color)
	}
}