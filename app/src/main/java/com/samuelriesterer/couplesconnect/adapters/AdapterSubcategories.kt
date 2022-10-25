package com.samuelriesterer.couplesconnect.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.samuelriesterer.couplesconnect.R
import com.samuelriesterer.couplesconnect.data.Data
import com.samuelriesterer.couplesconnect.data.Subcategory
import com.samuelriesterer.couplesconnect.general.C
import com.samuelriesterer.couplesconnect.general.Logger
import com.samuelriesterer.couplesconnect.interfaces.InterfaceMain

class AdapterSubcategories(val context: Context, val list: List<Subcategory>) : BaseAdapter() {
	lateinit var interfaceMain: InterfaceMain
	val TAG: String = "~*ADAPTER_SUBCATEGORIES"

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
	fun dialogInfo(title: String, message: String) {
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		val builder = AlertDialog.Builder(context)

		builder.setTitle(title)
		builder.setMessage(message)
		builder.setPositiveButton(context.getString(R.string.ok)) { dialog, _ ->
			dialog.dismiss()
		}
		builder.show()
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
		var convertView = LayoutInflater.from(context).inflate(R.layout.row_subcategory, parent, false)
		convertView.findViewById<LinearLayout>(R.id.subcategory_layout).setOnClickListener {
			it?.playSoundEffect(android.view.SoundEffectConstants.CLICK)
			Data.makeDeckSingleSubcategory(list[position].id)
			interfaceMain.switchFragments(C.FRAG_QUESTION)
		}
		convertView.findViewById<LinearLayout>(R.id.subcategory_layout).setOnLongClickListener {
			it?.playSoundEffect(android.view.SoundEffectConstants.CLICK)
			dialogInfo("", Data.subcategoriesHelp[list[position].id])
			true
		}
		convertView.findViewById<ImageView>(R.id.subcategory_icon).setImageDrawable(list[position].icon)
		convertView.findViewById<TextView>(R.id.subcategory_title).text = list[position].heading


		return convertView
	}
}