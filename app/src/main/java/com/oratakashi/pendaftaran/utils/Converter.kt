package com.oratakashi.pendaftaran.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object Converter {
    fun decimalFormat(number : Int): String{
        val numberFormat = DecimalFormat("00")
        return numberFormat.format(number.toLong())
    }
    fun toSQLFormat(date : String) : String{
        var format = SimpleDateFormat("dd-MM-yyyy")
        var newDate : Date? = null

        newDate = format.parse(date)

        format = SimpleDateFormat("yyyy-MM-dd")

        return format.format(newDate)
    }
    fun toIndoFormat(date : String) : String{
        var format = SimpleDateFormat("yyyy-MM-dd")
        var newDate : Date? = null

        newDate = format.parse(date)

        format = SimpleDateFormat("dd MMMM yyyy")

        return format.format(newDate)
    }
}