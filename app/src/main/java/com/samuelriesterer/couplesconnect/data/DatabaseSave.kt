package com.samuelriesterer.couplesconnect.data

import android.content.Context
import com.samuelriesterer.couplesconnect.activities.ActivityMain
import com.samuelriesterer.couplesconnect.general.C
import com.samuelriesterer.couplesconnect.interfaces.InterfaceMain
import com.samuelriesterer.couplesconnect.general.Logger

class DatabaseSave(var activity: ActivityMain) {
	companion object {
		private val TAG = "DATABASE_SAVE"
		lateinit var interfaceMain: InterfaceMain
		const val numTokensBase = 9
		const val numTokensRange = 8

		/*=======================================================================================================*/
		fun setup(context: Context) {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, ": start")

			try {
				interfaceMain = context as InterfaceMain
			} catch (e: ClassCastException) {
				throw ClassCastException("$context must implement interfaceMain")
			}
		}

	}
}