package com.samuelriesterer.couplesconnect.data

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
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
		lateinit var categoryIcons: List<Drawable?>
		lateinit var categoryColors: List<Int>

		lateinit var subcategoryGroupings: List<Int>
		lateinit var subcategoryNames: List<String>
		lateinit var subcategoryIcons: List<Drawable?>
		lateinit var subcategoryColors: List<Int>

		lateinit var currentConfiguration: EntityConfiguration
		lateinit var changedConfiguration: EntityConfiguration // The configuration the user is editing in FragmentCustom

		lateinit var savedQuestions: MutableList<EntityQuestion>
		/*=======================================================================================================*/
		fun setup(context: Context) {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
			/* Variables */
			listOfQuestionsStrings = context.resources.getStringArray(R.array.questionsStrings).toList()
			numOfQuestions = listOfQuestionsStrings.size
			Questions.initQuestions()

			/* Init Favorites & currentConfiguration from Database */
			if(!Settings.settingsBoolean[C.SETTING_APP_INITIALIZED])
			{
				savedFavorites = initFavoritesFirstTime()
				DatabaseOps.insertFavorites(savedFavorites)
				savedQuestions = initQuestionsFirstTime()
				DatabaseOps.insertQuestions(savedQuestions)
				currentConfiguration = getDefaultConfiguration()
				DatabaseOps.insertConfiguration(currentConfiguration)
			}
			else
			{
				savedFavorites = DatabaseOps.getFavorites()
				savedQuestions = DatabaseOps.getQuestions()
				copyFavoritesToList()
				currentConfiguration = DatabaseOps.getConfiguration()
			}

			changedConfiguration = currentConfiguration.copy()

			/* Categories */
			categoryNames = context.resources.getStringArray(R.array.category_names).toList()
			categoryColors = context.resources.getIntArray(R.array.category_colors).toList()
			categoryIcons = listOf(
				AppCompatResources.getDrawable(context, R.drawable.im_conversations),
				AppCompatResources.getDrawable(context, R.drawable.im_date),
				AppCompatResources.getDrawable(context, R.drawable.im_intimacy),
				AppCompatResources.getDrawable(context, R.drawable.im_sensual)
			)

			/* Subcategories */
			subcategoryNames = context.resources.getStringArray(R.array.subcategory_names).toList()
			subcategoryColors = context.resources.getIntArray(R.array.subcategory_cat1_colors).toList()
			subcategoryIcons = listOf(
				AppCompatResources.getDrawable(context, R.drawable.im_life),
				AppCompatResources.getDrawable(context, R.drawable.im_love),
				AppCompatResources.getDrawable(context, R.drawable.im_relationships),
				AppCompatResources.getDrawable(context, R.drawable.im_self),
				AppCompatResources.getDrawable(context, R.drawable.im_religion),
				AppCompatResources.getDrawable(context, R.drawable.im_government),
				AppCompatResources.getDrawable(context, R.drawable.im_fun),
				AppCompatResources.getDrawable(context, R.drawable.im_date),
				AppCompatResources.getDrawable(context, R.drawable.im_hearts),
				AppCompatResources.getDrawable(context, R.drawable.im_feelings),
				AppCompatResources.getDrawable(context, R.drawable.im_contrast),
				AppCompatResources.getDrawable(context, R.drawable.im_question),
				AppCompatResources.getDrawable(context, R.drawable.im_lovelanguage),
				AppCompatResources.getDrawable(context, R.drawable.im_childhood),
				AppCompatResources.getDrawable(context, R.drawable.im_trauma),
				AppCompatResources.getDrawable(context, R.drawable.im_affirmations),
				AppCompatResources.getDrawable(context, R.drawable.im_needs),
				AppCompatResources.getDrawable(context, R.drawable.im_romance),
				AppCompatResources.getDrawable(context, R.drawable.im_flirt),
				AppCompatResources.getDrawable(context, R.drawable.im_foreplay),
				AppCompatResources.getDrawable(context, R.drawable.im_sex)
			)

			// These groupings are to map which category the subcategory belongs to:
			subcategoryGroupings = listOf(
				C.CAT_CONVERSATION,
				C.CAT_CONVERSATION,
				C.CAT_CONVERSATION,
				C.CAT_CONVERSATION,
				C.CAT_CONVERSATION,
				C.CAT_CONVERSATION,

				C.CAT_DATE,
				C.CAT_DATE,
				C.CAT_DATE,

				C.CAT_INTIMACY,
				C.CAT_INTIMACY,
				C.CAT_INTIMACY,
				C.CAT_INTIMACY,
				C.CAT_INTIMACY,
				C.CAT_INTIMACY,
				C.CAT_INTIMACY,
				C.CAT_INTIMACY,

				C.CAT_SENSUAL,
				C.CAT_SENSUAL,
				C.CAT_SENSUAL,
				C.CAT_SENSUAL
			)

			/* Make the Deck */
			makeDeck(currentConfiguration)
		}
		/*=======================================================================================================*/
		/* QUESTIONS                                                                                             */
		/*=======================================================================================================*/
		fun initQuestionsFirstTime() : MutableList<EntityQuestion> {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
			val list : MutableList<EntityQuestion> = mutableListOf()
			list.add(EntityQuestion(0, C.CAT_CONVERSATION, C.SUB_LIFE, 0, "This is a life1 question"))
			list.add(EntityQuestion(1, C.CAT_CONVERSATION, C.SUB_LIFE, 0, "This is a life2 question"))
			list.add(EntityQuestion(2, C.CAT_CONVERSATION, C.SUB_LIFE, 0, "This is a life3 question"))
			return list
		}
		/*=======================================================================================================*/
		fun questionsToString(list: MutableList<EntityQuestion>) : String {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")

			return ""
		}
		/*=======================================================================================================*/
		/* FAVORITES                                                                                             */
		/*=======================================================================================================*/
		fun initFavoritesFirstTime() : MutableList<EntityFavorites> {
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
		/* CATEGORY/SUBCATEGORY                                                                                  */
		/*=======================================================================================================*/
		fun getSubcategoryID(category: Int, subcategoryIndex: Int) : Int {
//			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
			var index = 0
			for(i in subcategoryGroupings.indices) {
				if(getCategory(i) == category) {
//					println("subcategory returned is ${index + subcategoryIndex}")
					return index + subcategoryIndex
				}
				index++
			}
			return -1
		}
		/*=======================================================================================================*/
		fun getCategory(subcategory: Int) : Int {
//			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
//			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "subcategory = ${subcategoryNames[subcategory]}, returning ${categoryNames[subcategoryGroupings[subcategory]]}")
			return subcategoryGroupings[subcategory]
		}
		/*=======================================================================================================*/
		/* CONFIGURATIONS                                                                                        */
		/*=======================================================================================================*/
		fun getEmptyConfiguration(): EntityConfiguration {
			Logger.log(C.LOG_I, Settings.TAG, object {}.javaClass.enclosingMethod?.name, "start")
			//@formatter:off
			return EntityConfiguration(
				0,
				mutableListOf(),
				mutableListOf(),
				Settings.getDefaultSortOrder(),
				Settings.getDefaultFilterType()
			)
			//@formatter:on
		}

		/*=======================================================================================================*/
		fun getDefaultConfiguration(): EntityConfiguration {
			Logger.log(C.LOG_I, Settings.TAG, object {}.javaClass.enclosingMethod?.name, "start")
			//@formatter:off
			return EntityConfiguration(
				0,
				mutableListOf(true, true, true, true),
				mutableListOf(true, true, true,true, true, true,true, true, true,true, true, true,true, true, true,true, true, true,true, true, true),
				Settings.getDefaultSortOrder(),
				Settings.getDefaultFilterType()
			)
			//@formatter:on
		}
		/*=======================================================================================================*/
		/* MAKE DECK                                                                                             */
		/*=======================================================================================================*/
		fun makeDeck(configuration: EntityConfiguration) {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
			currentConfiguration.print()
			currentDeck.clear()
			var include: Boolean

			// Goes through each question and sets flag include if that question is to be included in this deck.
			// If a category is turned on, it included all subcategories.
			for(i in listOfQuestions.indices) {
				include = false
				/* Check for category match */
				// Goes through each category and if a match, sets the flag:
				for(j in categoryNames.indices) {
					if(configuration.categoriesTurnedOn[j] && listOfQuestions[i].category == j) {
						include = true
						break
					}
				}

				/* Check for subcategory match if no matches are found in category */
				// Goes through each subcategory and if a match, sets the flag:
				if(!include) // Only need to check the subcategories if no categories are turned on:
				{
					for(j in subcategoryNames.indices) {
						if(configuration.subcategoriesTurnedOn[j] && listOfQuestions[i].subcategory == j) {
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
			for(i in currentConfiguration.categoriesTurnedOn.indices)
				currentConfiguration.categoriesTurnedOn[i] = false

			// Turn selected category on:
			currentConfiguration.categoriesTurnedOn[category] = true

			// Turn all subcategories off except those belonging to the single category:
			for(i in currentConfiguration.subcategoriesTurnedOn.indices) {
				if(getCategory(i) == category)
					currentConfiguration.subcategoriesTurnedOn[i] = true
				else
					currentConfiguration.subcategoriesTurnedOn[i] = false
			}

			// If setting to keep sort settings is off, revert to default settings:
			if(!Settings.settingsBoolean[C.SETTING_KEEP_SORT_SETTING]) {
				currentConfiguration.currentSortOrder = Settings.getDefaultSortOrder()
				currentConfiguration.currentFilterType = Settings.getDefaultFilterType()
			}

			currentConfiguration.print()
			// Make deck:
//			makeDeck(currentConfiguration)
		}
		/*=======================================================================================================*/
		fun makeDeckSingleSubcategory(subcategory: Int) {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")

			// Turns all categories off:
			for(i in currentConfiguration.categoriesTurnedOn.indices)
				currentConfiguration.categoriesTurnedOn[i] = false

			// Turn all subcategories off:
			for(i in currentConfiguration.subcategoriesTurnedOn.indices)
				currentConfiguration.subcategoriesTurnedOn[i] = false

			// Turn selected subcategory on:
			currentConfiguration.subcategoriesTurnedOn[subcategory] = true

			// If setting to keep sort settings is off, revert to default settings:
			if(!Settings.settingsBoolean[C.SETTING_KEEP_SORT_SETTING]) {
				currentConfiguration.currentSortOrder = Settings.getDefaultSortOrder()
				currentConfiguration.currentFilterType = Settings.getDefaultFilterType()
			}

			// Make deck:
//			makeDeck(currentConfiguration)

		}
	}
}