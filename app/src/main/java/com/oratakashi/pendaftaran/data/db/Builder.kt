package com.oratakashi.pendaftaran.data.db

import android.database.Cursor
import androidx.core.content.contentValuesOf
import com.oratakashi.pendaftaran.data.model.Students

object Builder {
    fun generate(cursor: Cursor?) : ArrayList<Students>{
        val students = ArrayList<Students>()
        cursor?.apply {
            while (moveToNext()){
                students.add(
                    Students(
                        getString(getColumnIndexOrThrow(DatabaseContract.StudentColumn.ID)),
                        getString(getColumnIndexOrThrow(DatabaseContract.StudentColumn.NAME)),
                        getString(getColumnIndexOrThrow(DatabaseContract.StudentColumn.GENDER)),
                        getString(getColumnIndexOrThrow(DatabaseContract.StudentColumn.TGLLAHIR)),
                        getString(getColumnIndexOrThrow(DatabaseContract.StudentColumn.TMPTLAHIR))
                    )
                )
            }
        }
        return students
    }

    fun generate(data : Students) = contentValuesOf(
        DatabaseContract.StudentColumn.NAME to data.name,
        DatabaseContract.StudentColumn.GENDER to data.gender,
        DatabaseContract.StudentColumn.TGLLAHIR to data.tgllahir,
        DatabaseContract.StudentColumn.TMPTLAHIR to data.tmplahir
    )
}