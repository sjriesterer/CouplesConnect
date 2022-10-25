package com.samuelriesterer.couplesconnect.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.samuelriesterer.couplesconnect.R
import com.samuelriesterer.couplesconnect.data.Question
import com.samuelriesterer.couplesconnect.general.C
import com.samuelriesterer.couplesconnect.general.Logger
import java.util.*

class AdapterViewPageQuestions(val context: Context, val imageList: MutableList<Question>) : PagerAdapter() {
	val TAG: String = "~*VIEW_PAGER_ADAPTER"

	/*=======================================================================================================*/
	override fun getCount(): Int {
		return imageList.size
	}

	/*=======================================================================================================*/
	// on below line we are returning the object
	override fun isViewFromObject(view: View, `object`: Any): Boolean {
		return view === `object` as RelativeLayout
	}

	/*=======================================================================================================*/
	override fun instantiateItem(container: ViewGroup, position: Int): Any {
		Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "Viewpager position = $position")


		val mLayoutInflater =
			context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

		val itemView: View = mLayoutInflater.inflate(R.layout.question_layout, container, false)

		// on below line we are initializing our image view with the id.
		val textView: TextView = itemView.findViewById<View>(R.id.question_textview) as TextView

		// on below line we are setting string resource for textView.
		textView.text = imageList[position].question

		// on the below line we are adding this item view to the container.
		Objects.requireNonNull(container).addView(itemView)

		return itemView
	}

	/*=======================================================================================================*/
	override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
		// on below line we are removing view
		container.removeView(`object` as RelativeLayout)
	}


}