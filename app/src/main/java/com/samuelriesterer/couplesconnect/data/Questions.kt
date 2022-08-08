package com.samuelriesterer.couplesconnect.data

import android.content.Context
import com.samuelriesterer.couplesconnect.R

class Questions {

	companion object{
		lateinit var listOfQuestionsStrings: List<String>
		var listOfQuestions: MutableList<Question> = mutableListOf()
		var numOfQuestions = 0

		fun setup(context: Context) {
			listOfQuestionsStrings = context.resources.getStringArray(R.array.questionsStrings).toList()
			numOfQuestions = listOfQuestionsStrings.size

			initQuestions()
		}

		fun initQuestions() {
			listOfQuestions = mutableListOf(
				Question(0,0,0,0, listOfQuestionsStrings[0],false),
				Question(1,0,0,0, listOfQuestionsStrings[1],false)
			)

		}
	}
}