package com.samuelriesterer.couplesconnect.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.samuelriesterer.couplesconnect.R
import com.samuelriesterer.couplesconnect.data.EntityQuestion

class AdapterTemp(private val context: Context, var list: MutableList<EntityQuestion>) : BaseAdapter() {
	private lateinit var question: TextView
	/*=======================================================================================================*/
	override fun getCount(): Int {
		return list.size
	}
	/*=======================================================================================================*/
	override fun getItem(position: Int): Any {
		return list[position]
	}
	/*=======================================================================================================*/
	override fun getItemId(position: Int): Long {
		return position.toLong()
	}
	/*=======================================================================================================*/
	override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
		val rowView = LayoutInflater.from(context).inflate(R.layout.temp_row, parent, false)
		question = rowView.findViewById<TextView>(R.id.temp_row_text)
		question.text = list[position].question

		return rowView
	}
}