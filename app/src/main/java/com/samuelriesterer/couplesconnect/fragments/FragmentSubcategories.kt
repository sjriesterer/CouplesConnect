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
import com.samuelriesterer.couplesconnect.databinding.FragmentSubcategoriesBinding
import com.samuelriesterer.couplesconnect.general.C
import com.samuelriesterer.couplesconnect.general.Logger
import com.samuelriesterer.couplesconnect.interfaces.InterfaceMain

class FragmentSubcategories : Fragment() {
	private var _binding: FragmentSubcategoriesBinding? = null
	private val binding get() = _binding!!
	val TAG: String = "~*FRAGMENT_SUBCATEGORIES"
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
		_binding = FragmentSubcategoriesBinding.inflate(inflater, container, false)
		val root: View = binding.root
		/* INITIALIZATION ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
		/* Variables */

		/* Setup Views */
		interfaceMain.showActionBar()

		/* LISTENERS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
		/* Subcategory Click */
		binding.subcategoryConversations.subcategoriesConversationsLife.setOnClickListener {v ->
			v?.playSoundEffect(android.view.SoundEffectConstants.CLICK)
			Data.makeDeckSingleSubcategory(C.SUB_LIFE)
			interfaceMain.switchFragments(C.FRAG_QUESTION)
		}
		binding.subcategoryConversations.subcategoriesConversationsLove.setOnClickListener {v ->
			v?.playSoundEffect(android.view.SoundEffectConstants.CLICK)
			Data.makeDeckSingleSubcategory(C.SUB_LOVE)
			interfaceMain.switchFragments(C.FRAG_QUESTION)
		}
		binding.subcategoryConversations.subcategoriesConversationsRelationships.setOnClickListener {v ->
			v?.playSoundEffect(android.view.SoundEffectConstants.CLICK)
			Data.makeDeckSingleSubcategory(C.SUB_RELATIONSHIPS)
			interfaceMain.switchFragments(C.FRAG_QUESTION)
		}
		binding.subcategoryConversations.subcategoriesConversationsSelf.setOnClickListener {v ->
			v?.playSoundEffect(android.view.SoundEffectConstants.CLICK)
			Data.makeDeckSingleSubcategory(C.SUB_SELF)
			interfaceMain.switchFragments(C.FRAG_QUESTION)
		}
		binding.subcategoryConversations.subcategoriesConversationsReligion.setOnClickListener {v ->
			v?.playSoundEffect(android.view.SoundEffectConstants.CLICK)
			Data.makeDeckSingleSubcategory(C.SUB_RELIGION)
			interfaceMain.switchFragments(C.FRAG_QUESTION)
		}
		binding.subcategoryConversations.subcategoriesConversationsGovernment.setOnClickListener {v ->
			v?.playSoundEffect(android.view.SoundEffectConstants.CLICK)
			Data.makeDeckSingleSubcategory(C.SUB_GOVERNMENT)
			interfaceMain.switchFragments(C.FRAG_QUESTION)
		}

		/* Question Click */
		binding.subcategoryConversations.subcategoriesLifeQuestion.setOnClickListener {v ->
			v?.playSoundEffect(android.view.SoundEffectConstants.CLICK)
			dialogInfo(getString(R.string.subcategory_info0), "")
		}
		binding.subcategoryConversations.subcategoriesLoveQuestion.setOnClickListener {v ->
			v?.playSoundEffect(android.view.SoundEffectConstants.CLICK)
			dialogInfo(getString(R.string.subcategory_info1), "")
		}
		binding.subcategoryConversations.subcategoriesRelationshipsQuestion.setOnClickListener {v ->
			v?.playSoundEffect(android.view.SoundEffectConstants.CLICK)
			dialogInfo(getString(R.string.subcategory_info2), "")
		}
		binding.subcategoryConversations.subcategoriesSelfQuestion.setOnClickListener {v ->
			v?.playSoundEffect(android.view.SoundEffectConstants.CLICK)
			dialogInfo(getString(R.string.subcategory_info3), "")
		}
		binding.subcategoryConversations.subcategoriesReligionQuestion.setOnClickListener {v ->
			v?.playSoundEffect(android.view.SoundEffectConstants.CLICK)
			dialogInfo(getString(R.string.subcategory_info4), "")
		}
		binding.subcategoryConversations.subcategoriesGovernmentQuestion.setOnClickListener {v ->
			v?.playSoundEffect(android.view.SoundEffectConstants.CLICK)
			dialogInfo(getString(R.string.subcategory_info5), "")
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
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
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