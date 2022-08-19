package com.samuelriesterer.couplesconnect.data

import android.content.Context
import com.samuelriesterer.couplesconnect.R
import com.samuelriesterer.couplesconnect.general.C
import com.samuelriesterer.couplesconnect.general.Logger
import com.samuelriesterer.couplesconnect.general.Settings

class Data {
	companion object {
		val TAG: String = "~*DATA"
		lateinit var listOfQuestionsStrings: List<String>
		var listOfQuestions: MutableList<Question> = mutableListOf()
		var numOfQuestions = 0
		var currentDeck: MutableList<Question> = mutableListOf()
		lateinit var savedFavorites: MutableList<EntityFavorites>
		lateinit var categoryNames: List<String>
		lateinit var subcategoryNames: List<String>
		lateinit var currentConfiguration: EntityConfiguration

		/*=======================================================================================================*/
		fun setup(context: Context) {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
			listOfQuestionsStrings = context.resources.getStringArray(R.array.questionsStrings).toList()
			numOfQuestions = listOfQuestionsStrings.size
			Questions.initQuestions()

			if(!Settings.settingsBoolean[C.SETTING_APP_INITIALIZED])
			{
				savedFavorites = initFavorites()
				DatabaseOps.insertFavorites(savedFavorites)
				currentConfiguration = Settings.getDefaultConfiguration()
				DatabaseOps.insertConfiguration(currentConfiguration)
			}
			else
			{
				savedFavorites = DatabaseOps.getFavorites()
				copyFavoritesToList()
				currentConfiguration = DatabaseOps.getConfiguration()
			}
			categoryNames = context.resources.getStringArray(R.array.category_names).toList()
			subcategoryNames = context.resources.getStringArray(R.array.subcategory_names).toList()
			makeDeck(currentConfiguration)
		}
		/*=======================================================================================================*/
		fun initFavorites() : MutableList<EntityFavorites> {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
			// Inits the favorites list upon first app run so that program can load into database first time:
			val list : MutableList<EntityFavorites> = mutableListOf()
			for(i in listOfQuestionsStrings.indices) list.add(EntityFavorites(i, false))
			return list
		}
		/*=======================================================================================================*/
		fun copyFavoritesToList() {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
			// Copies the favorites list pulled from database into the current list of Questions:
			for(i in listOfQuestionsStrings.indices) listOfQuestions[i].favorite = savedFavorites[i].favorite
		}

		/*=======================================================================================================*/
		fun makeDeck(configuration: EntityConfiguration) {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")

			currentDeck.clear()
			var include: Boolean

			// Goes through each question and sets flag include if that question is to be included in this deck.
			// If a category is turned on, it included all subcategories.
			for(i in listOfQuestions.indices) {
				include = false
				/* Check for category match */
				// Goes through each category and if a match, sets the flag:
				for(j in categoryNames.indices) {
					if(configuration.categories[j] && listOfQuestions[i].category == j) {
						include = true
						break
					}
				}

				/* Check for subcategory match if no matches are found in category */
				// Goes through each subcategory and if a match, sets the flag:
				if(!include) // Only need to check the subcategories if no categories are turned on:
				{
					for(j in subcategoryNames.indices) {
						if(configuration.subcategories[j] && listOfQuestions[i].subcategory == j) {
							include = true
							break
						}
					}
				}

				/* Check for favorite type */
				if(include) {
					include = configuration.currentFilterType == C.FILTER_ALL_TYPES ||
						configuration.currentFilterType == C.FILTER_FAVORITES_ONLY && listOfQuestions[i].favorite ||
						configuration.currentFilterType == C.FILTER_UNRATED_ONLY && !listOfQuestions[i].favorite
				}

				/* Add entry */
				if(include)
					currentDeck.add(listOfQuestions[i])
			}
			/* Sort Deck */
			when (configuration.currentSortOrder) {
				C.SORT_ID -> currentDeck.sortBy { it.id }
				C.SORT_ALPHABETICALLY -> currentDeck.sortBy { it.question }
				C.SORT_FAVORITES -> currentDeck.sortByDescending { it.favorite }
				else -> currentDeck.shuffle()
			}
		}

		/*=======================================================================================================*/
		fun makeDeckSingleCategory(category: Int) {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")

			// Turns all categories off:
			for(i in currentConfiguration.categories.indices)
				currentConfiguration.categories[i] = false

			// Turn selected category on:
			currentConfiguration.categories[category] = true

			// Turn all subcategories off:
			for(i in currentConfiguration.subcategories.indices)
				currentConfiguration.subcategories[i] = false

			// If setting to keep sort settings is off, revert to default settings:
			if(!Settings.settingsBoolean[C.SETTING_KEEP_SORT_SETTING]) {
				currentConfiguration.currentSortOrder = Settings.getDefaultSortOrder()
				currentConfiguration.currentFilterType = Settings.getDefaultFilterType()
			}

			// Make deck:
			makeDeck(currentConfiguration)
		}
		/*=======================================================================================================*/
		fun makeDeckSingleSubcategory(subcategory: Int) {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")

			// Turns all categories off:
			for(i in currentConfiguration.categories.indices)
				currentConfiguration.categories[i] = false

			// Turn all subcategories off:
			for(i in currentConfiguration.subcategories.indices)
				currentConfiguration.subcategories[i] = false

			// Turn selected subcategory on:
			currentConfiguration.subcategories[subcategory] = true

			// If setting to keep sort settings is off, revert to default settings:
			if(!Settings.settingsBoolean[C.SETTING_KEEP_SORT_SETTING]) {
				currentConfiguration.currentSortOrder = Settings.getDefaultSortOrder()
				currentConfiguration.currentFilterType = Settings.getDefaultFilterType()
			}

			// Make deck:
			makeDeck(currentConfiguration)

		}
	}
}