package com.samuelriesterer.couplesconnect.activities

import android.os.Bundle
import android.view.Menu
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.samuelriesterer.couplesconnect.R
import com.samuelriesterer.couplesconnect.data.Questions
import com.samuelriesterer.couplesconnect.databinding.ActivityMainBinding
import com.samuelriesterer.couplesconnect.general.C
import com.samuelriesterer.couplesconnect.general.Logger
import com.samuelriesterer.couplesconnect.general.Settings

class ActivityMain : AppCompatActivity() {
	private lateinit var appBarConfiguration: AppBarConfiguration
	private lateinit var binding: ActivityMainBinding
	lateinit var logger: Logger
	val TAG: String = "~*ACTIVITY_MAIN"

	/*=======================================================================================================*/
	/* ON CREATE                                                                                             */
	/*=======================================================================================================*/
	override fun onCreate(savedInstanceState: Bundle?) {
		/* INITIALIZATION ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
		/* Setup Logger and storage */
		logger = Logger(this, Settings.sharedPreferenceKey, Settings.appDirectory, false)
		Logger.deleteLog()
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name + ": start: ")

		/* Setup Settings and SharedPreferences */
		super.onCreate(savedInstanceState)
		Settings.setup(this)
		Questions.setup(this)

		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		setSupportActionBar(binding.appBarMain.toolbar)

		val drawerLayout: DrawerLayout = binding.drawerLayout
		val navView: NavigationView = binding.navView
		val navController = findNavController(R.id.nav_host_fragment_content_main)
		// Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
		appBarConfiguration = AppBarConfiguration(setOf(
			R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
		setupActionBarWithNavController(navController, appBarConfiguration)
		navView.setupWithNavController(navController)
	}

	/*=======================================================================================================*/
	/* ON CREATE OPTIONS                                                                                     */
	/*=======================================================================================================*/
	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		// Inflate the menu; this adds items to the action bar if it is present.
		menuInflater.inflate(R.menu.main, menu)
		return true
	}

	/*=======================================================================================================*/
	/* ON SUPPORT NAVIGATE UP                                                                                */
	/*=======================================================================================================*/
	override fun onSupportNavigateUp(): Boolean {
		val navController = findNavController(R.id.nav_host_fragment_content_main)
		return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
	}

	/*=======================================================================================================*/
	/* COMPANION OBJECTS                                                                                     */
	/*=======================================================================================================*/
	//<editor-fold desc="Companion Objects">
	//	companion object {}

	//</editor-fold>
	/*=======================================================================================================*/
	/* OVERRIDE LIFECYCLE METHODS                                                                            */
	/*=======================================================================================================*/
	//<editor-fold desc="Override Lifecycle Methods">
	override fun onPause() {
//		Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name + ": start")
		super.onPause()
	}

	/*=======================================================================================================*/
	override fun onResume() {
//		Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name + ": start")
		super.onResume()
	}

	/*=======================================================================================================*/
	override fun onStart() {
//		Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name + ": start")
		super.onStart()
	}

	/*=======================================================================================================*/
	override fun onStop() {
//		Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name + ": start")
		super.onStop()
	}

	/*=======================================================================================================*/
	override fun onDestroy() {
//		Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name + ": start")
		super.onDestroy()
	}
	/*=======================================================================================================*/
	//</editor-fold>
}