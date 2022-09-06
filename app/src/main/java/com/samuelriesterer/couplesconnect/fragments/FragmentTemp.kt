package com.samuelriesterer.couplesconnect.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import com.samuelriesterer.couplesconnect.R
import com.samuelriesterer.couplesconnect.adapters.AdapterTemp
import com.samuelriesterer.couplesconnect.data.Data
import com.samuelriesterer.couplesconnect.databinding.FragmentTempBinding
import com.samuelriesterer.couplesconnect.general.C
import com.samuelriesterer.couplesconnect.general.Logger
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

		/* LISTENERS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
		binding.tempAdd.setOnClickListener {

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
}