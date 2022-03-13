package com.example.streamlinelife

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBSupport(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object DatabaseCompanion {

        // Database constants
        private const val DATABASE_NAME = "STREAMLINE_LIFE"
        private const val DATABASE_VERSION = 1

    }

    override fun onCreate(db: SQLiteDatabase) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVer: Int, newVer: Int) {
        TODO("Not yet implemented")
    }

}