package com.samuelriesterer.couplesconnect.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.samuelriesterer.couplesconnect.general.Settings

@Database(
	entities = [EntityFavorites::class, EntityConfiguration::class
	],
	version = 1,
	exportSchema = false) @TypeConverters(Converters::class) abstract class DatabaseApp : RoomDatabase()
{
	abstract fun daoDatabase(): DAODatabase

	companion object
	{
		var INSTANCE: DatabaseApp? = null
		fun getDatabase(context: Context): DatabaseApp?
		{
			if(INSTANCE == null)
			{

				//				val MIGRATION_1_2 = object : Migration(1, 2) {
				//					override fun migrate(database: SupportSQLiteDatabase) {
				//						database.execSQL("ALTER TABLE PersonsTable ADD COLUMN allowanceList DOUBLE NOT NULL DEFAULT 0.0")
				//					}
				//				}

				synchronized(DatabaseApp::class) {
					INSTANCE = Room.databaseBuilder(context.applicationContext, DatabaseApp::class.java,
						Settings.databaseFilename)
						//						.addMigrations(MIGRATION_1_2)
						.build()
				}
			}
			return INSTANCE
		}


	}
}