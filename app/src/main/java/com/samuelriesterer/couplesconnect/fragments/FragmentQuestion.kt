package com.samuelriesterer.couplesconnect.fragments

import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.samuelriesterer.couplesconnect.adapters.ViewPagerAdapter
import com.samuelriesterer.couplesconnect.data.Questions
import com.samuelriesterer.couplesconnect.databinding.FragmentQuestionBinding
import com.samuelriesterer.couplesconnect.general.C
import com.samuelriesterer.couplesconnect.general.Logger
import com.samuelriesterer.couplesconnect.interfaces.InterfaceMain

class FragmentQuestion : Fragment() {
	private var _binding: FragmentQuestionBinding? = null
	private val binding get() = _binding!!
	lateinit var viewPager: ViewPager
	lateinit var viewPagerAdapter: ViewPagerAdapter
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
//		setHasOptionsMenu(false)
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
		viewPagerAdapter = ViewPagerAdapter(requireContext(), Questions.currentDeck)
		viewPager.adapter = viewPagerAdapter

		/* Setup Views */
		binding.questionsBack.visibility = Button.GONE

		/* LISTENERS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
		viewPager.addOnPageChangeListener(object : OnPageChangeListener {
			override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {	}
			override fun onPageSelected(position: Int) {setBackAndForwardVisibility(position)}
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
		binding.questionFilter.setOnClickListener {
			Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "Filter clicked in question")

		}
		binding.questionId.setOnClickListener {
			Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "ID clicked in question")

		}
		binding.questionFavorite.setOnClickListener {
			Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "Favorite clicked in question")

		}
		binding.questionsDice.setOnClickListener {
			Logger.log(C.LOG_V, TAG, object {}.javaClass.enclosingMethod?.name, "Dice clicked in question")
			viewPager.setCurrentItem(1, false)
		}
		binding.questionsBack.setOnClickListener {

		}
		binding.questionsForward.setOnClickListener {

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
	fun setBackAndForwardVisibility(position: Int){
		Logger.log(C.LOG_I, TAG, object {}.javaClass.enclosingMethod?.name, "start")
		when (position) {
			0 -> binding.questionsBack.visibility = Button.GONE
			Questions.currentDeck.size-1 -> binding.questionsForward.visibility = Button.GONE
			else -> {
				binding.questionsBack.visibility = Button.VISIBLE
				binding.questionsForward.visibility = Button.VISIBLE
			}
		}
	}
	/*=======================================================================================================*/
	fun randomNumber(min: Int, max: Int): Int
	{
		Logger.log(C.LOG_I, TAG, object{}.javaClass.enclosingMethod?.name, "start: min = $min; max = $max")
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