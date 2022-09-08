package com.samuelriesterer.couplesconnect.fragments

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.samuelriesterer.couplesconnect.R
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
	var currentSortOrderIDAsc = false
	var currentSortOrderAZAsc = true

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
		sortSaveQuestions(Settings.settingsInt[C.SETTING_CURRENT_SORT_ORDER])
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
			showPopupSortMenu(binding.tempSort)
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
	fun showPopupSortMenu(v: View?) {
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		val popup = PopupMenu(requireContext(), v!!)
		popup.inflate(R.menu.popup_sort)
		// Make popup title black:
		val popupTitle = SpannableString(getString(R.string.sort_by) + ":")
		popupTitle.setSpan(ForegroundColorSpan(Color.BLACK), 0, popupTitle.length, 0)
		popupTitle.setSpan(StyleSpan(Typeface.BOLD), 0, popupTitle.length, 0)
		popup.menu.findItem(R.id.sort_title).title = popupTitle

		popup.setOnMenuItemClickListener { item ->
			when (item.itemId) {
				R.id.sort_cat -> sortSaveQuestions(0)
				R.id.sort_sub -> sortSaveQuestions(1)
				R.id.sort_id -> sortSaveQuestions(2)
				R.id.sort_az -> sortSaveQuestions(3)
			}
			adapterTemp.notifyDataSetChanged()

			true
		}
		popup.show()
	}

	/*=======================================================================================================*/
	fun sortSaveQuestions(method: Int) {
		Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		when(method) {
			0 -> Data.savedQuestions.sortBy { it.category }
			1 -> Data.savedQuestions.sortBy { it.subcategory }
			2 -> {
				if(currentSortOrderIDAsc)
					Data.savedQuestions.sortByDescending { it.id }
				else
					Data.savedQuestions.sortBy { it.id }
				currentSortOrderAZAsc = !currentSortOrderIDAsc
			}
			3 -> {
				if(currentSortOrderAZAsc)
					Data.savedQuestions.sortByDescending { it.question }
				else
					Data.savedQuestions.sortBy { it.question }
				currentSortOrderAZAsc = !currentSortOrderAZAsc
			}
		}
		Settings.saveSetting(method, C.SETTING_CURRENT_SORT_ORDER)
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