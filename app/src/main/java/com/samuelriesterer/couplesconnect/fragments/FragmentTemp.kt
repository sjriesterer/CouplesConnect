package com.samuelriesterer.couplesconnect.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.samuelriesterer.couplesconnect.adapters.AdapterTemp
import com.samuelriesterer.couplesconnect.data.Data
import com.samuelriesterer.couplesconnect.data.DatabaseOps
import com.samuelriesterer.couplesconnect.data.EntityQuestion
import com.samuelriesterer.couplesconnect.databinding.DialogAddQuestionBinding
import com.samuelriesterer.couplesconnect.databinding.FragmentTempBinding
import com.samuelriesterer.couplesconnect.general.C
import com.samuelriesterer.couplesconnect.general.Logger
import com.samuelriesterer.couplesconnect.general.Settings
import com.samuelriesterer.couplesconnect.interfaces.InterfaceMain

class FragmentTemp : Fragment() {
	private var _binding: FragmentTempBinding? = null
	private val binding get() = _binding!!
	val TAG: String = "~*FRAGMENT_TEMP"
	private lateinit var adapterTemp: AdapterTemp
	val fragmentID = C.FRAG_TEMP

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
		adapterTemp = AdapterTemp(requireContext(), Data.savedQuestions)
	}

	/*=======================================================================================================*/
	/* ON CREATE VIEW                                                                                        */
	/*=======================================================================================================*/
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		_binding = FragmentTempBinding.inflate(inflater, container, false)
		val root: View = binding.root
		/* INITIALIZATION ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
		/* Variables */
		//		val menuHost: MenuHost = requireActivity()
		//		menuHost.addMenuProvider(object : MenuProvider {
		//			override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
		//				menuInflater.inflate(R.menu.fragment_temp_menu, menu)
		//			}
		//
		//			override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
		//				when (menuItem.itemId) {
		//					R.id.action_share -> {
		//
		//					}
		//				}
		//				return true
		//			}
		//		})
		binding.tempList.apply {
			this.adapter = adapterTemp
		}
		/* Setup Views */
		interfaceMain.showActionBar()
		/* LISTENERS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
		binding.tempAdd.setOnClickListener {
			AdapterTemp.dialogAddQuestion(adapterTemp, requireContext(), null)
		}
		binding.tempShare.setOnClickListener {
		}
		binding.tempSort.setOnClickListener {
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
	fun dialogAddQuestion(position: Int?) {
		Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		/* Setup */
		val dialog = Dialog(requireContext())
		val bind: DialogAddQuestionBinding = DialogAddQuestionBinding.inflate(LayoutInflater.from(activity))
		dialog.setContentView(bind.root)
		bind.dialogAddQuestionEditText.requestFocus()
		if(position != null)
			bind.dialogAddQuestionEditText.setText(Data.savedQuestions[position].question)

		/* Methods */
		fun makeQuestion(id: Int) : EntityQuestion {
			return EntityQuestion(id, bind.dialogAddQuestionSpinnerCat.selectedItemPosition, bind.dialogAddQuestionSpinnerSub.selectedItemPosition, 0, bind.dialogAddQuestionEditText.text.toString())
		}

		fun addQuestion() {
			Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "adding question ${bind.dialogAddQuestionEditText.text.toString()}")
			val q = makeQuestion(Settings.settingsInt[C.SETTING_SAVED_QUESTION_ID])
			DatabaseOps.insertQuestion(q)
			Data.savedQuestions.add(0, q)
			Settings.settingsInt[C.SETTING_SAVED_QUESTION_ID]++
			Settings.saveSettingInt(Settings.settingsInt[C.SETTING_SAVED_QUESTION_ID], C.SETTING_SAVED_QUESTION_ID)
			adapterTemp.notifyDataSetChanged()
		}

		fun saveQuestion() {
			val q = makeQuestion(Data.savedQuestions[position!!].id)
			Data.savedQuestions[position] = q
			DatabaseOps.insertQuestion(q)
			adapterTemp.notifyDataSetChanged()
		}
		/* Listeners */
		bind.dialogAddQuestionDel.setOnClickListener {
			if(position == null) {
				bind.dialogAddQuestionEditText.text.clear()
			}
			else {
				bind.dialogAddQuestionEditText.text.clear()
				DatabaseOps.deleteQuestion(Data.savedQuestions[position].id)
				Data.savedQuestions.removeAt(position)
				adapterTemp.notifyDataSetChanged()
			}
		}
		bind.dialogAddQuestionNext.setOnClickListener {
			if(position == null) {
				if(bind.dialogAddQuestionEditText.text.isNotEmpty())
					addQuestion()
				bind.dialogAddQuestionEditText.text.clear()
			}
			else {
				saveQuestion()
			}
		}
		bind.dialogAddQuestionOk.setOnClickListener {
			if(bind.dialogAddQuestionEditText.text.isNotEmpty()) {
				if(position == null)
					addQuestion()
				else
					saveQuestion()
			}
			dialog.dismiss()
		}
		dialog.show()
	}

	/*=======================================================================================================*/
	fun addQuestion2(q: EntityQuestion) {
		Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		Data.savedQuestions.add(0, q)
		adapterTemp.notifyDataSetChanged()
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