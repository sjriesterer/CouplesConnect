package com.samuelriesterer.couplesconnect.data

import androidx.room.Query
import com.samuelriesterer.couplesconnect.activities.ActivityMain
import com.samuelriesterer.couplesconnect.general.C
import com.samuelriesterer.couplesconnect.general.Logger
import com.samuelriesterer.couplesconnect.general.Settings

class DatabaseOps {
	companion object
	{
		val TAG: String = "~*DATABASE_OPS"
		/*=======================================================================================================*/
		fun getFavorites() : MutableList<EntityFavorites>{
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
		/*=======================================================================================================*/
		fun getConfiguration() : EntityConfiguration {
			Logger.log(C.LOG_I, TAG, object{}.javaClass.enclosingMethod?.name, "start")
			var config = Data.getEmptyConfiguration()
			val thread = Thread{
				config = ActivityMain.databaseApp.daoDatabase().returnConfiguration(0)
			}
			thread.start()
			thread.join()
			return config
		}
		/*=======================================================================================================*/
		fun insertConfiguration(entity: EntityConfiguration) {
			Logger.log(C.LOG_I, TAG, object{}.javaClass.enclosingMethod?.name, "start")
			val thread = Thread{
				ActivityMain.databaseApp.daoDatabase().insertConfiguration(entity)
			}
			thread.start()
		}
	}
}
