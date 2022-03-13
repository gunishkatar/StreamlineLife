package com.example.streamlinelife

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBSupport(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object DatabaseCompanion {

        // Database constants
        private const val DATABASE_NAME = "STREAMLINE_LIFE"
        private const val DATABASE_VERSION = 1

        // Reminder table constants
        private const val REMINDER_TABLE = "Reminders"
        private const val ID_COL = "id";
        private const val TITLE_COL = "title";
        private const val DESCRIPTION_COL = "description";
        private const val DATETIME_COL = "datetime";
        private const val LOCATION_COL = "location";
        private const val IMPORTANCE_COL = "importance";
        private const val LIST_COL = "list_number";
        private const val createRemindersTable = "CREATE TABLE $REMINDER_TABLE (" +
                "$ID_COL INTEGER PRIMARY KEY," +
                "$TITLE_COL TEXT," +
                "$DESCRIPTION_COL TEXT," +
                "$DATETIME_COL DATETIME," +
                "$LOCATION_COL TEXT," +
                "$IMPORTANCE_COL INTEGER," +
                "$LIST_COL TEXT)"

        // Group table constant
        private const val GROUP_TABLE = "Groups"
        private const val NAME_COL = "name";
        private const val createGroupsTable = "CREATE TABLE ${GROUP_TABLE}_TABLE (" +
                "$ID_COL INTEGER PRIMARY KEY," +
                "$NAME_COL TEXT)"

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createRemindersTable);
        db.execSQL(createGroupsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVer: Int, newVer: Int) {
        TODO("Not yet implemented")
    }

}