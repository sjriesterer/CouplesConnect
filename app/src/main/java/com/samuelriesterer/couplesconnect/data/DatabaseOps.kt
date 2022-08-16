package com.samuelriesterer.couplesconnect.data

import androidx.room.Query
import com.samuelriesterer.couplesconnect.activities.ActivityMain
import com.samuelriesterer.couplesconnect.general.C
import com.samuelriesterer.couplesconnect.general.Logger

class DatabaseOps {
	companion object
	{
		val TAG: String = "~*DATABASE_OPS"
		/*=======================================================================================================*/
		fun databaseGetFavorites() : MutableList<EntityFavorites>{
			Logger.log(C.LOG_I, TAG, object{}.javaClass.enclosingMethod?.name, "start")
			var list: MutableList<EntityFavorites> = mutableListOf()

			val thread = Thread{
				list = ActivityMain.databaseApp.daoDatabase().returnFavorites()
			}
			thread.start()
			thread.join()
			return list
		}
		/*=======================================================================================================*/
		fun insertFavorites(list: MutableList<EntityFavorites>) {
			Logger.log(C.LOG_I, TAG, object{}.javaClass.enclosingMethod?.name, "start")
			val thread = Thread{
				ActivityMain.databaseApp.daoDatabase().insertFavorites(list)
			}
			thread.start()
		}
		/*=======================================================================================================*/
		fun updateFavorite(id: Int, value: Boolean) {
			Logger.log(C.LOG_I, TAG, object{}.javaClass.enclosingMethod?.name, "start")
			val thread = Thread{
				ActivityMain.databaseApp.daoDatabase().updateFavorite(id, value)
			}
			thread.start()
		}
	}
}
