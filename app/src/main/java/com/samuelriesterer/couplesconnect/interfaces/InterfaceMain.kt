package com.samuelriesterer.couplesconnect.interfaces

import com.samuelriesterer.couplesconnect.general.FragStack

interface InterfaceMain {
	fun switchFragments(fragmentID: Int)
	fun getFragment(fragmentID: Int): FragStack
}