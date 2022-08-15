package com.samuelriesterer.couplesconnect.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.samuelriesterer.couplesconnect.R
import com.samuelriesterer.couplesconnect.data.Questions
import com.samuelriesterer.couplesconnect.databinding.FragmentCategoriesBinding
import com.samuelriesterer.couplesconnect.general.C
import com.samuelriesterer.couplesconnect.general.FragStack
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

		/* Category Click */
		binding.categoriesConversations.setOnClickListener { v ->
			v?.playSoundEffect(android.view.SoundEffectConstants.CLICK)
			interfaceMain.switchFragments(C.FRAG_SUBCATEGORY)
		}
		binding.categoriesDates.setOnClickListener { v ->
			v?.playSoundEffect(android.view.SoundEffectConstants.CLICK)
			Questions.makeDeckSingleCategory(C.CAT_DATE)
			interfaceMain.switchFragments(C.FRAG_QUESTION)
		}
		binding.categoriesIntimacy.setOnClickListener { v ->
			v?.playSoundEffect(android.view.SoundEffectConstants.CLICK)
			Questions.makeDeckSingleCategory(C.CAT_INTIMACY)
			interfaceMain.switchFragments(C.FRAG_QUESTION)
		}
		binding.categoriesSensual.setOnClickListener { v ->
			v?.playSoundEffect(android.view.SoundEffectConstants.CLICK)
			Questions.makeDeckSingleCategory(C.CAT_SENSUAL)
			interfaceMain.switchFragments(C.FRAG_QUESTION)
		}


		/* Question Click */
		binding.categoriesConversationsQuestion.setOnClickListener {
			if(binding.categoriesConversationsInfo.visibility == TextView.VISIBLE)
			binding.categoriesConversationsInfo.visibility = TextView.GONE
			else
				binding.categoriesConversationsInfo.visibility = TextView.VISIBLE
		}
		binding.categoriesDatesQuestion.setOnClickListener {
			if(binding.categoriesDatesInfo.visibility == TextView.VISIBLE)
				binding.categoriesDatesInfo.visibility = TextView.GONE
			else
				binding.categoriesDatesInfo.visibility = TextView.VISIBLE
		}
		binding.categoriesIntimacyQuestion.setOnClickListener {
			if(binding.categoriesIntimacyInfo.visibility == TextView.VISIBLE)
				binding.categoriesIntimacyInfo.visibility = TextView.GONE
			else
				binding.categoriesIntimacyInfo.visibility = TextView.VISIBLE
		}
		binding.categoriesSensualQuestion.setOnClickListener {
			if(binding.categoriesSensualInfo.visibility == TextView.VISIBLE)
				binding.categoriesSensualInfo.visibility = TextView.GONE
			else
				binding.categoriesSensualInfo.visibility = TextView.VISIBLE
		}
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
		binding.categoriesConversationsInfo.visibility = TextView.GONE
		binding.categoriesDatesInfo.visibility = TextView.GONE
		binding.categoriesIntimacyInfo.visibility = TextView.GONE
		binding.categoriesSensualInfo.visibility = TextView.GONE

		/* LISTENERS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

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