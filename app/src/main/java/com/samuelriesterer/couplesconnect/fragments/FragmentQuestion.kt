package com.samuelriesterer.couplesconnect.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.samuelriesterer.couplesconnect.databinding.FragmentQuestionBinding
import com.samuelriesterer.couplesconnect.general.C
import com.samuelriesterer.couplesconnect.general.FragStack
import com.samuelriesterer.couplesconnect.general.Logger
import com.samuelriesterer.couplesconnect.interfaces.InterfaceMain

class FragmentQuestion : Fragment() {
	private var _binding: FragmentQuestionBinding? = null
	private val binding get() = _binding!!
	val TAG: String = "~*FRAGMENT_QUESTION"

	/*=======================================================================================================*/
	/* INTERFACE                                                                                             */
	/*=======================================================================================================*/
	lateinit var interfaceMain: InterfaceMain

	/*=======================================================================================================*/
	/* ON ATTACH                                                                                             */
	/*=======================================================================================================*/
	override fun onAttach(context: Context) {
		super.onAttach(context)
		try {
			interfaceMain = context as InterfaceMain
		}
		catch (e: ClassCastException) {
			throw ClassCastException("$context must implement interfaceMain")
		}
	}
	/*=======================================================================================================*/
	/* ON CREATE                                                                                             */
	/*=======================================================================================================*/
	override fun onCreate(savedInstanceState: Bundle?) {
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start: ")
		super.onCreate(savedInstanceState)
//		setHasOptionsMenu(false)
	}
	/*=======================================================================================================*/
	/* ON CREATE VIEW                                                                                        */
	/*=======================================================================================================*/
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start: ")
		_binding = FragmentQuestionBinding.inflate(inflater, container, false)
		val root: View = binding.root
		return root
	}

	/*=======================================================================================================*/
	/* ON VIEW CREATED                                                                                       */
	/*=======================================================================================================*/
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
//		super.onViewCreated(view, savedInstanceState)
		/* INITIALIZATION ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
		/* Variables */

//		val formatter = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
		/* Setup Views */


		/* LISTENERS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
		binding.questionsCategory.setOnClickListener {
			Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "clicked category in question")
			interfaceMain.switchFragments(FragStack(C.FRAG_CATEGORY, FragmentCategories()))
		}
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
		_binding = null
	}
	/*=======================================================================================================*/
	//</editor-fold>
}