package com.example.streamlinelife

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


//References

/*
*
* https://www.thegadget360.com/post/sqlite-database-tutorial-insert-delete-update-and-view-data-from-sqlite-db-in-android-studio
*
* https://www.geeksforgeeks.org/android-sqlite-database-in-kotlin/
*
*
* */



class DBSupport(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object DatabaseCompanion {

        // Database constants
        private const val DATABASE_NAME = "STREAMLINE_LIFE"
        private const val DATABASE_VERSION = 1

        // Reminder table constants
        private const val REMINDER_TABLE = "Reminders"
        private const val ID_COL = "id"
        private const val TITLE_COL = "title"
        private const val DESCRIPTION_COL = "description"
        private const val DATETIME_COL = "datetime"
        private const val LOCATION_COL = "location"
        private const val IMPORTANCE_COL = "importance"
        private const val REPEAT_COL = "repeat"
        private const val GROUP_COL = "list_number"
        private const val createRemindersTable = "CREATE TABLE $REMINDER_TABLE (" +
                "$ID_COL INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$TITLE_COL TEXT," +
                "$DESCRIPTION_COL TEXT," +
                "$DATETIME_COL TEXT," +
                "$LOCATION_COL TEXT," +
                "$IMPORTANCE_COL INTEGER," +
                "$REPEAT_COL INTEGER," +
                "$GROUP_COL TEXT)"

        // Group table constant
        private const val GROUP_TABLE = "Groups"
        private const val NAME_COL = "name"
        private const val NUMBER_OF_REMINDERS = "numberofreminders"
        private const val createGroupsTable = "CREATE TABLE ${GROUP_TABLE}_TABLE (" +
                "$ID_COL INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$NAME_COL TEXT" +
                "$NUMBER_OF_REMINDERS INTEGER)"

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createRemindersTable)
        db.execSQL(createGroupsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVer: Int, newVer: Int) {
        TODO("Not yet implemented")
    }

//    override fun onDestroy() {
//        this.close()
//        super.onDestroy()
//    }

    fun addReminder(title: String, desc: String, datetime: String, loc: String, imp: Int, repeat: Boolean, group: String): Boolean{
        val data = ContentValues()

        data.put(TITLE_COL, title)
        data.put(DESCRIPTION_COL, desc)
        data.put(DATETIME_COL, datetime)
        data.put(LOCATION_COL, loc)
        data.put(IMPORTANCE_COL, imp)
        data.put(REPEAT_COL, repeat)
        data.put(GROUP_COL, group)

        val db = this.writableDatabase

        var result = db.insert(REMINDER_TABLE, null, data)

        db.close()

        if (result == -1.toLong()){
            return false
        }

        return true
    }

    fun addGroup(name: String, number_of_reminders: Int): Boolean{
        val data = ContentValues()

        data.put(NAME_COL, name)
        data.put(NUMBER_OF_REMINDERS, number_of_reminders)

        val db = this.writableDatabase

        var result = db.insert(GROUP_TABLE, null, data)

        db.close()

        if (result == -1.toLong()){
            return false
        }

        return true
    }

    fun getAllReminders() {

        val db = this.writableDatabase
        val cursor = db.query(REMINDER_TABLE, null,null,null,null,null,null,null)

        val itemIds = mutableListOf<Long>()
        with(cursor) {
            while (moveToNext()) {
                val itemId = getLong(getColumnIndexOrThrow(ID_COL))
                itemIds.add(itemId)
            }
        }
        cursor.close()
    }

    fun getNumberofRemindersInGroup(name: String): Long{
        val db = this.writableDatabase

        val columns = arrayOf(NUMBER_OF_REMINDERS)

        val selection = "${NAME_COL} = ?"
        val selectionValue = arrayOf(name)

        val cursor = db.query(GROUP_TABLE, columns,selection,selectionValue,null,null,null,null)

        cursor.moveToNext()
        val numberOfReminders = cursor.getLong(cursor.getColumnIndexOrThrow(NUMBER_OF_REMINDERS))

        return numberOfReminders

    }

    fun updateNumberofRemindersInGroup(name: String, number_of_reminders: Int): Boolean{
        val db = this.writableDatabase

        val valueToUpdate = ContentValues().apply {
            put(NUMBER_OF_REMINDERS, number_of_reminders)
        }

        val selection = "${NAME_COL} = ?"
        val selectionValue = arrayOf(name)

        val count = db.update(GROUP_TABLE, valueToUpdate,selection,selectionValue)

        if(count > 1){
            return false
        }

        return true

    }



}