package com.samuelriesterer.couplesconnect.fragments

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.samuelriesterer.couplesconnect.R
import com.samuelriesterer.couplesconnect.adapters.AdapterViewPageQuestions
import com.samuelriesterer.couplesconnect.data.DatabaseOps
import com.samuelriesterer.couplesconnect.data.Data
import com.samuelriesterer.couplesconnect.databinding.DialogSortBinding
import com.samuelriesterer.couplesconnect.databinding.FragmentQuestionBinding
import com.samuelriesterer.couplesconnect.general.C
import com.samuelriesterer.couplesconnect.general.Logger
import com.samuelriesterer.couplesconnect.interfaces.InterfaceMain

class FragmentQuestion : Fragment() {
	private var _binding: FragmentQuestionBinding? = null
	private val binding get() = _binding!!
	lateinit var viewPager: ViewPager
	lateinit var adapterViewPageQuestions: AdapterViewPageQuestions
	var currentPosition = 0
	val TAG: String = "~*FRAGMENT_QUESTION"

	/*=======================================================================================================*/
	/* INTERFACE                                                                                             */
	/*=======================================================================================================*/
	lateinit var interfaceMain: InterfaceMain

	/*=======================================================================================================*/
	/* ON ATTACH                                                                                             */
	/*=======================================================================================================*/
	override fun onAttach(context: Context) {
		super.onAttach(context)
		try {
			interfaceMain = context as InterfaceMain
		}
		catch (e: ClassCastException) {
			throw ClassCastException("$context must implement interfaceMain")
		}
	}

	/*=======================================================================================================*/
	/* ON CREATE                                                                                             */
	/*=======================================================================================================*/
	override fun onCreate(savedInstanceState: Bundle?) {
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		super.onCreate(savedInstanceState)
		Data.makeDeck(Data.currentConfiguration)
	}

	/*=======================================================================================================*/
	/* ON CREATE VIEW                                                                                        */
	/*=======================================================================================================*/
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		_binding = FragmentQuestionBinding.inflate(inflater, container, false)
		val root: View = binding.root
		/* INITIALIZATION ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
		/* Variables */
		viewPager = binding.pager
		adapterViewPageQuestions = AdapterViewPageQuestions(requireContext(), Data.currentDeck)
		viewPager.adapter = adapterViewPageQuestions
		/* Setup Views */
//		interfaceMain.showActionBar()

		// Displays a text message if there are no categories/subcategories selected:
		if(Data.currentDeck.isEmpty()) {
			binding.fragmentQuestionLayout.visibility = RelativeLayout.GONE
		}
		else {
			binding.fragmentQuestionLayout.visibility = RelativeLayout.VISIBLE
			setFavoriteIcon(0)
			setQuestionPositionText(currentPosition)

			setCategoryIcon()

			/* LISTENERS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
			viewPager.addOnPageChangeListener(object : OnPageChangeListener {
				override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
				override fun onPageSelected(position: Int) {
					currentPosition = position
					setQuestionPositionText(currentPosition)
					setFavoriteIcon(position)
				}

				override fun onPageScrollStateChanged(state: Int) {}
			})

			binding.questionsCategory.setOnClickListener {
				Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "Category clicked in question")
				interfaceMain.switchFragments(C.FRAG_CATEGORY)
			}
			binding.questionsSubcategory.setOnClickListener {
				Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "Subcategory clicked in question")
				interfaceMain.switchFragments(C.FRAG_SUBCATEGORY)
			}
			binding.questionsPosition.setOnClickListener {
				Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "Position clicked in question")
			}
			binding.questionId.setOnClickListener {
				Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "ID clicked in question")
			}
			binding.questionFavorite.setOnClickListener {
				Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "Favorite clicked in question")
				val newValue = !Data.currentDeck[currentPosition].favorite
				Data.listOfQuestions.find { it.id == Data.currentDeck[currentPosition].id }?.favorite = newValue
				Data.currentDeck[currentPosition].favorite = newValue
				DatabaseOps.updateFavorite(Data.currentDeck[currentPosition].id, newValue)
				setFavoriteIcon(currentPosition)
			}
			binding.questionShare.setOnClickListener {
				Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "Share clicked in question")
				val sharingIntent = Intent(Intent.ACTION_SEND)
				sharingIntent.type = "text/plain"
				val shareBody = "${Data.currentDeck[currentPosition].question} (${getString(R.string.app_name)})"
				Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, " shareBody = $shareBody")
				sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "${getString(R.string.share_subject)} ")
				sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
				startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_intent)))
			}
			binding.questionSort.setOnClickListener {
				Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "Sort clicked in question")
				dialogSort()
			}
		}

		return root
	}

	/*=======================================================================================================*/
	/* ON VIEW CREATED                                                                                       */
	/*=======================================================================================================*/
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		super.onViewCreated(view, savedInstanceState)
	}
	/*=======================================================================================================*/
	fun setCategoryIcon() {
		// Sets the top category title and icon and subcategory
		//TODO set category icon
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		binding.questionsCategory.text = ""
		binding.questionsCategory.background
		binding.questionsCategoryIcon
		binding.questionsSubcategory.text = ""

	}
	/*=======================================================================================================*/
	fun setQuestionPositionText(position: Int) {
		// Sets the text display of the current question position and total questions in the deck (e.g. "1/12")
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		binding.questionsPosition.text = "${position + 1}/${Data.currentDeck.size}"
		binding.questionId.text = "${Data.currentDeck[position].id + 1}"
	}

	/*=======================================================================================================*/
	fun setFavoriteIcon(position: Int) {
		// Sets the favorite icon for the current question
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		//		val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_heart_on_sel)
		if(Data.currentDeck[position].favorite)
			binding.questionFavorite.setBackgroundResource(R.drawable.ic_heart_on_sel)
		else
			binding.questionFavorite.setBackgroundResource(R.drawable.ic_heart_off_sel)
	}

	/*=======================================================================================================*/
	fun dialogSort() {
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		/* INITIALIZATION ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
		/* Binding */
		val dialog = Dialog(requireContext())
		val bind: DialogSortBinding = DialogSortBinding.inflate(layoutInflater)
		dialog.setContentView(bind.root)
		/* Variables */
		val sortCheckBoxes = arrayListOf(bind.dialogSortId, bind.dialogSortAlphabet, bind.dialogSortFavorites)
		val filterCheckBoxes = arrayListOf(bind.dialogFilterFavorites, bind.dialogFilterUnrated, bind.dialogFilterAll)
		val changedConfiguration = Data.currentConfiguration.copy()
		/* Setup Views */
		if(Data.currentConfiguration.currentSortOrder == C.SORT_SHUFFLE)
			toggleCheckBoxes(sortCheckBoxes, null)
		else
			toggleCheckBoxes(sortCheckBoxes, sortCheckBoxes[Data.currentConfiguration.currentSortOrder])

		toggleCheckBoxes(filterCheckBoxes, filterCheckBoxes[Data.currentConfiguration.currentFilterType])
		/* LISTENERS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
		/* Sort */
		bind.dialogSortId.setOnClickListener { toggleCheckBoxes(sortCheckBoxes, bind.dialogSortId) }
		bind.dialogSortAlphabet.setOnClickListener { toggleCheckBoxes(sortCheckBoxes, bind.dialogSortAlphabet) }
		bind.dialogSortFavorites.setOnClickListener { toggleCheckBoxes(sortCheckBoxes, bind.dialogSortFavorites) }
		bind.dialogSortShuffle.setOnClickListener {
			toggleCheckBoxes(sortCheckBoxes, null) // Turns all checkboxes off
		}
		/* Filter */
		bind.dialogFilterFavorites.setOnClickListener { toggleCheckBoxes(filterCheckBoxes, bind.dialogFilterFavorites) }
		bind.dialogFilterUnrated.setOnClickListener { toggleCheckBoxes(filterCheckBoxes, bind.dialogFilterUnrated) }
		bind.dialogFilterAll.setOnClickListener { toggleCheckBoxes(filterCheckBoxes, bind.dialogFilterAll) }
		/* OK */
		bind.dialogSortOk.setOnClickListener {
			// Set temp settings:
			when {
				bind.dialogSortId.isChecked -> changedConfiguration.currentSortOrder = C.SORT_ID
				bind.dialogSortAlphabet.isChecked -> changedConfiguration.currentSortOrder = C.SORT_ALPHABETICALLY
				bind.dialogSortFavorites.isChecked -> changedConfiguration.currentSortOrder = C.SORT_FAVORITES
				else -> changedConfiguration.currentSortOrder = C.SORT_SHUFFLE
			}

			when {
				bind.dialogFilterFavorites.isChecked -> changedConfiguration.currentFilterType = C.FILTER_FAVORITES_ONLY
				bind.dialogFilterUnrated.isChecked -> changedConfiguration.currentFilterType = C.FILTER_UNRATED_ONLY
				else -> changedConfiguration.currentFilterType = C.FILTER_ALL_TYPES
			}
			// Compare changed settings with original settings:
			if(!Data.currentConfiguration.compare(changedConfiguration)) {
				// Set Setting if not the same:
				Data.currentConfiguration = changedConfiguration.copy()
				// Save Setting:
				DatabaseOps.insertConfiguration(Data.currentConfiguration)
				Data.makeDeck(Data.currentConfiguration)
				adapterViewPageQuestions.notifyDataSetChanged()
				currentPosition = 0
				viewPager.setCurrentItem(currentPosition)
				setQuestionPositionText(currentPosition)
				setFavoriteIcon(0)
			}
			dialog.dismiss()
		}

		dialog.show()
	}

	/*=======================================================================================================*/
	fun toggleCheckBoxes(checkBoxes: ArrayList<CheckBox>, cb: CheckBox?) {
		// Toggles all checkboxes off except for passed checkbox (pass null to turn all off)
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		for(i in checkBoxes.indices) {
			checkBoxes[i].isChecked = checkBoxes[i] == cb
		}
	}

	/*=======================================================================================================*/
	fun randomNumber(min: Int, max: Int): Int {
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start: min = $min; max = $max")
		return (min..max).random()
	}

	/*=======================================================================================================*/
	/* OVERRIDE LIFECYCLE METHODS                                                                            */
	/*=======================================================================================================*/
	//<editor-fold desc="Override Lifecycle Methods">
	override fun onPause() {
		Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		super.onPause()
	}

	/*=======================================================================================================*/
	override fun onResume() {
		Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		super.onResume()
	}

	/*=======================================================================================================*/
	override fun onStart() {
		Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		super.onStart()
	}

	/*=======================================================================================================*/
	override fun onStop() {
		Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		super.onStop()
	}

	/*=======================================================================================================*/
	override fun onDestroy() {
		Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		super.onDestroy()
		_binding = null
	}
	/*=======================================================================================================*/
	//</editor-fold>
}