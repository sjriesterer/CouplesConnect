package com.samuelriesterer.couplesconnect.general

import android.content.Context
import com.samuelriesterer.couplesconnect.R

class Settings {

	companion object {
		const val TAG: String = "~*SETTINGS"
		lateinit var categoryColors: List<Int>
		lateinit var subcategoryColors: List<Int>
		lateinit var fragmentNames: List<String>
		/* DEBUG +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
		// Debug: displays extra debugging print statements if true:
		//				var debugText = true
		var debugText = false

		/* PROGRAM +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
		var appName = "CouplesConnect"
		const val appDirectory = "CouplesConnect"
		const val appAcronym = "CC"
		const val sharedPreferenceKey: String = "${appAcronym}_SP"

		var currentFragment = C.FRAG_HOME

		fun setup(context: Context) {

			categoryColors = context.resources.getIntArray(R.array.category_colors).toList()
			subcategoryColors = context.resources.getIntArray(R.array.subcategory_cat1_colors).toList()
			fragmentNames = context.resources.getStringArray(R.array.fragment_names).toList()
		}

	}
}