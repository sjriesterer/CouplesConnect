package com.samuelriesterer.couplesconnect.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.samuelriesterer.couplesconnect.data.Category
import com.samuelriesterer.couplesconnect.data.Data
import com.samuelriesterer.couplesconnect.data.Subcategory
import com.samuelriesterer.couplesconnect.databinding.GroupCustomBinding
import com.samuelriesterer.couplesconnect.databinding.RowCustomBinding
import com.samuelriesterer.couplesconnect.general.C
import com.samuelriesterer.couplesconnect.general.Logger

class AdapterCustomList internal constructor(private val context: Context, private val categoryList: List<Category>, private val dataHashMap: HashMap<String, List<Subcategory>>) : BaseExpandableListAdapter() {
	val TAG: String = "~*ADAPTER_CUSTOM_LIST"
	private val inflater: LayoutInflater = LayoutInflater.from(context)
	private lateinit var groupBinding: GroupCustomBinding
	private lateinit var itemBinding: RowCustomBinding

	/*=======================================================================================================*/
	/* GROUP METHODS                                                                                         */
	/*=======================================================================================================*/
	override fun getGroup(categoryPosition: Int): Category {
		return this.categoryList[categoryPosition]
	}

	/*=======================================================================================================*/
	override fun getGroupCount(): Int {
		return this.categoryList.size
	}

	/*=======================================================================================================*/
	override fun getGroupId(categoryPosition: Int): Long {
		return categoryPosition.toLong()
	}

	/*=======================================================================================================*/
	override fun getGroupView(categoryPosition: Int, isExpanded: Boolean, view: View?, parent: ViewGroup): View {
		var convertView = view
		val holder: GroupViewHolder
		/* Setup View */
		if(convertView == null) {
			groupBinding = GroupCustomBinding.inflate(inflater)
			convertView = groupBinding.root
			holder = GroupViewHolder()
			holder.layout = groupBinding.customElvGroup
			holder.label = groupBinding.customElvGroupText
			holder.icon = groupBinding.customElvGroupIcon
			holder.checkbox = groupBinding.customElvGroupCheckbox
			convertView.tag = holder
		}
		else {
			holder = convertView.tag as GroupViewHolder
		}
		val category = getGroup(categoryPosition)

		/* Setup Views */
		holder.layout!!.setBackgroundColor(Data.categories[categoryPosition].color)
		holder.icon!!.setImageDrawable(category.icon)
		holder.label!!.text = category.heading
		holder.checkbox!!.isChecked = Data.changedConfiguration.categoriesTurnedOn[categoryPosition]

		/* Listeners */
		holder.checkbox!!.setOnClickListener{
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "category checked : ${Data.categories[categoryPosition].heading} ${Data.changedConfiguration.categoriesTurnedOn[categoryPosition]}")
			Data.changedConfiguration.categoriesTurnedOn[categoryPosition] = holder.checkbox!!.isChecked
			markAllSubcategories(categoryPosition, Data.changedConfiguration.categoriesTurnedOn[categoryPosition])
			notifyDataSetChanged()
			Data.changedConfiguration.print()
		}
		return convertView
	}

	/*=======================================================================================================*/
	/* CHILD METHODS                                                                                         */
	/*=======================================================================================================*/
	override fun getChild(categoryPosition: Int, subcategoryPosition: Int): Subcategory {
		return this.dataHashMap[this.categoryList[categoryPosition].heading]!![subcategoryPosition]
	}

	/*=======================================================================================================*/
	override fun getChildId(categoryPosition: Int, expandedListPosition: Int): Long {
		return expandedListPosition.toLong()
	}

	/*=======================================================================================================*/
	override fun getChildView(listPosition: Int, expandedListPosition: Int, isLastChild: Boolean, view: View?, parent: ViewGroup): View {
		var convertView = view
		val holder: ItemViewHolder

		/* Setup View */
		if(convertView == null) {
			itemBinding = RowCustomBinding.inflate(inflater)
			convertView = itemBinding.root
			holder = ItemViewHolder()
			holder.label = itemBinding.customElvTextview
			holder.icon = itemBinding.customElvChildIcon
			holder.checkbox = itemBinding.customElvChildCheckbox
			convertView.tag = holder
		}
		else {
			holder = convertView.tag as ItemViewHolder
		}
		val subcategory = getChild(listPosition, expandedListPosition)

		/* Setup Views */
//		println("setting up subcategory $expandableListPosition ${Data.subcategoryNames[expandableListPosition]} : turnedOn = ${Data.changedConfiguration.subcategoriesTurnedOn[expandableListPosition]}")

		holder.checkbox!!.isChecked = Data.changedConfiguration.subcategoriesTurnedOn[Data.getSubcategoryID(listPosition, expandedListPosition)]
		holder.label!!.text = subcategory.heading
		holder.icon!!.setImageDrawable(subcategory.icon)

		/* Listeners */
		holder.checkbox!!.setOnClickListener{
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "subcategory clicked: ${Data.categories[listPosition].heading} - ${Data.subcategories[Data.getSubcategoryID(listPosition, expandedListPosition)].heading}")

			// Set settings:
			Data.changedConfiguration.subcategoriesTurnedOn[Data.getSubcategoryID(listPosition, expandedListPosition)] = holder.checkbox!!.isChecked
//			dataHashMap[this.categoryList[categoryPosition].heading]!![subcategoryPosition].isChecked = holder.checkbox!!.isChecked

			var needToRefresh = false
			if(allSubcategoriesAreChecked(listPosition)) {
				Data.changedConfiguration.categoriesTurnedOn[listPosition] = true
//				categoryList[listPosition].isChecked = true // Set the category to be checked
				needToRefresh = true
			}

			if(!holder.checkbox!!.isChecked) { // If this subcategory is not checked
				Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "subcategory checked off: ${Data.categories[listPosition].heading} - ${Data.subcategories[Data.getSubcategoryID(listPosition, expandedListPosition)].heading}")
				Data.changedConfiguration.categoriesTurnedOn[listPosition] = false
//				categoryList[listPosition].isChecked = false // Uncheck the category
				needToRefresh = true
			}
		if(needToRefresh) notifyDataSetChanged()
			Data.changedConfiguration.print()
		}

		return convertView
	}

	/*=======================================================================================================*/
	override fun getChildrenCount(categoryPosition: Int): Int {
		return this.dataHashMap[this.categoryList[categoryPosition].heading]!!.size
	}

	/*=======================================================================================================*/
	override fun isChildSelectable(categoryPosition: Int, subcategoryPosition: Int): Boolean {
		return true
	}
	/*=======================================================================================================*/
	/* GENERAL METHODS                                                                                       */
	/*=======================================================================================================*/
	override fun hasStableIds(): Boolean {
		return false
	}
	/*=======================================================================================================*/
	fun markAllSubcategories(category: Int, isChecked: Boolean) {
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "Category: ${Data.categories[category].heading} = $isChecked")

		for(i in Data.changedConfiguration.subcategoriesTurnedOn.indices) {
			if(Data.subcategories[i].category == category)
				Data.changedConfiguration.subcategoriesTurnedOn[i] = isChecked
		}

	}
	/*=======================================================================================================*/
	fun allSubcategoriesAreChecked(categoryPosition: Int) : Boolean {
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "Category: ${Data.categories[categoryPosition].heading}")

		for(i in Data.changedConfiguration.subcategoriesTurnedOn.indices) {
			if(Data.subcategories[i].category == categoryPosition && !Data.changedConfiguration.subcategoriesTurnedOn[i])
				return false
		}

		return true
	}

	/*=======================================================================================================*/
	/* HOLDER CLASSES                                                                                        */
	/*=======================================================================================================*/
	inner class ItemViewHolder {
		internal var label: TextView? = null
		internal var checkbox: CheckBox? = null
		internal var icon: ImageView? = null
	}
	/*=======================================================================================================*/
	inner class GroupViewHolder {
		internal var layout: LinearLayout? = null
		internal var label: TextView? = null
		internal var checkbox: CheckBox? = null
		internal var icon: ImageView? = null
	}
}
