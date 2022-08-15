package com.samuelriesterer.couplesconnect.general

import android.Manifest
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.pm.PackageManager
import android.os.Environment
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.samuelriesterer.couplesconnect.activities.ActivityMain
import java.io.*
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*

class Logger (private val activityMain: ActivityMain, sharedPreferenceKey: String, appDirectory: String, exportLog: Boolean)
/*=======================================================================================================*/
{
	private var sharedPreferenceKey = ""
	private var appDirectory = ""

	/// Paths:
	private var programFolderPath: String = ""

	/// Shared Preference Strings:
	private val programFolderPathString = "PROGRAM_FOLDER_PATH"
	private val programFolderInitializedString = "PROGRAM_FOLDER_INITIALIZED"
	private val programDebugLogFilePathString = "PROGRAM_STARTUP_LOG_PATH"
	private val writePermissionDeniedAlreadyString = "WRITE_PERMISSION_DENIED_ALREADY"
	private val writePermissionString = "WRITE_PERMISSION"

	/// Media values:
	private val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1
	private val MEDIA_UNAVAILABLE = 2
	private val MEDIA_READ_ONLY = 3
	private val MEDIA_UNKNOWN_ERROR = 4
	private val MEDIA_NOT_PERMITTED = 5
	private val FOLDER_ALREADY_EXISTS = 6
	private val FOLDER_CREATED = 7

	/*==========================================================================================================*/
	/* INIT                                                                                                     */
	/*==========================================================================================================*/
	init {
		this.sharedPreferenceKey = sharedPreferenceKey
		this.appDirectory = appDirectory
		Companion.exportLog = exportLog
		getSettings()
	}

	/*==========================================================================================================*/
	/* COMPANION OBJECT                                                                                         */
	/*==========================================================================================================*/
	//<editor-fold desc="Companion Object">
	companion object {
		private val TAG = "LOGGER"
		private var exportLog = false

		///Files:
		private var debugFile: File? = null
		private var programDebugLogFilePath: String = ""

		/// Shared Preference Variables:
		private var programFolderInitialized = false
		private var writePermission = false
		private var writePermissionDeniedAlready = false
		private var debugFilesInitiated = false

		/// Log values:
		const val LOG_V = 0
		const val LOG_D = 1
		const val LOG_I = 2
		const val LOG_E = 3

		/*==========================================================================================================*/
		fun log(logType: Int, callingClass: String, callingMethod: String?, message: String) {
			/// Logs current message and then appends it to external file
			when (logType) {
				LOG_V -> Log.v(callingClass, "$callingMethod : $message")
				LOG_D -> Log.d(callingClass, "$callingMethod : $message")
				LOG_I -> Log.i(callingClass, "$callingMethod : $message")
				LOG_E -> Log.e(callingClass, "$callingMethod : $message")
			}
			if(exportLog && programFolderInitialized && debugFilesInitiated) {
				val sdf6 = SimpleDateFormat("mm:ss:SSS", Locale.getDefault())
				val currentDateAndTime = sdf6.format(Date())
				val s = "@: $currentDateAndTime~: " + Integer.toString(android.os.Process.myPid()) + "~: " + android.os.Process.myTid()
					.toString() + "~: " + callingClass + "~: " + message + "\n"
				exportToFile(debugFile, s, true)
			}
		}

		/*=======================================================================================================*/
		private fun exportToFile(file: File?, string: String, append: Boolean) {
			if(exportLog) {
				var out: Writer? = null
				try {
					out = BufferedWriter(OutputStreamWriter(FileOutputStream(file, append), StandardCharsets.UTF_8))
					out.write(string)
				}
				catch (e: IOException) {
					Log.e(TAG, e.toString())
				}

				try {
					out?.close()
				}
				catch (e: IOException) {
					Log.e(TAG, e.toString())
				}
			}
		}

		/*=======================================================================================================*/
		fun deleteLog() {
			log(LOG_I, TAG, "deleteLog", "Start")

			if(programFolderInitialized && programDebugLogFilePath != "" && debugFilesInitiated) {
				exportToFile(debugFile, "", false) /// Erases  log
			}
		}

		/*=======================================================================================================*/
		fun logToString(): String = File(programDebugLogFilePath).readText(Charsets.UTF_8)
	}

	//</editor-fold>
	/*==========================================================================================================*/
	/* METHODS                                                                                                  */
	/*==========================================================================================================*/
	//<editor-fold desc="Methods">
	private fun logToString(path: String): String {
		/// Reads the current log in external file and makes a task from it
		log(LOG_I, TAG, "logToString", "Start")
		val file = File(path)
		val fileInputStream: FileInputStream
		val inputStreamReader: InputStreamReader
		val bufferedReader: BufferedReader
		try {
			fileInputStream = FileInputStream(file)
			inputStreamReader = InputStreamReader(fileInputStream)
			bufferedReader = BufferedReader(inputStreamReader)
		}
		catch (e: IOException) {
			log(LOG_E, TAG, "logToString", e.toString())
			return "Unable to init bufferReader\n$e"
		}
		val sb = StringBuilder()
		var line: String
		try {
			line = bufferedReader.readLine()
			while(line != null) {
				sb.append(line)
				sb.append("\n")
				line = bufferedReader.readLine()
			}
		}
		catch (e: IOException) {
			log(LOG_E, TAG, "logToString", e.toString())
			sb.append("\nError reading from log file")
			sb.append(e.toString())
			return sb.toString()
		}

		try {
			bufferedReader.close()
		}
		catch (e: IOException) {
			e.printStackTrace()
			log(LOG_E, TAG, "logToString", e.toString())
			sb.append("\nError closing log file\n")
			sb.append(e.toString())
			return sb.toString()
		}

		return sb.toString()
	}

	/*=======================================================================================================*/
	fun attemptFolderCreation() {
		if(exportLog) {
			/// Two different processes for different API levels
			if(android.os.Build.VERSION.SDK_INT < 23) {
				log(LOG_D, TAG, "attemptFolderCreation", "API < 23")
				initProgramFolder()
			}
			else {
				log(LOG_D, TAG, "attemptFolderCreation", "API >= 23")
				initProgramFolderRequestPermission()
			}
		}
	}

	/*=======================================================================================================*/
	fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
		log(LOG_I, TAG, "onRequestPermissionsResult", "Start")
		when (requestCode) {
			MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE -> {
				if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					log(LOG_D, TAG, "onRequestPermissionsResult", "Permission granted")
					writePermission = true
					/// Handle the result of the permission request:
					initProgramFolder()
				}
				else
				/// Permission is denied
				{
					log(LOG_D, TAG, "onRequestPermissionsResult", "Permission not granted")
					writePermission = false
					if(!writePermissionDeniedAlready)
					/// Permission has already been denied previously
					{
						log(LOG_D, TAG, "onRequestPermissionsResult","Permission already denied")
					}
					writePermissionDeniedAlready = true
					putSettingBoolean(writePermissionDeniedAlreadyString, writePermissionDeniedAlready)
					programFolderInitialized = false
					programFolderPath = ""
					programDebugLogFilePath = ""
					putSettingBoolean(programFolderInitializedString, programFolderInitialized)
					putSettingString(programFolderPathString, programFolderPath)
					putSettingString(programDebugLogFilePathString, programDebugLogFilePath)
				}
				putSettingBoolean(writePermissionString, writePermission)
			}
		}
	}

	/*=======================================================================================================*/
	private fun analyseStorage(context: Context) {
		log(LOG_I, TAG, "analyseStorage", "Start")
		var totalSize: Long = 0
		val appBaseFolder = context.filesDir.parentFile
		if(appBaseFolder?.listFiles() != null)
		{
			for(f in appBaseFolder.listFiles()) {
				totalSize += if(f.isDirectory) {
					val dirSize = browseFiles(f)
					dirSize
				}
				else {
					f.length()
				}
			}
		}
		log(LOG_D, TAG, "analyseStorage", "App uses $totalSize total bytes")
	}

	/*=======================================================================================================*/
	private fun browseFiles(dir: File): Long {
		/// Returns the size in bytes of dir
		log(LOG_I, TAG, "browseFiles", "Start: dir = $dir")
		var dirSize: Long = 0
		if(dir.listFiles() != null) {
			for(f in dir.listFiles()) {
				dirSize += f.length()
				log(LOG_D, TAG, "browseFiles", dir.absolutePath + "/" + f.name + " weighs " + f.length())
				if(f.isDirectory) {
					dirSize += browseFiles(f)
				}
			}
		}

		return dirSize
	}

	/*=======================================================================================================*/
	private fun initProgramFolderRequestPermission() {
		log(LOG_D, TAG, "initProgramFolderRequestPermission", "API >= 23")
		// Write permission granted
		if(ContextCompat.checkSelfPermission(activityMain, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
			log(LOG_D, TAG, "initProgramFolderRequestPermission", "Write permission already granted")
			initProgramFolder()
		}
		/// Write permission not granted yet
		else {
			log(LOG_D, TAG, "initProgramFolderRequestPermission", "permission not explicitly granted; must request permission")
			/// If user has denied permission, give explanation
			if(ActivityCompat.shouldShowRequestPermissionRationale(activityMain, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
				log(LOG_D, TAG, "initProgramFolderRequestPermission", "Explanation needed to request permission")
				ActivityCompat.requestPermissions(activityMain, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)
			}
			else
			/// User has not denied permission yet: No explanation needed to user
			{
				log(LOG_D, TAG, "initProgramFolderRequestPermission", "permission: No explanation needed; request permission")
				ActivityCompat.requestPermissions(activityMain, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)
			}
		}
	}

	/*=======================================================================================================*/
	private fun makeFolder(folderName: String): Int {
		log(LOG_I, TAG, "makeFolder", "Start: folderName = $folderName")
		val state = Environment.getExternalStorageState() /// Gets environment state
		log(LOG_D, TAG, "makeFolder", "ExternalStorageDirectory = " + Environment.getExternalStorageDirectory() + "; fil path " + activityMain.getExternalFilesDir(null))
		programFolderPath = Environment.getExternalStorageDirectory().toString() + "/" + folderName /// Saves path to local folder
		//		val p = activityMain.getExternalFilesDir(null)
		//		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name + "", "external file dir path = $p")

		log(LOG_D, TAG, "makeFolder", "ext fil dir = " + activityMain.getExternalFilesDir(null))

		if(Environment.MEDIA_MOUNTED != state)
		/// If media storage is not mounted with phone
		{
			log(LOG_D, TAG, "makeFolder", "external storage is unavailable")
			return MEDIA_UNAVAILABLE
		}
		if(Environment.MEDIA_MOUNTED_READ_ONLY == state)
		/// Media is read only
		{
			log(LOG_D, TAG, "makeFolder", "external storage is read only.")
			return MEDIA_READ_ONLY
		}
		log(LOG_D, TAG, "makeFolder", "External storage is available and writeable")
		val folder = File(Environment.getExternalStorageDirectory(), folderName)
		var result: Int
		if(folder.exists())
		/// Folder already exists
		{
			log(LOG_D, TAG, "makeFolder", "folder exists:$folder")
			programFolderPath = folder.toString()
			putSettingString(programFolderPathString, programFolderPath)
			result = FOLDER_ALREADY_EXISTS
		}
		else
		/// Attempts to save folder
		{
			try {
				if(folder.mkdir()) {
					log(LOG_D, TAG, "makeFolder", "folder created:$folder")
					programFolderPath = folder.toString()
					putSettingString(programFolderPathString, programFolderPath)
					result = FOLDER_CREATED
				}
				else {
					log(LOG_D, TAG, "makeFolder", "create folder failed:$folder")
					result = MEDIA_UNKNOWN_ERROR
				}
			}
			catch (e: Exception) {
				log(LOG_D, TAG, "makeFolder", "create folder failed (exception):$folder")
				result = MEDIA_UNKNOWN_ERROR
				log(LOG_E, TAG, "makeFolder", e.toString())
			}
		}
		return result
	}

	/*=======================================================================================================*/
	private fun initDebugFiles() {
		debugFile = File(programDebugLogFilePath)
		try {
			debugFile!!.createNewFile()
			debugFilesInitiated = true
		}
		catch (e: IOException) {
			log(LOG_E, TAG, "initDebugFiles", e.toString())
			debugFilesInitiated = false
		}
	}

	/*=======================================================================================================*/
	private fun initProgramFolder() {
		log(LOG_I, TAG, "initProgramFolder", "start")
		val makeFolderResult = makeFolder(appDirectory) /// Attempts to make program folder on phone storage
		log(LOG_D, TAG, "initProgramFolder", "makeFolderResult = $makeFolderResult")
		if(makeFolderResult == FOLDER_ALREADY_EXISTS || makeFolderResult == FOLDER_CREATED) {
			programFolderInitialized = true /// Sets switch
			val debugFilename = "debugLog"
			programDebugLogFilePath = "$programFolderPath/$debugFilename.txt" /// Saves debug log filename & path
			if(!debugFilesInitiated) initDebugFiles() /// Inits the debug file for logging purposes
		}
		else
		/// Make folder attempt failed
		{
			log(LOG_D, TAG, "initProgramFolder", "Make program folder failed")
			programFolderInitialized = false
			programDebugLogFilePath = ""
			programFolderPath = ""
		}
		/// Puts all file path settings:
		putSettingString(programFolderPathString, programFolderPath)
		putSettingBoolean(programFolderInitializedString, programFolderInitialized)
		putSettingString(programDebugLogFilePathString, programDebugLogFilePath)
	}

	/*=======================================================================================================*/
	private fun putSettingBoolean(stringID: String, value: Boolean) {
		log(LOG_I, TAG, "putSettingBoolean", "Start: stringID = $stringID; value = $value")
		val spSettings = activityMain.getSharedPreferences(sharedPreferenceKey, MODE_PRIVATE)
		val editor = spSettings.edit()
		editor.putBoolean(stringID, value)
		editor.apply()
	}

	/*=======================================================================================================*/
	private fun putSettingString(stringID: String, value: String) {
		log(LOG_I, TAG, "putSettingString", "Start: stringID = $stringID; value = $value")
		val spSettings = activityMain.getSharedPreferences(sharedPreferenceKey, MODE_PRIVATE)
		val editor = spSettings.edit()
		editor.putString(stringID, value)
		editor.apply()
	}

	/*=======================================================================================================*/
	fun getAppDir(): String = appDirectory

	/*=======================================================================================================*/
	fun getProgramFolderPath(): String = programFolderPath

	/*=======================================================================================================*/
	private fun getSettings() {
		log(LOG_I, TAG, "getSettings", "Start")
		val spSettings = activityMain.getSharedPreferences(sharedPreferenceKey, MODE_PRIVATE)
		/// Settings:
		programFolderInitialized = spSettings.getBoolean(programFolderInitializedString, false)
		writePermission = spSettings.getBoolean(writePermissionString, false)
		writePermissionDeniedAlready = spSettings.getBoolean(writePermissionDeniedAlreadyString, false)
		/// Paths:
		programFolderPath = spSettings.getString(programFolderPathString, "")!!
		programDebugLogFilePath = spSettings.getString(programDebugLogFilePathString, "")!!
	}
	//</editor-fold>
}
