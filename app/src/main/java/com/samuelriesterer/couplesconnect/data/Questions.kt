package com.samuelriesterer.couplesconnect.data

import android.content.Context
import com.samuelriesterer.couplesconnect.R
import com.samuelriesterer.couplesconnect.general.C

class Questions {

	companion object{
		lateinit var listOfQuestionsStrings: List<String>
		var listOfQuestions: MutableList<Question> = mutableListOf()
		var numOfQuestions = 0
		var currentDeck: MutableList<Question> = mutableListOf()

		/*=======================================================================================================*/
		fun setup(context: Context) {
			listOfQuestionsStrings = context.resources.getStringArray(R.array.questionsStrings).toList()
			numOfQuestions = listOfQuestionsStrings.size
			initQuestions()
		}

		/*=======================================================================================================*/
		fun initQuestions() {
			listOfQuestions = mutableListOf(
				Question(0,0,0,0, listOfQuestionsStrings[0],false),
				Question(1,0,0,0, listOfQuestionsStrings[1],false)
			)
		}
		/*=======================================================================================================*/
		fun makeDeck(categories: List<Boolean>, subcategories: List<Boolean>, favoriteType: Int) {
			currentDeck = mutableListOf() // reset list
			var include : Boolean

			for(i in listOfQuestions.indices) {
				include = false

				/* Check for category match */
				if(categories[C.CAT_CONVERSATION] && listOfQuestions[i].category == C.CAT_CONVERSATION)
					include = true
				else if(categories[C.CAT_DATE] && listOfQuestions[i].category == C.CAT_DATE)
					include = true
				else if(categories[C.CAT_INTIMACY] && listOfQuestions[i].category == C.CAT_INTIMACY)
					include = true
				else if(categories[C.CAT_SENSUAL] && listOfQuestions[i].category == C.CAT_SENSUAL)
					include = true

				/* Check for subcategory match */
				else if(subcategories[C.SUB_LIFE] && listOfQuestions[i].subcategory == C.SUB_LIFE)
					include = true

				/* Check for favorite type */
				if(include) {
					include = favoriteType == C.ALL_TYPES ||
						favoriteType == C.FAVORITES_ONLY && listOfQuestions[i].favorite ||
						favoriteType == C.UNRATED_ONLY && !listOfQuestions[i].favorite
				}

				if(include)
					currentDeck.add(listOfQuestions[i])
			}
		}

	}
}