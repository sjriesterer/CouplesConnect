package com.samuelriesterer.couplesconnect.adapters

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
		question = rowView.findViewById(R.id.temp_row_text)
		delete = rowView.findViewById(R.id.temp_row_del)
		question.text = list[position].question
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
			bind.dialogAddQuestionEditText.requestFocus()
			if(position != null)
				bind.dialogAddQuestionEditText.setText(Data.savedQuestions[position].question)

			/* Methods */
			fun makeQuestion(id: Int) : EntityQuestion {
				return EntityQuestion(id, bind.dialogAddQuestionSpinnerCat.selectedItemPosition, bind.dialogAddQuestionSpinnerSub.selectedItemPosition, 0, bind.dialogAddQuestionEditText.text.toString())
			}

			fun addQuestion() {
				Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "adding question ${bind.dialogAddQuestionEditText.text.toString()}")
				val q = makeQuestion(Settings.settingsInt[C.SETTING_SAVED_QUESTION_ID])
				DatabaseOps.insertQuestion(q)
				Data.savedQuestions.add(0, q)
				Settings.settingsInt[C.SETTING_SAVED_QUESTION_ID]++
				Settings.saveSettingInt(Settings.settingsInt[C.SETTING_SAVED_QUESTION_ID], C.SETTING_SAVED_QUESTION_ID)
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
				dialog.dismiss()
			}
			dialog.show()
		}
	}
}