package com.oratakashi.pendaftaran.data.db

import android.provider.BaseColumns

object DatabaseContract {
    internal class StudentColumn : BaseColumns {
        companion object {
            const val TABLE_NAME = "students"
            const val ID : String = "id"
            const val NAME : String = "name"
            const val GENDER : String = "gender"
            const val TGLLAHIR : String = "tgllahir"
            const val TMPTLAHIR : String = "tmptlahir"
        }
    }
}