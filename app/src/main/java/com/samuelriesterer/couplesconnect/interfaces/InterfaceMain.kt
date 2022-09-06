package com.samuelriesterer.couplesconnect.interfaces

import com.samuelriesterer.couplesconnect.general.FragStack

interface InterfaceMain {
	fun switchFragments(fragmentID: Int)
	fun getFragment(fragmentID: Int): FragStack
	fun putSettingBoolean(key: String, stringID: String, value: Boolean)
	fun putSettingInt(key: String, stringID: String, value: Int)
	fun disableNavDrawer()
	fun enableNavDrawer()
	fun hideActionBar()
	fun showActionBar()
}