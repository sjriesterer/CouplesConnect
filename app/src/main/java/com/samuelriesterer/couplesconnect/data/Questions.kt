package com.samuelriesterer.couplesconnect.data

import android.content.Context
import com.samuelriesterer.couplesconnect.R
import com.samuelriesterer.couplesconnect.general.C
import com.samuelriesterer.couplesconnect.general.Logger

class Questions {
	companion object {
		val TAG: String = "~*QUESTIONS"
		lateinit var listOfQuestionsStrings: List<String>
		var listOfQuestions: MutableList<Question> = mutableListOf()
		var numOfQuestions = 0
		var listOfFavorites: MutableList<Boolean> = mutableListOf()
		var currentDeck: MutableList<Question> = mutableListOf()


		/*=======================================================================================================*/
		fun setup(context: Context) {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
			listOfQuestionsStrings = context.resources.getStringArray(R.array.questionsStrings).toList()
			numOfQuestions = listOfQuestionsStrings.size
			// get list of favorites from database
			initQuestions()
		}

		/*=======================================================================================================*/
		fun initQuestions() {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
			listOfQuestions = mutableListOf(
				Question(0, C.CAT_CONVERSATION, C.SUB_LIFE, 0, listOfQuestionsStrings[0], false),
				Question(1, C.CAT_CONVERSATION, C.SUB_LIFE, 0, listOfQuestionsStrings[1], false),
				Question(2, C.CAT_CONVERSATION, C.SUB_LIFE, 0, listOfQuestionsStrings[2], false),
				Question(3, C.CAT_CONVERSATION, C.SUB_LIFE, 0, listOfQuestionsStrings[3], false),
				Question(4, C.CAT_CONVERSATION, C.SUB_LIFE, 0, listOfQuestionsStrings[4], false),
				Question(5, C.CAT_CONVERSATION, C.SUB_LIFE, 0, listOfQuestionsStrings[5], false),
				Question(1, C.CAT_CONVERSATION, C.SUB_LOVE, 0, listOfQuestionsStrings[1], false),
				Question(2, C.CAT_CONVERSATION, C.SUB_RELATIONSHIPS, 0, listOfQuestionsStrings[2], false),
				Question(3, C.CAT_CONVERSATION, C.SUB_RELIGION, 0, listOfQuestionsStrings[3], false),
				Question(4, C.CAT_CONVERSATION, C.SUB_SELF, 0, listOfQuestionsStrings[4], false),
				Question(5, C.CAT_CONVERSATION, C.SUB_GOVERNMENT, 0, listOfQuestionsStrings[5], false),
				Question(6, C.CAT_DATE, C.SUB_FUN, 0, listOfQuestionsStrings[6], false),
				Question(7, C.CAT_DATE, C.SUB_DATE, 0, listOfQuestionsStrings[7], false),
				Question(8, C.CAT_DATE, C.SUB_ROMANTIC_DATE, 0, listOfQuestionsStrings[8], false),
				Question(9, C.CAT_INTIMACY, C.SUB_FEELINGS, 0, listOfQuestionsStrings[9], false),
				Question(10, C.CAT_INTIMACY, C.SUB_CONTRAST, 0, listOfQuestionsStrings[10], false),
				Question(11, C.CAT_INTIMACY, C.SUB_COMPLAINTS, 0, listOfQuestionsStrings[11], false),
				Question(12, C.CAT_INTIMACY, C.SUB_LOVE_LANGUAGE, 0, listOfQuestionsStrings[12], false),
				Question(13, C.CAT_INTIMACY, C.SUB_CHILDHOOD, 0, listOfQuestionsStrings[13], false),
				Question(14, C.CAT_INTIMACY, C.SUB_TRAUMA, 0, listOfQuestionsStrings[14], false),
				Question(15, C.CAT_INTIMACY, C.SUB_AFFIRMATION, 0, listOfQuestionsStrings[15], false),
				Question(16, C.CAT_INTIMACY, C.SUB_NEEDS, 0, listOfQuestionsStrings[16], false),
				Question(17, C.CAT_SENSUAL, C.SUB_ROMANCE, 0, listOfQuestionsStrings[17], false),
				Question(18, C.CAT_SENSUAL, C.SUB_FLIRTATION, 0, listOfQuestionsStrings[18], false),
				Question(19, C.CAT_SENSUAL, C.SUB_FOREPLAY, 0, listOfQuestionsStrings[19], false),
				Question(20, C.CAT_SENSUAL, C.SUB_SEX, 0, listOfQuestionsStrings[20], false)
			)
		}
		/*=======================================================================================================*/
		fun filterDeck() {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")

		}
		/*=======================================================================================================*/
		fun makeDeck(categories: List<Boolean>, subcategories: List<Boolean>, favoriteType: Int) {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
			currentDeck.clear()
			var include: Boolean

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
					include = favoriteType == C.FILTER_ALL_TYPES ||
						favoriteType == C.FILTER_FAVORITES_ONLY && listOfQuestions[i].favorite ||
						favoriteType == C.FILTER_UNRATED_ONLY && !listOfQuestions[i].favorite
				}

				if(include)
					currentDeck.add(listOfQuestions[i])
			}
		}

		/*=======================================================================================================*/
		fun makeDeckSingleCategory(category: Int) {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
			currentDeck.clear()

			for(i in listOfQuestions.indices) {
				if(listOfQuestions[i].category == category)
					currentDeck.add(listOfQuestions[i])
			}
		}
		/*=======================================================================================================*/
		fun makeDeckSingleSubcategory(subcategory: Int) {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
			currentDeck.clear()

			for(i in listOfQuestions.indices) {
				if(listOfQuestions[i].subcategory == subcategory)
					currentDeck.add(listOfQuestions[i])
			}
		}
	}
}