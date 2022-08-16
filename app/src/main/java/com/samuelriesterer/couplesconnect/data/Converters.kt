package com.samuelriesterer.couplesconnect.data

import androidx.room.TypeConverter

/// Database uses converters to convert a non-compatible SQL type to a compatible type
object Converters
{
	/*=======================================================================================================*/
	@TypeConverter @JvmStatic fun stringToMListInt(value: String): MutableList<Int>
	{
		val list = mutableListOf<Int>()
		val array = value.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

		for(s in array)
		{
			if(s.isNotEmpty())
			{
				list.add(Integer.parseInt(s))
			}
		}
		return list
	}

	@TypeConverter @JvmStatic fun mlistIntToString(list: MutableList<Int>): String
	{
		var value = ""
		if(list.isNotEmpty())
			value += "${list[0]}"
		for(i in 1 until list.size)
		{
			value += "_${list[i]}"
		}
		return value
	}

	/*=======================================================================================================*/
	@TypeConverter @JvmStatic fun stringToListBoolean(value: String): MutableList<Boolean>
	{
		val list = mutableListOf<Boolean>()
		value.forEach { c ->
			if(c == 'F') list.add(false)
			else list.add(true)
		}
		return list
	}

	@TypeConverter @JvmStatic fun listBooleanToString(list: MutableList<Boolean>): String
	{
		var value = ""
		for(i in list)
		{
			if(i) value += "T"
			else value += "F"
		}
		return value
	}

	/*=======================================================================================================*/
}
