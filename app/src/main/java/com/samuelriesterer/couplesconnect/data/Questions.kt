package com.samuelriesterer.couplesconnect.data

import com.samuelriesterer.couplesconnect.general.C
import com.samuelriesterer.couplesconnect.general.Logger

class Questions {
	companion object {
		val TAG: String = "~*QUESTIONS"
		/*=======================================================================================================*/
		fun initQuestions() {
			Logger.log(C.LOG_I, Data.TAG, object {}.javaClass.enclosingMethod?.name, "start")
			Data.listOfQuestions = mutableListOf(
				Question(0, C.CAT_CONVERSATION, C.SUB_LIFE, 0, Data.listOfQuestionsStrings[0], false),
				Question(3, C.CAT_CONVERSATION, C.SUB_LOVE, 0, Data.listOfQuestionsStrings[1], false),
				Question(6, C.CAT_CONVERSATION, C.SUB_RELATIONSHIPS, 0, Data.listOfQuestionsStrings[2], false),
				Question(9, C.CAT_CONVERSATION, C.SUB_RELIGION, 0, Data.listOfQuestionsStrings[3], false),
				Question(12, C.CAT_CONVERSATION, C.SUB_SELF, 0, Data.listOfQuestionsStrings[4], false),
				Question(15, C.CAT_CONVERSATION, C.SUB_GOVERNMENT, 0, Data.listOfQuestionsStrings[5], false),
				Question(18, C.CAT_DATE, C.SUB_FUN, 0, Data.listOfQuestionsStrings[6], false),
				Question(21, C.CAT_DATE, C.SUB_DATE, 0, Data.listOfQuestionsStrings[7], false),
				Question(24, C.CAT_DATE, C.SUB_ROMANTIC_DATE, 0, Data.listOfQuestionsStrings[8], false),
				Question(27, C.CAT_INTIMACY, C.SUB_FEELINGS, 0, Data.listOfQuestionsStrings[9], false),
				Question(30, C.CAT_INTIMACY, C.SUB_CONTRAST, 0, Data.listOfQuestionsStrings[10], false),
				Question(33, C.CAT_INTIMACY, C.SUB_COMPLAINTS, 0, Data.listOfQuestionsStrings[11], false),
				Question(36, C.CAT_INTIMACY, C.SUB_LOVE_LANGUAGE, 0, Data.listOfQuestionsStrings[12], false),
				Question(39, C.CAT_INTIMACY, C.SUB_CHILDHOOD, 0, Data.listOfQuestionsStrings[13], false),
				Question(42, C.CAT_INTIMACY, C.SUB_TRAUMA, 0, Data.listOfQuestionsStrings[14], false),
				Question(45, C.CAT_INTIMACY, C.SUB_AFFIRMATION, 0, Data.listOfQuestionsStrings[15], false),
				Question(48, C.CAT_INTIMACY, C.SUB_NEEDS, 0, Data.listOfQuestionsStrings[16], false),
				Question(51, C.CAT_SENSUAL, C.SUB_ROMANCE, 0, Data.listOfQuestionsStrings[17], false),
				Question(54, C.CAT_SENSUAL, C.SUB_FLIRTATION, 0, Data.listOfQuestionsStrings[18], false),
				Question(57, C.CAT_SENSUAL, C.SUB_FOREPLAY, 0, Data.listOfQuestionsStrings[19], false),
				Question(60, C.CAT_SENSUAL, C.SUB_SEX, 0, Data.listOfQuestionsStrings[20], false),

				Question(1, C.CAT_CONVERSATION, C.SUB_LIFE, 0, Data.listOfQuestionsStrings[21], false),
				Question(4, C.CAT_CONVERSATION, C.SUB_LOVE, 0, Data.listOfQuestionsStrings[22], false),
				Question(7, C.CAT_CONVERSATION, C.SUB_RELATIONSHIPS, 0, Data.listOfQuestionsStrings[23], false),
				Question(10, C.CAT_CONVERSATION, C.SUB_RELIGION, 0, Data.listOfQuestionsStrings[24], false),
				Question(13, C.CAT_CONVERSATION, C.SUB_SELF, 0, Data.listOfQuestionsStrings[25], false),
				Question(16, C.CAT_CONVERSATION, C.SUB_GOVERNMENT, 0, Data.listOfQuestionsStrings[26], false),
				Question(19, C.CAT_DATE, C.SUB_FUN, 0, Data.listOfQuestionsStrings[27], false),
				Question(22, C.CAT_DATE, C.SUB_DATE, 0, Data.listOfQuestionsStrings[28], false),
				Question(25, C.CAT_DATE, C.SUB_ROMANTIC_DATE, 0, Data.listOfQuestionsStrings[29], false),
				Question(28, C.CAT_INTIMACY, C.SUB_FEELINGS, 0, Data.listOfQuestionsStrings[30], false),
				Question(31, C.CAT_INTIMACY, C.SUB_CONTRAST, 0, Data.listOfQuestionsStrings[31], false),
				Question(34, C.CAT_INTIMACY, C.SUB_COMPLAINTS, 0, Data.listOfQuestionsStrings[32], false),
				Question(37, C.CAT_INTIMACY, C.SUB_LOVE_LANGUAGE, 0, Data.listOfQuestionsStrings[33], false),
				Question(40, C.CAT_INTIMACY, C.SUB_CHILDHOOD, 0, Data.listOfQuestionsStrings[34], false),
				Question(43, C.CAT_INTIMACY, C.SUB_TRAUMA, 0, Data.listOfQuestionsStrings[35], false),
				Question(46, C.CAT_INTIMACY, C.SUB_AFFIRMATION, 0, Data.listOfQuestionsStrings[36], false),
				Question(49, C.CAT_INTIMACY, C.SUB_NEEDS, 0, Data.listOfQuestionsStrings[37], false),
				Question(52, C.CAT_SENSUAL, C.SUB_ROMANCE, 0, Data.listOfQuestionsStrings[38], false),
				Question(55, C.CAT_SENSUAL, C.SUB_FLIRTATION, 0, Data.listOfQuestionsStrings[39], false),
				Question(58, C.CAT_SENSUAL, C.SUB_FOREPLAY, 0, Data.listOfQuestionsStrings[40], false),
				Question(61, C.CAT_SENSUAL, C.SUB_SEX, 0, Data.listOfQuestionsStrings[41], false),

				Question(2, C.CAT_CONVERSATION, C.SUB_LIFE, 0, Data.listOfQuestionsStrings[42], false),
				Question(5, C.CAT_CONVERSATION, C.SUB_LOVE, 0, Data.listOfQuestionsStrings[43], false),
				Question(8, C.CAT_CONVERSATION, C.SUB_RELATIONSHIPS, 0, Data.listOfQuestionsStrings[44], false),
				Question(11, C.CAT_CONVERSATION, C.SUB_RELIGION, 0, Data.listOfQuestionsStrings[45], false),
				Question(14, C.CAT_CONVERSATION, C.SUB_SELF, 0, Data.listOfQuestionsStrings[46], false),
				Question(17, C.CAT_CONVERSATION, C.SUB_GOVERNMENT, 0, Data.listOfQuestionsStrings[47], false),
				Question(20, C.CAT_DATE, C.SUB_FUN, 0, Data.listOfQuestionsStrings[48], false),
				Question(23, C.CAT_DATE, C.SUB_DATE, 0, Data.listOfQuestionsStrings[49], false),
				Question(26, C.CAT_DATE, C.SUB_ROMANTIC_DATE, 0, Data.listOfQuestionsStrings[50], false),
				Question(29, C.CAT_INTIMACY, C.SUB_FEELINGS, 0, Data.listOfQuestionsStrings[51], false),
				Question(32, C.CAT_INTIMACY, C.SUB_CONTRAST, 0, Data.listOfQuestionsStrings[52], false),
				Question(35, C.CAT_INTIMACY, C.SUB_COMPLAINTS, 0, Data.listOfQuestionsStrings[53], false),
				Question(38, C.CAT_INTIMACY, C.SUB_LOVE_LANGUAGE, 0, Data.listOfQuestionsStrings[54], false),
				Question(41, C.CAT_INTIMACY, C.SUB_CHILDHOOD, 0, Data.listOfQuestionsStrings[55], false),
				Question(44, C.CAT_INTIMACY, C.SUB_TRAUMA, 0, Data.listOfQuestionsStrings[56], false),
				Question(47, C.CAT_INTIMACY, C.SUB_AFFIRMATION, 0, Data.listOfQuestionsStrings[57], false),
				Question(50, C.CAT_INTIMACY, C.SUB_NEEDS, 0, Data.listOfQuestionsStrings[58], false),
				Question(53, C.CAT_SENSUAL, C.SUB_ROMANCE, 0, Data.listOfQuestionsStrings[59], false),
				Question(56, C.CAT_SENSUAL, C.SUB_FLIRTATION, 0, Data.listOfQuestionsStrings[60], false),
				Question(59, C.CAT_SENSUAL, C.SUB_FOREPLAY, 0, Data.listOfQuestionsStrings[61], false),
				Question(62, C.CAT_SENSUAL, C.SUB_SEX, 0, Data.listOfQuestionsStrings[62], false)
			)
		}
	}
	}