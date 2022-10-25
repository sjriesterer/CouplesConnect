package com.samuelriesterer.couplesconnect.activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.samuelriesterer.couplesconnect.R
import com.samuelriesterer.couplesconnect.data.DatabaseApp
import com.samuelriesterer.couplesconnect.data.Data
import com.samuelriesterer.couplesconnect.databinding.ActivityMainBinding
import com.samuelriesterer.couplesconnect.fragments.*
import com.samuelriesterer.couplesconnect.general.C
import com.samuelriesterer.couplesconnect.general.FragStack
import com.samuelriesterer.couplesconnect.general.Logger
import com.samuelriesterer.couplesconnect.general.Settings
import com.samuelriesterer.couplesconnect.interfaces.InterfaceMain
import java.util.*

//TODO Settings page
//TODO persist groups expanded in custom page
//TODO delete fragSTack (make just a stack of fragments)

class ActivityMain : AppCompatActivity(), InterfaceMain {
	private lateinit var binding: ActivityMainBinding
	private lateinit var fragmentStack: Stack<FragStack>
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
		databaseApp = DatabaseApp.getDatabase(this)!! /// Inits the database (Call this first)
		Settings.setup(this) // Call this second
		Settings.databaseFilePath = this.getDatabasePath(Settings.databaseFilename).absolutePath
		Data.setup(this)
		fragmentStack = Stack()

		/* Setup View & Navigation */
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		switchFragments(C.FRAG_CATEGORY)
		Settings.settingsBoolean[C.SETTING_APP_INITIALIZED] = true
		Settings.saveSetting(Settings.settingsBoolean[C.SETTING_APP_INITIALIZED], C.SETTING_APP_INITIALIZED)
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

//		if(drawerLayout.isDrawerOpen(GravityCompat.START)) // Drawer is open
//			drawerLayout.closeDrawer(GravityCompat.START) // Close drawer
//		else {
			if(fragmentStack.size == 1) {
				super.onBackPressed()
			}
			else if(fragmentStack.size > 1) {
				fragmentStack.pop()
				switchFragments(fragmentStack.lastElement().id)
			}
//		}

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
			C.FRAG_SETTINGS -> {
				return FragStack(fragmentID, FragmentSettings())
			}
			C.FRAG_DECK -> {
				return FragStack(fragmentID, FragmentDeck())
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

		// Switch fragments:
		val transition = this.supportFragmentManager.beginTransaction()
		transition.add(R.id.container, fragStack.fragment).commit()
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
				C.FRAG_SETTINGS -> {
					list.add(C.FRAG_SETTINGS)
				}
				C.FRAG_DECK -> {
					list.add(C.FRAG_DECK)
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
				C.FRAG_SETTINGS -> {
					i++
					stack.add(FragStack(C.FRAG_SETTINGS, FragmentCustom()))
				}
				C.FRAG_DECK -> {
					i++
					stack.add(FragStack(C.FRAG_DECK, FragmentCustom()))
				}
			}
			i++
		}

		return stack
	}
	/*=======================================================================================================*/
	/* SHARED PREFERENCES                                                                                    */
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
	/*=======================================================================================================*/
	/* COMPANION OBJECTS                                                                                     */
	/*=======================================================================================================*/
		companion object {
		lateinit var databaseApp: DatabaseApp /// The master database
		}
	/*=======================================================================================================*/
	/* OVERRIDE LIFECYCLE METHODS                                                                            */
	/*=======================================================================================================*/
	//<editor-fold desc="Override Lifecycle Methods">
	override fun onPause() {
		Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		super.onPause()
	}

	/*=======================================================================================================*/
	override fun onResume() {
		Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		super.onResume()
	}

	/*=======================================================================================================*/
	override fun onStart() {
		Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		super.onStart()
	}

	/*=======================================================================================================*/
	override fun onStop() {
		Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		super.onStop()
	}

	/*=======================================================================================================*/
	override fun onDestroy() {
		Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		super.onDestroy()
	}
	/*=======================================================================================================*/
	//</editor-fold>
}