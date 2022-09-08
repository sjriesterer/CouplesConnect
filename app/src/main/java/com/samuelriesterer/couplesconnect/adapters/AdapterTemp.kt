package com.samuelriesterer.couplesconnect.adapters

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import com.samuelriesterer.couplesconnect.R
import com.samuelriesterer.couplesconnect.data.Data
import com.samuelriesterer.couplesconnect.data.DatabaseOps
import com.samuelriesterer.couplesconnect.data.EntityQuestion
import com.samuelriesterer.couplesconnect.databinding.DialogAddQuestionBinding
import com.samuelriesterer.couplesconnect.general.C
import com.samuelriesterer.couplesconnect.general.Logger
import com.samuelriesterer.couplesconnect.general.Settings

class AdapterTemp(private val context: Context, var list: MutableList<EntityQuestion>) : BaseAdapter() {
	private lateinit var question: TextView
	private lateinit var delete: RelativeLayout
	private lateinit var cat: TextView
	private lateinit var sub: TextView
	private lateinit var topic: TextView

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
		/* Variables */
		val rowView = LayoutInflater.from(context).inflate(R.layout.temp_row, parent, false)
		question = rowView.findViewById(R.id.temp_row_text)
		delete = rowView.findViewById(R.id.temp_row_del)
		cat = rowView.findViewById(R.id.temp_row_cat)
		sub = rowView.findViewById(R.id.temp_row_sub)
		topic = rowView.findViewById(R.id.temp_row_topic)

		/* Set View */
		question.text = list[position].question
		cat.text = list[position].category.toString()
		sub.text = list[position].subcategory.toString()
		topic.text = list[position].topic.toString()

		/* Listeners */
		question.setOnClickListener {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "row $position clicked")
			dialogAddQuestion(this, context, position)
		}
		delete.setOnClickListener {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "delete row $position clicked")
			deleteRow(position)
		}
		return rowView
	}

	/*=======================================================================================================*/
	fun deleteRow(position: Int) {
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		DatabaseOps.deleteQuestion(Data.savedQuestions[position].id)
		Data.savedQuestions.removeAt(position)
		notifyDataSetChanged()
	}
	/*=======================================================================================================*/
	companion object {
		val TAG: String = "~*ADAPTER_TEMP"
		/*=======================================================================================================*/
		fun dialogAddQuestion(adapter: AdapterTemp, context: Context, position: Int?) {
			Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "start")
			/* Setup */
			val dialog = Dialog(context)
			val bind: DialogAddQuestionBinding = DialogAddQuestionBinding.inflate(LayoutInflater.from(context))
			dialog.setContentView(bind.root)
			bind.dialogAddQuestionEditText.setSelectAllOnFocus(true)

			if(position == null) {
				bind.dialogAddQuestionSpinnerCat.setSelection(Settings.settingsInt[C.SETTING_LAST_CAT])
				bind.dialogAddQuestionSpinnerSub.setSelection(Settings.settingsInt[C.SETTING_LAST_SUB])
				bind.dialogAddQuestionSpinnerTopic.setSelection(Settings.settingsInt[C.SETTING_LAST_TOPIC])
			}
			else	{
				bind.dialogAddQuestionEditText.setText(Data.savedQuestions[position].question)
				bind.dialogAddQuestionSpinnerCat.setSelection(Data.savedQuestions[position].category)
				bind.dialogAddQuestionSpinnerSub.setSelection(Data.savedQuestions[position].subcategory)
				bind.dialogAddQuestionSpinnerTopic.setSelection(Data.savedQuestions[position].topic)
			}

			bind.dialogAddQuestionEditText.requestFocus()
			showSoftKeyboard(bind.dialogAddQuestionEditText)

			/* Methods */
			fun makeQuestion(id: Int) : EntityQuestion {
				return EntityQuestion(id, bind.dialogAddQuestionSpinnerCat.selectedItemPosition,
					bind.dialogAddQuestionSpinnerSub.selectedItemPosition, bind.dialogAddQuestionSpinnerTopic.selectedItemPosition, bind.dialogAddQuestionEditText.text.toString())
			}

			fun addQuestion() {
				Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "adding question ${bind.dialogAddQuestionEditText.text.toString()}")
				val q = makeQuestion(Settings.settingsInt[C.SETTING_SAVED_QUESTION_ID])
				DatabaseOps.insertQuestion(q)
				Data.savedQuestions.add(0, q)
				Settings.saveSetting(++Settings.settingsInt[C.SETTING_SAVED_QUESTION_ID], C.SETTING_SAVED_QUESTION_ID)
				Settings.saveSetting(bind.dialogAddQuestionSpinnerCat.selectedItemPosition, C.SETTING_LAST_CAT)
				Settings.saveSetting(bind.dialogAddQuestionSpinnerSub.selectedItemPosition, C.SETTING_LAST_SUB)
				Settings.saveSetting(bind.dialogAddQuestionSpinnerTopic.selectedItemPosition, C.SETTING_LAST_TOPIC)
				adapter.notifyDataSetChanged()
			}

			fun saveQuestion() {
				val q = makeQuestion(Data.savedQuestions[position!!].id)
				Data.savedQuestions[position] = q
				DatabaseOps.insertQuestion(q)
				adapter.notifyDataSetChanged()
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
					adapter.notifyDataSetChanged()
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
				hideKeyboard(bind.dialogAddQuestionEditText)
				dialog.dismiss()
			}
			dialog.show()
		}
		/*=======================================================================================================*/
		fun hideKeyboard(view: View) {
			val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
			imm?.hideSoftInputFromWindow(view.windowToken, 0)
		}
		/*=======================================================================================================*/
		fun showSoftKeyboard(context: Context, view: View) {
			if(view.requestFocus()) {
				val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
				imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
			}
		}
		/*=======================================================================================================*/
		private fun showSoftKeyboard(view: View) {
			if (view.requestFocus()) {
				val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?

				// here is one more tricky issue
				// imm.showSoftInputMethod doesn't work well
				// and imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0) doesn't work well for all cases too
				imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
			}
		}
	}
}