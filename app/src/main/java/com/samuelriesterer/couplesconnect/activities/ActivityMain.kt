package com.samuelriesterer.couplesconnect.activities

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.samuelriesterer.couplesconnect.R
import com.samuelriesterer.couplesconnect.data.Questions
import com.samuelriesterer.couplesconnect.databinding.ActivityMainBinding
import com.samuelriesterer.couplesconnect.fragments.*
import com.samuelriesterer.couplesconnect.general.C
import com.samuelriesterer.couplesconnect.general.FragStack
import com.samuelriesterer.couplesconnect.general.Logger
import com.samuelriesterer.couplesconnect.general.Settings
import com.samuelriesterer.couplesconnect.interfaces.InterfaceMain
import java.util.*


class ActivityMain : AppCompatActivity(), InterfaceMain, NavigationView.OnNavigationItemSelectedListener {
	private lateinit var appBarConfiguration: AppBarConfiguration
	private lateinit var binding: ActivityMainBinding
	lateinit var drawerLayout: DrawerLayout

	private lateinit var fragmentStack: Stack<FragStack>
	lateinit var navView: NavigationView
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
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		/* Setup Settings & Data */
		super.onCreate(savedInstanceState)
		Settings.setup(this)
		Questions.setup(this)
		fragmentStack = Stack()

		/* Setup View & Navigation */
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		setSupportActionBar(binding.appBarMain.toolbar)
		drawerLayout = binding.drawerLayout
		navView = binding.navView
		val navController = findNavController(R.id.nav_host_fragment_content_main)

		// Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
		appBarConfiguration = AppBarConfiguration(setOf(
			R.id.nav_home, R.id.nav_categories, R.id.nav_subcategories, R.id.nav_questions, R.id.nav_custom), drawerLayout)

		setupActionBarWithNavController(navController, appBarConfiguration)
		navView.setupWithNavController(navController)
		navView.setNavigationItemSelectedListener(this)
		fragmentStack.add(FragStack(C.FRAG_HOME, FragmentHome()))
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
	/* ON NAVIGATION ITEM SELECTED                                                                           */
	/*=======================================================================================================*/
		override fun onNavigationItemSelected(item: MenuItem): Boolean {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
			// Handle navigation view item clicks here.
			when (item.itemId) {
				R.id.nav_home -> {
					Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "fragment home clicked: ")
					switchFragments(C.FRAG_HOME)
				}
				R.id.nav_categories -> {
					Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "fragment category clicked: ")
					switchFragments(C.FRAG_CATEGORY)
				}
				R.id.nav_subcategories -> {
					Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "fragment home clicked: ")
					switchFragments(C.FRAG_SUBCATEGORY)
				}
				R.id.nav_questions -> {
					Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "fragment home clicked: ")
					switchFragments(C.FRAG_QUESTION)
				}
				R.id.nav_custom -> {
					Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "fragment home clicked: ")
					switchFragments(C.FRAG_CUSTOM)
				}

			}
			drawerLayout.closeDrawer(GravityCompat.START)
			return true
		}
	/*=======================================================================================================*/
	/* FRAGMENT METHODS                                                                                      */
	/*=======================================================================================================*/
	//<editor-fold desc="Fragment Methods">
	fun printFragmentStack() {
		println("FragmentStack contents:")
		for(i in fragmentStack.indices)
			println("FragmentStack[$i] = ${Settings.fragmentNames[fragmentStack[i].id]}")
	}
	/*=======================================================================================================*/
	override fun onBackPressed() {
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")

		if(drawerLayout.isDrawerOpen(GravityCompat.START)) // Drawer is open
			drawerLayout.closeDrawer(GravityCompat.START) // Close drawer
		else {
			if(fragmentStack.size == 1) {
				super.onBackPressed()
			}
			else if(fragmentStack.size > 1) {
				fragmentStack.pop()
				switchFragments(fragmentStack.lastElement().id)
			}
		}

	}
	/*=======================================================================================================*/
	override fun getFragment(fragmentID: Int): FragStack {
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start, fragmentID = $fragmentID : ${Settings.fragmentNames[fragmentID]}")
		when (fragmentID) {
			C.FRAG_HOME -> {
				return FragStack(fragmentID, FragmentHome())
			}
			C.FRAG_CATEGORY -> {
				return FragStack(fragmentID, FragmentCategories())
			}
			C.FRAG_SUBCATEGORY -> {
				return FragStack(fragmentID, FragmentSubcategories())
			}
			C.FRAG_QUESTION -> {
				return FragStack(fragmentID, FragmentQuestion())
			}
			C.FRAG_CUSTOM -> {
				return FragStack(fragmentID, FragmentCustom())
			}
		}
		return FragStack(fragmentID, FragmentHome())
	}

	/*=======================================================================================================*/
	override fun switchFragments(fragmentID: Int) {
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		// Get the requested fragment:
		val fragStack = getFragment(fragmentID)

		// Remove fragment from stack if it is there previously:
		for(i in (fragmentStack.size - 1) downTo 0) {
			if(fragmentStack[i].id == fragStack.id) {
				fragmentStack.removeAt(i)
				break
			}
		}
		// Push fragment to top of stack:
		fragmentStack.push(fragStack)
		Settings.currentFragment = fragStack.id

		// Remove all fragments from container:
//		for(fragment in supportFragmentManager.fragments) {
//			if(fragment != null) {
//				supportFragmentManager.beginTransaction().remove(fragment).commit()
//			}
//		}
		// Set navigation drawer checked item:
//		when (fragStack.id) {
//			C.FRAG_HOME -> {
//				navView.setCheckedItem(R.id.nav_home)
//			}
//			C.FRAG_CATEGORY -> {
//				navView.setCheckedItem(R.id.nav_categories)
//			}
//			C.FRAG_SUBCATEGORY -> {
//				navView.setCheckedItem(R.id.nav_subcategories)
//			}
//			C.FRAG_QUESTION -> {
//				navView.setCheckedItem(R.id.nav_questions)
//			}
//		}
		// Save backstack state:
		//		Settings.saveState.fragmentList = fragStackToIntArray(fragmentStack)
		//		DatabaseOps.databaseSaveSaveState(Settings.saveState)

		// Switch fragments:
		val transition = this.supportFragmentManager.beginTransaction()
//		transition.add(R.id.nav_host_fragment_content_main, fragStack.fragment).addToBackStack(Settings.fragmentNames[fragStack.id]).commit()
		transition.add(R.id.nav_host_fragment_content_main, fragStack.fragment).commit()
		printFragmentStack()

	}

	/*=======================================================================================================*/
	fun fragStackToIntArray(stack: Stack<FragStack>): MutableList<Int> {
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start, stack.size = ${stack.size}")
		val list: MutableList<Int> = mutableListOf()
		for(i in 0 until stack.size) {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "fragStack id $i = ${stack[i].id}")
			when (stack[i].id) {
				C.FRAG_HOME -> {
					list.add(C.FRAG_HOME)
				}
				C.FRAG_CATEGORY -> {
					list.add(C.FRAG_CATEGORY)
				}
				C.FRAG_SUBCATEGORY -> {
					list.add(C.FRAG_SUBCATEGORY)
				}
				C.FRAG_QUESTION -> {
					list.add(C.FRAG_QUESTION)
				}
				C.FRAG_CUSTOM -> {
					list.add(C.FRAG_CUSTOM)
				}
			}
		}
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, " ${list}")
		return list
	}

	/*=======================================================================================================*/
	fun intArrayToFragStack(list: MutableList<Int>): Stack<FragStack> {
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		val stack: Stack<FragStack> = Stack()
		var i = 0
		while(i < list.size) {
			when (list[i]) {
				C.FRAG_HOME -> {
					stack.add(FragStack(C.FRAG_HOME, FragmentHome()))
				}
				C.FRAG_CATEGORY -> {
					stack.add(FragStack(C.FRAG_CATEGORY, FragmentCategories()))
				}
				C.FRAG_SUBCATEGORY -> {
					stack.add(FragStack(C.FRAG_SUBCATEGORY, FragmentSubcategories()))
				}
				C.FRAG_QUESTION -> {
					i++
					stack.add(FragStack(C.FRAG_QUESTION, FragmentQuestion()))
				}
				C.FRAG_CUSTOM -> {
					i++
					stack.add(FragStack(C.FRAG_CUSTOM, FragmentCustom()))
				}
			}
			i++
		}

		return stack
	}
	/*=======================================================================================================*/
	/* SHARED PREFERENCES                                                                                    */
	/*=======================================================================================================*/
	/*=======================================================================================================*/
	/* SHARED PREFERENCES METHODS                                                                            */
	/*=======================================================================================================*/
	//<editor-fold desc="Shared Preference Methods">
	/*=======================================================================================================*/
	override fun putSettingBoolean(key: String, stringID: String, value: Boolean) {
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start: $stringID = $value")
		val spSettings = getSharedPreferences(key, Context.MODE_PRIVATE)
		val editor = spSettings.edit()
		editor.putBoolean(stringID, value)
		editor.apply()
	}

	/*=======================================================================================================*/
	override fun putSettingInt(key: String, stringID: String, value: Int) {
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start: $stringID = $value")
		val spSettings = getSharedPreferences(key, Context.MODE_PRIVATE)
		val editor = spSettings.edit()
		editor.putInt(stringID, value)
		editor.apply()
	}

	//</editor-fold>


	//</editor-fold>
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
		//		Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		super.onPause()
	}

	/*=======================================================================================================*/
	override fun onResume() {
		//		Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		super.onResume()
	}

	/*=======================================================================================================*/
	override fun onStart() {
		//		Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		super.onStart()
	}

	/*=======================================================================================================*/
	override fun onStop() {
		//		Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		super.onStop()
	}

	/*=======================================================================================================*/
	override fun onDestroy() {
		//		Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		super.onDestroy()
	}
	/*=======================================================================================================*/
	//</editor-fold>
}