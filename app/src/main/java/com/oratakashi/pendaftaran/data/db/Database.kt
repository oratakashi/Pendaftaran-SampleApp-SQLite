package com.oratakashi.pendaftaran.data.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.oratakashi.pendaftaran.BuildConfig
import com.oratakashi.pendaftaran.data.db.DatabaseContract.StudentColumn.Companion.TABLE_NAME

class Database (context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object{
        const val DB_NAME = "db."+ BuildConfig.APPLICATION_ID
        const val DB_VERSION = BuildConfig.VERSION_CODE
    }

    private val createTable = "CREATE TABLE $TABLE_NAME (" +
            "${DatabaseContract.StudentColumn.ID} INTEGER PRIMARY KEY, " +
            "${DatabaseContract.StudentColumn.NAME} TEXT NOT NULL, " +
            "${DatabaseContract.StudentColumn.GENDER} TEXT NOT NULL, " +
            "${DatabaseContract.StudentColumn.TGLLAHIR} TEXT, " +
            "${DatabaseContract.StudentColumn.TMPTLAHIR} TEXT NOT NULL " +
            ")"

    private var context: Context? = null
    private var db: SQLiteDatabase? = null

    init {
        this.context = context
        db = this.writableDatabase
    }

    fun getCursor(query : String) : Cursor {
        return db!!.rawQuery(query, null)
    }

    fun insert(content : ContentValues, table : String){
        db?.insert(table, null, content)
    }

    fun delete(table: String, id : String){
        db?.delete(table, "id = $id", null)
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        this.onCreate(db)
    }
}