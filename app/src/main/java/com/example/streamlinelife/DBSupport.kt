package com.example.streamlinelife

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.ArrayList


//References

/*
*
* https://www.thegadget360.com/post/sqlite-database-tutorial-insert-delete-update-and-view-data-from-sqlite-db-in-android-studio
*
* https://www.geeksforgeeks.org/android-sqlite-database-in-kotlin/
*
* https://developer.android.com/training/data-storage/sqlite
*
* https://tutorial.eyehunts.com/android/sqlite-database-in-android-kotlin-example/
*
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
        private const val COMPLETED_COL = "completed"
        private const val createRemindersTable = "CREATE TABLE $REMINDER_TABLE (" +
                "$ID_COL INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$TITLE_COL TEXT," +
                "$DESCRIPTION_COL TEXT," +
                "$DATETIME_COL TEXT," +
                "$LOCATION_COL TEXT," +
                "$IMPORTANCE_COL INTEGER," +
                "$REPEAT_COL INTEGER," +
                "$GROUP_COL TEXT," +
                "$COMPLETED_COL INTEGER)"

        // Group table constant
        private const val GROUP_TABLE = "Groups"
        private const val NAME_COL = "name"
        private const val NUMBER_OF_REMINDERS = "numberofreminders"
        private const val createGroupsTable = "CREATE TABLE ${GROUP_TABLE}_TABLE (" +
                "$NAME_COL TEXT PRIMARY KEY," +
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

    fun addReminder(title: String, desc: String, datetime: String, loc: String, imp: Int, repeat: Int, group: String, completed: Int): Boolean{
        val data = ContentValues()

        data.put(TITLE_COL, title)
        data.put(DESCRIPTION_COL, desc)
        data.put(DATETIME_COL, datetime)
        data.put(LOCATION_COL, loc)
        data.put(IMPORTANCE_COL, imp)
        data.put(REPEAT_COL, repeat)
        data.put(GROUP_COL, group)
        data.put(COMPLETED_COL, completed)

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

    fun getAllReminders(): Map<String,ArrayList<String>>{

        val allReminders = mutableMapOf<String,ArrayList<String>>()
        var row = arrayListOf<String>()

        val db = readableDatabase
        val cursor = db.query(REMINDER_TABLE, null,null,null,null,null,null,null)

        if (cursor != null){
            while (cursor.moveToNext()){
                row = arrayListOf<String>()
                val id = cursor.getString(cursor.getColumnIndexOrThrow(ID_COL))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE_COL))
                val desc = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION_COL))
                val datetime = cursor.getString(cursor.getColumnIndexOrThrow(DATETIME_COL))
                val location = cursor.getString(cursor.getColumnIndexOrThrow(LOCATION_COL))
                val importance = cursor.getString(cursor.getColumnIndexOrThrow(IMPORTANCE_COL))
                val repeat = cursor.getString(cursor.getColumnIndexOrThrow(REPEAT_COL))
                val group = cursor.getString(cursor.getColumnIndexOrThrow(GROUP_COL))
                val completed = cursor.getString(cursor.getColumnIndexOrThrow(COMPLETED_COL))
                row.add(title)
                row.add(desc)
                row.add(datetime)
                row.add(location)
                row.add(importance)
                row.add(repeat)
                row.add(group)
                row.add(completed)
                allReminders[id] = row
            }
        }

        cursor.close()
        db.close()
        return allReminders
    }

    fun getNumberofRemindersInGroup(name: String): Int{
        val db = readableDatabase

        val columns = arrayOf(NUMBER_OF_REMINDERS)

        val selection = "${NAME_COL} = ?"
        val selectionValue = arrayOf(name)

        val cursor = db.query(GROUP_TABLE, columns,selection,selectionValue,null,null,null,null)

        if (cursor!=null){
            cursor.moveToNext()
        }

        val numberOfReminders = cursor.getString(cursor.getColumnIndexOrThrow(NUMBER_OF_REMINDERS))

        cursor.close()
        db.close()
        return numberOfReminders.toInt()

    }

    fun getAllCompletedReminders(): Map<String,ArrayList<String>>{

        val completedReminders = mutableMapOf<String,ArrayList<String>>()
        var row = arrayListOf<String>()

        val db = readableDatabase

        val selection = "${COMPLETED_COL} = ?"
        val selectionValue = arrayOf("1")

        val cursor = db.query(REMINDER_TABLE, null,selection,selectionValue,null,null,null,null)

        if (cursor != null){
            while (cursor.moveToNext()){
                row = arrayListOf<String>()
                val id = cursor.getString(cursor.getColumnIndexOrThrow(ID_COL))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE_COL))
                val desc = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION_COL))
                val datetime = cursor.getString(cursor.getColumnIndexOrThrow(DATETIME_COL))
                val location = cursor.getString(cursor.getColumnIndexOrThrow(LOCATION_COL))
                val importance = cursor.getString(cursor.getColumnIndexOrThrow(IMPORTANCE_COL))
                val repeat = cursor.getString(cursor.getColumnIndexOrThrow(REPEAT_COL))
                val group = cursor.getString(cursor.getColumnIndexOrThrow(GROUP_COL))
                val completed = cursor.getString(cursor.getColumnIndexOrThrow(COMPLETED_COL))
                row.add(title)
                row.add(desc)
                row.add(datetime)
                row.add(location)
                row.add(importance)
                row.add(repeat)
                row.add(group)
                row.add(completed)
                completedReminders[id] = row
            }
        }

        cursor.close()
        db.close()
        return completedReminders

    }

    fun getAllGroups(): Map<String,String>{

        val allGroups = mutableMapOf<String,String>()

        val db = readableDatabase

        val cursor = db.query(GROUP_TABLE, null,null,null,null,null,null,null)

        if (cursor != null){
            while (cursor.moveToNext()){
                val name = cursor.getString(cursor.getColumnIndexOrThrow(NAME_COL))
                val numberofReminders = cursor.getString(cursor.getColumnIndexOrThrow(NUMBER_OF_REMINDERS))
                allGroups[name] = numberofReminders
            }
        }

//        val allGroups = mutableMapOf<String,ArrayList<String>>()
//        var row = arrayListOf<String>()
//
//        val db = readableDatabase
//
//        val cursor = db.query(GROUP_TABLE, null,null,null,null,null,null,null)
//
//        if (cursor != null){
//            while (cursor.moveToNext()){
//                row = arrayListOf<String>()
//                val id = cursor.getString(cursor.getColumnIndexOrThrow(ID_COL))
//                val name = cursor.getString(cursor.getColumnIndexOrThrow(NAME_COL))
//                val numberofReminders = cursor.getString(cursor.getColumnIndexOrThrow(NUMBER_OF_REMINDERS))
//                row.add(name)
//                row.add(numberofReminders)
//                allGroups[id] = row
//            }
//        }

        cursor.close()
        db.close()
        return allGroups

    }

    fun getRemindersForAParticularDate(date: String): Map<String,ArrayList<String>>{

        val allReminders = mutableMapOf<String,ArrayList<String>>()
        var row = arrayListOf<String>()

        val db = readableDatabase

        val selection = "${DATETIME_COL} = ?"
        val selectionValue = arrayOf("%" + date + "%")

        val cursor = db.query(REMINDER_TABLE, null,selection,selectionValue,null,null,null,null)

        if (cursor != null){
            while (cursor.moveToNext()){
                row = arrayListOf<String>()
                val id = cursor.getString(cursor.getColumnIndexOrThrow(ID_COL))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE_COL))
                val desc = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION_COL))
                val datetime = cursor.getString(cursor.getColumnIndexOrThrow(DATETIME_COL))
                val location = cursor.getString(cursor.getColumnIndexOrThrow(LOCATION_COL))
                val importance = cursor.getString(cursor.getColumnIndexOrThrow(IMPORTANCE_COL))
                val repeat = cursor.getString(cursor.getColumnIndexOrThrow(REPEAT_COL))
                val group = cursor.getString(cursor.getColumnIndexOrThrow(GROUP_COL))
                val completed = cursor.getString(cursor.getColumnIndexOrThrow(COMPLETED_COL))
                row.add(title)
                row.add(desc)
                row.add(datetime)
                row.add(location)
                row.add(importance)
                row.add(repeat)
                row.add(group)
                row.add(completed)
                allReminders[id] = row
            }
        }

        cursor.close()
        db.close()
        return allReminders

    }

    fun updateNumberofRemindersInGroup(name: String): Boolean{

        var number_of_reminders: Int = getNumberofRemindersInGroup(name)

        number_of_reminders = number_of_reminders + 1

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

    ///////////Below mentioned functions yet to be implemented///////////////////////////////////////////////////////////////////////////////////////////




}