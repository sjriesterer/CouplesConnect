package com.samuelriesterer.couplesconnect.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.samuelriesterer.couplesconnect.R
import com.samuelriesterer.couplesconnect.data.Question
import com.samuelriesterer.couplesconnect.general.C
import com.samuelriesterer.couplesconnect.general.Logger
import java.util.*

class AdapterQuestions(val context: Context, val questionList: MutableList<Question>) : PagerAdapter() {
	val TAG: String = "~*ADAPTER_QUESTIONS"

	/*=======================================================================================================*/
	override fun getCount(): Int {
		return questionList.size
	}

	/*=======================================================================================================*/
	override fun isViewFromObject(view: View, `object`: Any): Boolean {
		return view === `object` as RelativeLayout
	}

	/*=======================================================================================================*/
	override fun instantiateItem(container: ViewGroup, position: Int): Any {
		Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "Viewpager position = $position")
		val mLayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		val itemView: View = mLayoutInflater.inflate(R.layout.question_layout, container, false)
		val textView: TextView = itemView.findViewById<View>(R.id.question_textview) as TextView
		textView.text = questionList[position].question
		Objects.requireNonNull(container).addView(itemView)

		return itemView
	}

	/*=======================================================================================================*/
	override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
		container.removeView(`object` as RelativeLayout)
	}
}