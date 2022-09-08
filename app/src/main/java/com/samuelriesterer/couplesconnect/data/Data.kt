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

		lateinit var topicNames: List<String>

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
			/* Topics */
			topicNames = context.resources.getStringArray(R.array.topic_names).toList()


			/* Make the Deck */
			makeDeck(currentConfiguration)
		}
		/*=======================================================================================================*/
		/* QUESTIONS                                                                                             */
		/*=======================================================================================================*/
		fun initQuestionsFirstTime() : MutableList<EntityQuestion> {
			Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
			val list : MutableList<EntityQuestion> = mutableListOf()
			list.add(EntityQuestion(0,0,0,0,"What is hard about loving others?"))
			list.add(EntityQuestion(1,0,0,0,"What do you believe love is?"))
			list.add(EntityQuestion(2,0,0,0,"Describe an experience where you received the best love."))
			list.add(EntityQuestion(3,0,0,0,"Describe an experience where love was painful."))
			list.add(EntityQuestion(4,0,0,0,"Do you believe love is all a relationship needs?"))
			list.add(EntityQuestion(5,0,0,0,"What is your love language?"))
			list.add(EntityQuestion(6,0,0,0,"How does it feel to be loved?"))
			list.add(EntityQuestion(7,0,0,0,"How does it feel to love others?"))
			list.add(EntityQuestion(8,0,0,0,"What is self-love?"))
			list.add(EntityQuestion(9,0,0,0,"Who do you love the most?"))
			list.add(EntityQuestion(10,0,0,0,"When is loving most inconvenient?"))
			list.add(EntityQuestion(11,0,0,1,"What are the most important things in life?"))
			list.add(EntityQuestion(12,0,0,1,"Describe a good life?"))
			list.add(EntityQuestion(13,0,0,1,"How has your life been?"))
			list.add(EntityQuestion(14,0,0,1,"What has been the best thing that has ever happened to you in your life?"))
			list.add(EntityQuestion(15,0,0,1,"Do you love life?"))
			list.add(EntityQuestion(16,0,0,1,"Do you have any regrets?"))
			list.add(EntityQuestion(17,0,0,1,"Would you rather live a long mediocre life or a short fulfilling life?"))
			list.add(EntityQuestion(18,0,0,1,"What are some things you can do to live a more fulfilling life?"))
			list.add(EntityQuestion(19,0,0,1,"What is missing in your life?"))
			list.add(EntityQuestion(20,0,0,1,"What is your oldest memory?"))
			list.add(EntityQuestion(21,0,0,1,"What do you fear about death?"))
			list.add(EntityQuestion(22,0,0,1,"How do you cope with the death of a loved one?"))
			list.add(EntityQuestion(23,0,0,1,"Describe your ideal death process?"))
			list.add(EntityQuestion(24,0,0,1,"How many years would you like to live?"))
			list.add(EntityQuestion(25,0,0,1,"What are some things you would like to have achieved before you die?"))
			list.add(EntityQuestion(26,0,0,1,"Whose death has brought you the most grief?"))
			list.add(EntityQuestion(27,0,0,1,"What is the best thing about aging?"))
			list.add(EntityQuestion(28,0,0,1,"How have you aged over the years?"))
			list.add(EntityQuestion(29,0,0,1,"What has been the best age in your life?"))
			list.add(EntityQuestion(30,0,0,1,"Are you afraid of getting old?"))
			list.add(EntityQuestion(31,0,0,1,"What is the hardest thing about aging?"))
			list.add(EntityQuestion(32,0,0,1,"How do you feel you have changed through the years?"))
			list.add(EntityQuestion(33,0,0,1,"How do you hope to change through aging?"))
			list.add(EntityQuestion(34,0,0,1,"Are you happy with your current age?"))
			list.add(EntityQuestion(35,0,0,1,"What age do you consider young? Old?"))
			list.add(EntityQuestion(36,0,0,1,"What is one quality from your youth you miss the most?"))
			list.add(EntityQuestion(37,0,0,1,"What is the most miraculous part about birth?"))
			list.add(EntityQuestion(38,0,0,2,"Do you like the outdoors?"))
			list.add(EntityQuestion(39,0,0,2,"Describe your best moment with nature in your life."))
			list.add(EntityQuestion(40,0,0,2,"Describe your ideal outdoor camping experience."))
			list.add(EntityQuestion(41,0,0,2,"What is your favorite season?"))
			list.add(EntityQuestion(42,0,0,2,"Describe your perfect yearly weather seasons."))
			list.add(EntityQuestion(43,0,0,2,"Do you like storms?"))
			list.add(EntityQuestion(44,0,0,2,"What climate and temperature is perfect to live in? To work in? To sleep in?"))
			list.add(EntityQuestion(45,0,0,2,"Do you experience seasonal depression due to climate change?"))
			list.add(EntityQuestion(46,0,0,2,"Do you think getting caught in the rain is fun?"))
			list.add(EntityQuestion(47,0,0,2,"Describe a memorable experience you’ve had with the weather."))
			list.add(EntityQuestion(48,0,0,2,"Do you believe in recycling?"))
			list.add(EntityQuestion(49,0,0,2,"Do you believe in global warming?"))
			list.add(EntityQuestion(50,0,0,2,"What is the best way to preserve the earth?"))
			list.add(EntityQuestion(51,0,0,2,"Why do you think earth is special?"))
			list.add(EntityQuestion(52,0,0,2,"What is the biggest issue affecting the earth?"))
			list.add(EntityQuestion(53,0,0,2,"Do you believe in aliens?"))
			list.add(EntityQuestion(54,0,0,2,"Do you believe there is another life-bearing planet?"))
			list.add(EntityQuestion(55,0,0,2,"What is the most awesome thing in the cosmos?"))
			list.add(EntityQuestion(56,0,0,2,"Do you believe one day we will inhabit other planets?"))
			list.add(EntityQuestion(57,0,0,3,"What is the condition of humanity right now?"))
			list.add(EntityQuestion(58,0,0,3,"Why is there so much variance between peoples?"))
			list.add(EntityQuestion(64,0,0,4,"Do you believe in celebrating?"))
			list.add(EntityQuestion(65,0,0,4,"What should be celebrated the most in this life?"))
			list.add(EntityQuestion(66,0,0,4,"Describe a memorable celebration in your life."))
			list.add(EntityQuestion(67,0,0,4,"What things do you do to celebrate?"))
			list.add(EntityQuestion(72,0,1,5,"What are your struggles with communication?"))
			list.add(EntityQuestion(73,0,1,5,"Describe what effective communication is between couples."))
			list.add(EntityQuestion(74,0,1,5,"How important is communication?"))
			list.add(EntityQuestion(75,0,1,5,"Why do misunderstandings happen?"))
			list.add(EntityQuestion(76,0,1,6,"How do you define romance?"))
			list.add(EntityQuestion(77,0,1,6,"How much romance do you prefer in a relationship?"))
			list.add(EntityQuestion(78,0,1,6,"What does it mean to be intimate?"))
			list.add(EntityQuestion(79,0,1,6,"Do you have trust issues in being intimate with someone?"))
			list.add(EntityQuestion(80,0,1,6,"What things make you feel close to someone?"))
			list.add(EntityQuestion(81,0,1,6,"How does being intimate with someone change you?"))
			list.add(EntityQuestion(82,0,1,7,"How much sex do you want?"))
			list.add(EntityQuestion(83,0,1,7,"What parts of sex scare you?"))
			list.add(EntityQuestion(84,0,1,7,"What things in sex are you uncomfortable with?"))
			list.add(EntityQuestion(85,0,1,7,"Are you comfortable being naked?"))
			list.add(EntityQuestion(86,0,1,7,"What types of sex excite you?"))
			list.add(EntityQuestion(87,0,1,7,"How was sex portrayed to you growing up?"))
			list.add(EntityQuestion(88,0,1,7,"How does society depict sex?"))
			list.add(EntityQuestion(89,0,1,7,"Do you believe in safe sex?"))
			list.add(EntityQuestion(90,0,1,7,"Is feelings of intimacy necessary for sex?"))
			list.add(EntityQuestion(94,0,2,11,"Tell me about your closest friend."))
			list.add(EntityQuestion(95,0,2,11,"What do you consider is a true friend?"))
			list.add(EntityQuestion(96,0,2,11,"What are some qualities the friendship should have?"))
			list.add(EntityQuestion(97,0,2,11,"What do friends do?"))
			list.add(EntityQuestion(98,0,2,11,"Describe a time when a friend let you down."))
			list.add(EntityQuestion(99,0,2,11,"How many friends do you want to have in your life?"))
			list.add(EntityQuestion(100,0,2,11,"What are some things you want to do with friends more than with a partner?"))
			list.add(EntityQuestion(101,0,2,11,"Do you enjoy going on double dates?"))
			list.add(EntityQuestion(102,0,2,11,"What things do friends do that can be obnoxious?"))
			list.add(EntityQuestion(103,0,2,11,"What is the hardest thing about maintaining friendships?"))
			list.add(EntityQuestion(104,0,2,12,"Describe your immediate family."))
			list.add(EntityQuestion(105,0,2,12,"What are some good qualities of your family."))
			list.add(EntityQuestion(106,0,2,12,"What are some unhealthy aspects of your family."))
			list.add(EntityQuestion(107,0,2,12,"How much family involvement do you think is ok in a couple’s life?"))
			list.add(EntityQuestion(108,0,2,12,"What are your expectations on your partner’s family?"))
			list.add(EntityQuestion(109,0,2,12,"How much do you value family?"))
			list.add(EntityQuestion(110,0,2,12,"What does a health family look like?"))
			list.add(EntityQuestion(111,0,2,12,"What is your favorite family tradition?"))
			list.add(EntityQuestion(112,0,2,12,"What are some rules a family should have?"))
			list.add(EntityQuestion(113,0,2,13,"Do you want kids? Why or why not?"))
			list.add(EntityQuestion(114,0,2,13,"What are some things kids give you?"))
			list.add(EntityQuestion(115,0,2,13,"What things do kids do that annoy you?"))
			list.add(EntityQuestion(116,0,2,13,"Do you believe kids are innocent?"))
			list.add(EntityQuestion(117,0,2,13,"At what age do you believe a child becomes an adult?"))
			list.add(EntityQuestion(118,0,2,13,"How was puberty for you?"))
			list.add(EntityQuestion(119,0,2,13,"What changed when you went to High School?"))
			list.add(EntityQuestion(126,0,2,20,"Describe your relationship with the earth."))
			list.add(EntityQuestion(127,0,2,21,"If you could have one exotic pet, what would it be?"))
			list.add(EntityQuestion(128,0,2,21,"Do you like animals?"))
			list.add(EntityQuestion(129,0,2,21,"Name the top 3 most annoying animals."))
			list.add(EntityQuestion(131,0,3,23,"What is an absolute truth?What is a hopeless situation?"))
			list.add(EntityQuestion(151,0,4,43,"Do you believe there is a God?"))
			list.add(EntityQuestion(152,0,4,43,"How do you know there is a God?"))
			list.add(EntityQuestion(153,0,4,43,"Do you believe God is good? Why?"))
			list.add(EntityQuestion(154,0,4,43,"What are some qualities that come to mind when you think about God?"))
			list.add(EntityQuestion(155,0,4,43,"Is there only one God?"))
			list.add(EntityQuestion(156,0,4,43,"What does God look like?"))
			list.add(EntityQuestion(157,0,4,43,"Does God speak? How does God communicate?"))
			list.add(EntityQuestion(158,0,4,43,"What is the best thing God has done for you?"))
			list.add(EntityQuestion(159,0,4,43,"How do you believe God has failed you?"))
			list.add(EntityQuestion(160,0,4,43,"What is God’s purpose or goal?"))
			list.add(EntityQuestion(161,0,4,44,"Is there such a thing as sins?"))
			list.add(EntityQuestion(162,0,4,44,"What is your definition of a sin?"))
			list.add(EntityQuestion(163,0,4,44,"What is the worst sin?"))
			list.add(EntityQuestion(164,0,4,44,"Do you believe sins can be erased? How?"))
			list.add(EntityQuestion(165,0,4,45,"What are your thoughts about heaven?"))
			list.add(EntityQuestion(166,0,4,45,"Is there a hell?"))
			list.add(EntityQuestion(167,0,4,45,"Describe the afterlife."))
			list.add(EntityQuestion(168,0,4,45,"What is this life for?"))
			list.add(EntityQuestion(169,0,4,45,"Do you believe death is final?"))
			list.add(EntityQuestion(170,0,4,46,"Why is there evil in the world?"))
			list.add(EntityQuestion(171,0,4,46,"What are some of the greatest moral persons who ever lived?"))
			list.add(EntityQuestion(172,0,4,46,"Describe your definition of good."))
			list.add(EntityQuestion(173,0,4,46,"Describe your definition of evil."))
			list.add(EntityQuestion(174,0,4,46,"Describe the perfect human."))
			list.add(EntityQuestion(175,0,4,46,"What is the cure for humanity?"))
			list.add(EntityQuestion(176,0,4,46,"What makes you a good person?"))
			list.add(EntityQuestion(177,0,4,47,"What is the purpose of faith in life?"))
			list.add(EntityQuestion(178,0,4,47,"What is the greatest quality of humankind?"))
			list.add(EntityQuestion(179,0,4,48,"Why are humans distinct from other life forms?"))
			list.add(EntityQuestion(180,0,4,48,"Do you believe humans are divine?"))
			list.add(EntityQuestion(181,0,4,48,"Do you believe in aliens?"))
			list.add(EntityQuestion(182,0,4,48,"Do you believe in ghosts or spirits?"))
			list.add(EntityQuestion(183,0,4,48,"Do you believe there is a spirit world?"))
			list.add(EntityQuestion(184,0,4,49,"What is the purpose of a church?"))
			list.add(EntityQuestion(185,0,4,49,"Do you think all people should be of one religion?"))
			list.add(EntityQuestion(186,0,4,49,"Why are there so many religions?"))
			list.add(EntityQuestion(187,0,4,49,"What is at fault with organized religion?"))
			list.add(EntityQuestion(188,0,4,49,"What good is religion?"))
			list.add(EntityQuestion(189,0,4,49,"What religion did you grow up in?"))
			list.add(EntityQuestion(190,0,4,49,"What is the weirdest religion?"))
			list.add(EntityQuestion(191,0,4,49,"How do you define a cult?"))
			list.add(EntityQuestion(192,0,4,49,"Why do you think people leave a particular religion?"))
			list.add(EntityQuestion(196,0,4,54,"How did the universe begin?"))
			list.add(EntityQuestion(197,0,4,54,"Is earth special?"))
			list.add(EntityQuestion(198,0,4,54,"What are your thoughts about science?"))
			list.add(EntityQuestion(199,0,4,54,"Do you believe in evolution?"))
			list.add(EntityQuestion(200,0,4,48,"Do you believe you are or have a spirit?"))
			list.add(EntityQuestion(201,0,4,48,"What do you believe about the free will of humans?"))
			list.add(EntityQuestion(202,0,4,48,"Have you ever had a unique divine experience?"))
			list.add(EntityQuestion(203,0,4,48,"What is your hope in life?"))
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