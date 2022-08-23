package com.samuelriesterer.couplesconnect.general

import android.content.Context
import android.graphics.Typeface
import com.samuelriesterer.couplesconnect.R
import com.samuelriesterer.couplesconnect.data.EntityConfiguration
import com.samuelriesterer.couplesconnect.interfaces.InterfaceMain

class Settings {
	companion object {
		const val TAG: String = "~*SETTINGS"

		/* App */
		lateinit var fragmentNames: List<String>
		lateinit var fonts: List<Typeface>
		lateinit var interfaceMain: InterfaceMain
		var appName = "CouplesConnect"
		val databaseFilename = "CouplesConnectDB"
		var databaseFilePath = ""
		const val appDirectory = "CouplesConnect"
		const val appAcronym = "CC"
		const val sharedPreferenceKey: String = "${appAcronym}_SP"
		var currentFragment = C.FRAG_HOME

		/* Debug */
		// Debug: displays extra debugging print statements if true:
		//				var debugText = true
		var debugText = false

		/* SHARED PREFERENCES ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
		/* Int Settings */
		lateinit var settingsInt: IntArray
		val settingsIntKeys = arrayOf(
			"SETTING_INT1",
			"SETTING_INT2",
			"SETTING_INT3"
		)

		/* Boolean Settings */
		lateinit var settingsBoolean: BooleanArray
		val settingsBooleanKeys = arrayOf(
			"SETTING_APP_INITIALIZED",
			"SETTING_KEEP_SORT_SETTING",
			"SETTING_BOOLEAN3"
		)

		/*=======================================================================================================*/
		/* SETUP                                                                                                 */
		/*=======================================================================================================*/
		fun setup(context: Context) {
			/* Interface */
			try {
				interfaceMain = context as InterfaceMain
			}
			catch (e: ClassCastException) {
				throw ClassCastException("$context must implement interfaceMain")
			}


			currentFragment = C.FRAG_HOME
			fragmentNames = context.resources.getStringArray(R.array.fragment_names).toList()
			appName = context.resources.getString(R.string.app_name)
			settingsInt = getDefaultSettingsInt()
			settingsBoolean = getDefaultSettingsBoolean()
			getSettings(context)

			//			fonts =
			//				listOf(
			//					Typeface.SERIF,
			//					ResourcesCompat.getFont(context, R.font.varela)!!,
			//					ResourcesCompat.getFont(context, R.font.oswald_light)!!,
			//					Typeface.createFromAsset(context.assets, "fonts/arial.ttf"),
			//					Typeface.createFromAsset(context.assets, "fonts/GOTHIC.TTF"),
			//					Typeface.createFromAsset(context.assets, "fonts/times.ttf"),
			//					Typeface.MONOSPACE,
			//					ResourcesCompat.getFont(context, R.font.alex_brush)!!,
			//					Typeface.createFromAsset(context.assets, "fonts/OldEnglishFive.ttf")
			//				)
		}

		/*=======================================================================================================*/
		/* DEFAULTS                                                                                              */
		/*=======================================================================================================*/
		fun getDefaultSortOrder() : Int {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
			return C.SORT_ID
		}
		/*=======================================================================================================*/
		fun getDefaultFilterType() : Int {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
			return C.FILTER_ALL_TYPES
		}

		/*=======================================================================================================*/
		fun getDefaultSettingsInt(): IntArray {
			// Init all default and starting int settings here
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
			//@formatter:off
			return intArrayOf(
				0,
				0,
				0
			)
			//@formatter:on
		}

		/*=======================================================================================================*/
		fun getDefaultSettingsBoolean(): BooleanArray {
			// Init all default and starting boolean settings here
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
			//@formatter:off
			return booleanArrayOf(
				false,
				true,
				true
			)
			//@formatter:on
		}

		/*=======================================================================================================*/
		fun saveSettingInt(value: Int, id: Int) {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start: value = $value, id = $id")
			settingsInt[id] = value
			interfaceMain.putSettingInt(sharedPreferenceKey, settingsIntKeys[id], settingsInt[id])
		}

		/*=======================================================================================================*/
		fun saveSettingBoolean(value: Boolean, id: Int) {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start: value = $value, id = $id")
			settingsBoolean[id] = value
			interfaceMain.putSettingBoolean(sharedPreferenceKey, settingsBooleanKeys[id], settingsBoolean[id])
		}

		/*=======================================================================================================*/
		fun saveSettings() {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
			for(i in settingsBoolean.indices)
				saveSettingBoolean(settingsBoolean[i], i)
			for(i in settingsInt.indices)
				saveSettingInt(settingsInt[i], i)
		}

		/*=======================================================================================================*/
		fun getSettings(context: Context) {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
			val spSaved = context.getSharedPreferences(sharedPreferenceKey, Context.MODE_PRIVATE)
			/// Int Settings:
			for(i in settingsInt.indices) settingsInt[i] = spSaved.getInt(settingsIntKeys[i], settingsInt[i])
			/// Boolean Settings:
			for(i in settingsBoolean.indices) settingsBoolean[i] = spSaved.getBoolean(settingsBooleanKeys[i], settingsBoolean[i])
		}

		/*=======================================================================================================*/
		/* GENERAL METHODS                                                                                       */
		/*=======================================================================================================*/
	}
}