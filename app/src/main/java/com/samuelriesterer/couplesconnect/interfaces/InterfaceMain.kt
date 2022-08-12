package com.samuelriesterer.couplesconnect.interfaces

import com.samuelriesterer.couplesconnect.general.FragStack

interface InterfaceMain {
	fun switchFragments(fragStack: FragStack)
	fun getFragment(fragID: Int): FragStack
}