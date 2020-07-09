package com.oratakashi.pendaftaran.data.db

import android.content.Context
import com.oratakashi.pendaftaran.data.db.DatabaseCallback.*
import com.oratakashi.pendaftaran.data.db.DatabaseContract.StudentColumn.Companion.TABLE_NAME
import com.oratakashi.pendaftaran.data.model.Students
import kotlinx.coroutines.*

class DatabaseBuilder(context: Context) {
    private val db : Database by lazy {
        Database(context)
    }

    suspend fun all() : MutableList<Students>{
        val queries = GlobalScope.async(Dispatchers.IO) { Builder.generate(db.getCursor(
            "SELECT * FROM $TABLE_NAME"
        )) }
        return queries.await()
    }

    fun insert(data : Students, callback: Callback?= null) = GlobalScope.launch(Dispatchers.IO) {
        db.insert(Builder.generate(data), TABLE_NAME)
        withContext(Dispatchers.Main){
            callback?.callback(Insert)
        }
    }

    fun delete(id : String, callback: Callback?= null) = GlobalScope.launch(Dispatchers.IO) {
        db.delete(TABLE_NAME, id)
        withContext(Dispatchers.Main){
            callback?.callback(Delete)
        }
    }

    interface Callback {
        fun callback(callback : DatabaseCallback)
    }
}