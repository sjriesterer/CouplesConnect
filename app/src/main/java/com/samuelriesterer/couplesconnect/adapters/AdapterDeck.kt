package com.samuelriesterer.couplesconnect.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.samuelriesterer.couplesconnect.R
import com.samuelriesterer.couplesconnect.data.Data
import com.samuelriesterer.couplesconnect.data.DatabaseOps
import com.samuelriesterer.couplesconnect.data.Question
import com.samuelriesterer.couplesconnect.general.C
import com.samuelriesterer.couplesconnect.general.Logger
import com.samuelriesterer.couplesconnect.interfaces.InterfaceMain

class AdapterDeck(val context: Context, val list: List<Question>) : BaseAdapter() {
	lateinit var interfaceMain: InterfaceMain
	val TAG: String = "~*ADAPTER_DECK"

	/*=======================================================================================================*/
	fun setup(context: Context?) {
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		try {
			interfaceMain = context as InterfaceMain
		}
		catch (e: ClassCastException) {
			throw ClassCastException("$context must implement interfaceMain")
		}
	}
	/*=======================================================================================================*/
	fun setFavoriteIcon(view: ImageView, position: Int) {
		// Sets the favorite icon for the question at position in list
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		if(Data.currentDeck[position].favorite)
			view.setBackgroundResource(R.drawable.ic_heart_on_sel)
		else
			view.setBackgroundResource(R.drawable.ic_heart_off_sel)
	}

	/*=======================================================================================================*/
	override fun getCount(): Int {
		return list.count()
	}

	/*=======================================================================================================*/
	override fun getItem(position: Int): Any {
		return position
	}

	/*=======================================================================================================*/
	override fun getItemId(position: Int): Long {
		return position.toLong()
	}

	/*=======================================================================================================*/
	override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
		var convertView = LayoutInflater.from(context).inflate(R.layout.row_deck, parent, false)
		convertView.findViewById<ImageView>(R.id.deck_category_icon).setImageDrawable(Data.categories[list[position].category].icon)
		convertView.findViewById<ImageView>(R.id.deck_subcategory_icon).setImageDrawable(Data.subcategories[list[position].subcategory].icon)
		convertView.findViewById<TextView>(R.id.deck_question).text = list[position].question
		setFavoriteIcon(convertView.findViewById(R.id.deck_favorite), position)

		convertView.findViewById<ImageView>(R.id.deck_favorite).setOnClickListener {
			val newValue = !Data.currentDeck[position].favorite
			Data.listOfQuestions.find { it.id == Data.currentDeck[position].id }?.favorite = newValue
			Data.currentDeck[position].favorite = newValue
			DatabaseOps.updateFavorite(Data.currentDeck[position].id, newValue)
			setFavoriteIcon(convertView.findViewById(R.id.deck_favorite), position)
		}

		return convertView
	}
}