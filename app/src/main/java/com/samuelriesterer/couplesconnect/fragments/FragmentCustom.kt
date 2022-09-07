package com.samuelriesterer.couplesconnect.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.samuelriesterer.couplesconnect.R
import com.samuelriesterer.couplesconnect.data.*
import com.samuelriesterer.couplesconnect.databinding.FragmentCustomBinding
import com.samuelriesterer.couplesconnect.general.AdapterCustomList
import com.samuelriesterer.couplesconnect.general.C
import com.samuelriesterer.couplesconnect.general.Logger
import com.samuelriesterer.couplesconnect.interfaces.InterfaceMain

class FragmentCustom : Fragment() {
	private var _binding: FragmentCustomBinding? = null
	private val binding get() = _binding!!
	val TAG: String = "~*FRAGMENT_CUSTOM"
	private var customListAdapter: ExpandableListAdapter? = null

	private var titleList: List<String>? = null
	val categoryList: MutableList<Category> = mutableListOf()
	private lateinit var dataHashMap: HashMap<String, List<Subcategory>>

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
	}

	/*=======================================================================================================*/
	/* ON CREATE VIEW                                                                                        */
	/*=======================================================================================================*/
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		_binding = FragmentCustomBinding.inflate(inflater, container, false)
		val root: View = binding.root
		/* INITIALIZATION ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
		/* Variables */
		for(i in Data.categoryNames.indices) categoryList.add(Category(i, Data.categoryNames[i], Data.categoryIcons[i], Data.categoryColors[i]))
		dataHashMap = getCustomHashMap()
		Data.changedConfiguration = Data.currentConfiguration.copy()
		/* Setup Views */
		setupExpandableListView()
		interfaceMain.showActionBar()
		/* LISTENERS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
		binding.customExpand.setOnClickListener {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "Expand clicked")
			expandAllGroups(binding.customExpandableListView, customListAdapter!!)
		}
		binding.customCollapse.setOnClickListener {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "Collapse clicked")
			collapseAllGroups(binding.customExpandableListView, customListAdapter!!)
		}
		binding.customOk.setOnClickListener {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "OK clicked")
			// If configurations are not the same:
			if(!Data.currentConfiguration.compare(Data.changedConfiguration)) {
				// Save Setting:
				Data.currentConfiguration = Data.changedConfiguration.copy()
				DatabaseOps.insertConfiguration(Data.currentConfiguration)
			}
			interfaceMain.switchFragments(C.FRAG_QUESTION)
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
	/* EXPANDABLE LIST VIEW METHODS                                                                          */
	/*=======================================================================================================*/
	fun getCustomHashMap(): HashMap<String, List<Subcategory>> {
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		val hash: HashMap<String, List<Subcategory>> = hashMapOf()
		val subcategoryList: MutableList<Subcategory> = mutableListOf() // The list of all subcategories
		val childList: MutableList<List<Subcategory>> = mutableListOf() // A list of subcategories for each category
		/* Add the subcategories */
		for(i in Data.subcategoryNames.indices)
			subcategoryList.add(Subcategory(i, Data.subcategoryGroupings[i], Data.subcategoryNames[i], Data.subcategoryIcons[i]))
		/* Make a list of subcategories for each category */
		for(i in Data.categoryNames.indices) {
			val list: MutableList<Subcategory> = mutableListOf()
			// Go through all subcategories and if the subcategory belongs to this category then add:
			for(j in subcategoryList.indices) {
				if(subcategoryList[j].category == i) {
					list.add(subcategoryList[j])
				}
			}
			childList.add(list)
		}
		/* Build the HashMap */
		for(i in categoryList.indices) hash[categoryList[i].heading] = childList[i]

		return hash
	}

	/*=======================================================================================================*/
	fun collapseAllGroupsExceptSelected(elv: ExpandableListView, adapter: ExpandableListAdapter, selectedPos: Int) {
		for(i in 0 until adapter.groupCount) if(i != selectedPos) elv.collapseGroup(i) else elv.expandGroup(selectedPos)
	}

	/*=======================================================================================================*/
	fun collapseAllGroups(elv: ExpandableListView, adapter: ExpandableListAdapter) {
		Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		for(i in 0 until adapter.groupCount) elv.collapseGroup(i)
	}

	/*=======================================================================================================*/
	fun expandAllGroups(elv: ExpandableListView, adapter: ExpandableListAdapter) {
		Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		for(i in 0 until adapter.groupCount) elv.expandGroup(i)
	}

	/*=======================================================================================================*/
	private fun setupExpandableListView() {
		val expandableListView = binding.customExpandableListView
		customListAdapter = AdapterCustomList(requireContext(), categoryList, dataHashMap)
		expandableListView.setAdapter(customListAdapter)

		expandableListView.setOnGroupExpandListener { groupPosition ->
			println("group expanded")
		}

		expandableListView.setOnGroupCollapseListener { groupPosition ->
			println("group collapsed")
		}

		expandableListView.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
			println("group clicked")
			false
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