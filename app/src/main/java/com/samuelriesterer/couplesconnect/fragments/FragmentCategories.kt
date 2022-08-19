package com.samuelriesterer.couplesconnect.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.samuelriesterer.couplesconnect.R
import com.samuelriesterer.couplesconnect.data.Data
import com.samuelriesterer.couplesconnect.databinding.FragmentCategoriesBinding
import com.samuelriesterer.couplesconnect.general.C
import com.samuelriesterer.couplesconnect.general.Logger
import com.samuelriesterer.couplesconnect.interfaces.InterfaceMain

class FragmentCategories : Fragment() {
	private var _binding: FragmentCategoriesBinding? = null
	private val binding get() = _binding!!
	val TAG: String = "~*FRAGMENT_CATEGORIES"
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
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		super.onCreate(savedInstanceState)
		//		setHasOptionsMenu(false)
	}

	/*=======================================================================================================*/
	/* ON CREATE VIEW                                                                                        */
	/*=======================================================================================================*/
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		_binding = FragmentCategoriesBinding.inflate(inflater, container, false)
		val root: View = binding.root
		/* INITIALIZATION ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
		/* Variables */

		/* Setup Views */

		/* LISTENERS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
		/* Category Click */
		binding.categoriesConversations.setOnClickListener { v ->
			v?.playSoundEffect(android.view.SoundEffectConstants.CLICK)
			interfaceMain.switchFragments(C.FRAG_SUBCATEGORY)
		}
		binding.categoriesDates.setOnClickListener { v ->
			v?.playSoundEffect(android.view.SoundEffectConstants.CLICK)
			Data.makeDeckSingleCategory(C.CAT_DATE)
			interfaceMain.switchFragments(C.FRAG_QUESTION)
		}
		binding.categoriesIntimacy.setOnClickListener { v ->
			v?.playSoundEffect(android.view.SoundEffectConstants.CLICK)
			Data.makeDeckSingleCategory(C.CAT_INTIMACY)
			interfaceMain.switchFragments(C.FRAG_QUESTION)
		}
		binding.categoriesSensual.setOnClickListener { v ->
			v?.playSoundEffect(android.view.SoundEffectConstants.CLICK)
			Data.makeDeckSingleCategory(C.CAT_SENSUAL)
			interfaceMain.switchFragments(C.FRAG_QUESTION)
		}

		/* Question Click */
		binding.categoriesConversationsQuestion.setOnClickListener {
			dialogInfo(getString(R.string.category0), getString(R.string.category_info0))
		}
		binding.categoriesDatesQuestion.setOnClickListener {
			dialogInfo(getString(R.string.category1), getString(R.string.category_info1))
		}
		binding.categoriesIntimacyQuestion.setOnClickListener {
			dialogInfo(getString(R.string.category2), getString(R.string.category_info2))
		}
		binding.categoriesSensualQuestion.setOnClickListener {
			dialogInfo(getString(R.string.category3), getString(R.string.category_info3))
		}
		return root
	}
	/*=======================================================================================================*/
	/* ON VIEW CREATED                                                                                       */
	/*=======================================================================================================*/
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		super.onViewCreated(view, savedInstanceState)
	}

	/*=======================================================================================================*/
	fun dialogInfo(title: String, message: String) {
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, ": start")
		val builder = AlertDialog.Builder(requireContext())

		builder.setTitle(title)
		builder.setMessage(message)
		builder.setPositiveButton(getString(R.string.ok)) { dialog, _ ->
			dialog.dismiss()
		}
		builder.show()
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