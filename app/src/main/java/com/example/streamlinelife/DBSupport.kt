package com.example.streamlinelife

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.nio.charset.Charset
import java.util.*


/**
 * References SQLITE
 *
 * Allcodingtutorials, “SQLite database tutorial: Insert, DELETE, update and view data from SQLite DB in Android Studio,” The Gadget 360, 29-Jan-2022. [Online]. Available: https://www.thegadget360.com/post/sqlite-database-tutorial-insert-delete-update-and-view-data-from-sqlite-db-in-android-studio. [Accessed: 27-Mar-2022].
 *
 * “Android sqlite database in Kotlin,” GeeksforGeeks, 16-Sep-2021. [Online]. Available: https://www.geeksforgeeks.org/android-sqlite-database-in-kotlin/. [Accessed: 27-Mar-2022].
 *
 * “Save data using sqlite &nbsp;: &nbsp; android developers,” Android Developers. [Online]. Available: https://developer.android.com/training/data-storage/sqlite. [Accessed: 27-Mar-2022].
 *
 *RohitDegree in Computer Science and Engineer: App Developer and has multiple Programming languages experience. Enthusiasm for technology &amp; like learning technical., “SQLite database in Android Kotlin Example Tutorial - EyeHunts,” Tutorial, 19-May-2021. [Online]. Available: https://tutorial.eyehunts.com/android/sqlite-database-in-android-kotlin-example/. [Accessed: 27-Mar-2022].
 *
 * */

class DBSupport(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
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
        private const val REMIND_COL = "remind"
        private const val DEADLINE_COL = "deadline"
        private const val COMPLETED_COL = "completed"

        private const val createRemindersTable = "CREATE TABLE $REMINDER_TABLE (" +
                "$ID_COL INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$TITLE_COL TEXT," +
                "$DESCRIPTION_COL TEXT," +
                "$DATETIME_COL TEXT," +
                "$LOCATION_COL TEXT," +
                "$IMPORTANCE_COL INTEGER," +
                "$REPEAT_COL TEXT," +
                "$GROUP_COL TEXT," +
                "$REMIND_COL TEXT," +
                "$DEADLINE_COL INTEGER," +
                "$COMPLETED_COL INTEGER)"

        // Group table constant
        private const val GROUP_TABLE = "Groups"
        private const val GROUP_ID_COL = "id"
        private const val NAME_COL = "name"
        private const val NUMBER_OF_REMINDERS = "numberofreminders"
        private const val COLOR_COL = "color"
        private const val ICON_COL = "icon"

        // learn how to use blob
        // R. Peterson, “SQLite data types with example: INT, text, numeric, real, blob,” Guru99, 12-Feb-2022. [Online]. Available: https://www.guru99.com/sqlite-data-types.html. [Accessed: 28-Mar-2022].
        private const val createGroupsTable = "CREATE TABLE $GROUP_TABLE (" +
                "$GROUP_ID_COL INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$NAME_COL STRING," +
                "$NUMBER_OF_REMINDERS INTEGER,"+
                "$COLOR_COL STRING," +
                "$ICON_COL BLOB)"
    }

    // create two table --> reminders Table and Group Table
    @SuppressLint("SQLiteString")
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createRemindersTable)
        db.execSQL(createGroupsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVer: Int, newVer: Int) {
        TODO("Not yet implemented")
    }

    // add reminder with all these fields
    fun addReminder(title: String, desc: String, datetime: String, loc: String, imp: Int, repeat: String, group: String, remind: String, deadline: Int, completed: Int): Boolean{
        val data = ContentValues()

        data.put(TITLE_COL, title)
        data.put(DESCRIPTION_COL, desc)
        data.put(DATETIME_COL, datetime)
        data.put(LOCATION_COL, loc)
        data.put(IMPORTANCE_COL, imp)
        data.put(REPEAT_COL, repeat)
        data.put(GROUP_COL, group)
        data.put(REMIND_COL, remind)
        data.put(DEADLINE_COL, deadline)
        data.put(COMPLETED_COL, completed)

        val db = this.writableDatabase

        var result = db.insert(REMINDER_TABLE, null, data)

        db.close()

        if (result == -1.toLong()){
            return false
        }

        return true
    }

    // get all the reminder -- return the map which Key and Arraylist as value
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
                val remind = cursor.getString(cursor.getColumnIndexOrThrow(REMIND_COL))
                val deadline = cursor.getString(cursor.getColumnIndexOrThrow(DEADLINE_COL))
                val completed = cursor.getString(cursor.getColumnIndexOrThrow(COMPLETED_COL))
                row.add(title)
                row.add(desc)
                row.add(datetime)
                row.add(location)
                row.add(importance)
                row.add(repeat)
                row.add(group)
                row.add(remind)
                row.add(deadline)
                row.add(completed)
                allReminders[id] = row
            }
        }

        cursor.close()
        db.close()
        return allReminders
    }

    //R. Peterson, “SQLite data types with example: INT, text, numeric, real, blob,” Guru99, 12-Feb-2022. [Online]. Available: https://www.guru99.com/sqlite-data-types.html. [Accessed: 28-Mar-2022].
    fun addGroup(name: String, number_of_reminders: Int, color: String, icon: ByteArray): Boolean{
        val data = ContentValues()

        data.put(NAME_COL, name)
        data.put(NUMBER_OF_REMINDERS, number_of_reminders)
        data.put(COLOR_COL, color)
        data.put(ICON_COL, icon)

        val db = this.writableDatabase

        var result = db.insert(GROUP_TABLE, null, data)

        db.close()

        if (result == -1.toLong()){
            return false
        }

        return true
    }

    // get all group
    fun getAllGroups():  Map<String,ArrayList<String>>{
        val allGroups = mutableMapOf<String,ArrayList<String>>()
        var row = arrayListOf<String>()

        val db = readableDatabase
        val cursor = db.query(GROUP_TABLE, null,null,null,null,null,null,null)

        if (cursor != null){
            while (cursor.moveToNext()){
                row = arrayListOf<String>()
                val id = cursor.getString(cursor.getColumnIndexOrThrow(GROUP_ID_COL))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(NAME_COL))
                val numbers = cursor.getString(cursor.getColumnIndexOrThrow(NUMBER_OF_REMINDERS))
                val color = cursor.getString(cursor.getColumnIndexOrThrow(COLOR_COL))

                // this is my logic where i am converting blob into a one string separated by commans
                // so that i can get all the values of it
                var store_blob = ""
                for(i in cursor.getBlob(cursor.getColumnIndexOrThrow(ICON_COL))){
                    store_blob += "$i,"
                }
                val icon = store_blob
                row.add(name)
                row.add(numbers)
                row.add(color)
                row.add(icon)
                allGroups[id] = row
            }
        }

        cursor.close()
        db.close()
        return allGroups
    }

    // get all numbers of reminder present int he group
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

    // use in the edit page
    fun updateReminder(id: Int, title: String, desc: String, datetime: String, loc: String, imp: Int, repeat: String, remind: String, groupName: String, deadline: Int, completed: Int): Boolean{

        val db = this.writableDatabase

        val valueToUpdate = ContentValues().apply {
            put(TITLE_COL, title)
            put(DESCRIPTION_COL, desc)
            put(LOCATION_COL, loc)
            put(DATETIME_COL, datetime)
            put(IMPORTANCE_COL, imp)
            put(REPEAT_COL, repeat)
            put(GROUP_COL, groupName)
            put(REMIND_COL, remind)
            put(DEADLINE_COL, deadline)
            put(COMPLETED_COL, completed)
        }

        val selection = "${ID_COL} = ?"
        val selectionValue = arrayOf(id.toString())

        val count = db.update(REMINDER_TABLE, valueToUpdate,selection,selectionValue)

        if(count == 0){
            return false
        }

        return true
    }

    //delete reminder from the table
    fun deleteReminder(title: String, desc: String, datetime: String, loc: String, imp: Int, repeat: String, group: String, remind: String, deadline: Int, completed: Int): Boolean{

        val db = this.writableDatabase

        val selection = "${TITLE_COL} = ? AND ${DESCRIPTION_COL} = ? AND ${DATETIME_COL} = ? AND ${LOCATION_COL} = ? AND ${IMPORTANCE_COL} = ? AND ${REPEAT_COL} = ? AND ${GROUP_COL} = ? AND ${REMIND_COL} = ? AND ${DEADLINE_COL} = ? AND ${COMPLETED_COL} = ?"
        val selectionArgs = arrayOf(title, desc, datetime, loc, imp.toString(), repeat, group, remind, deadline.toString(), completed.toString())

        val deletedRow = db.delete(REMINDER_TABLE, selection, selectionArgs)

        if(deletedRow == 0){
            return false
        }

        return true
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

        if(count == 0){
            return false
        }

        return true

    }

    fun getAllRemindersofParticularGroup(groupName: String): Map<String,ArrayList<String>>{

        val allReminders = mutableMapOf<String,ArrayList<String>>()
        var row = arrayListOf<String>()

        val db = readableDatabase

        val selection = "${GROUP_COL} = ?"
        val selectionValue = arrayOf(groupName)

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
                val remind = cursor.getString(cursor.getColumnIndexOrThrow(REMIND_COL))
                val deadline = cursor.getString(cursor.getColumnIndexOrThrow(DEADLINE_COL))
                val completed = cursor.getString(cursor.getColumnIndexOrThrow(COMPLETED_COL))
                row.add(title)
                row.add(desc)
                row.add(datetime)
                row.add(location)
                row.add(importance)
                row.add(repeat)
                row.add(group)
                row.add(remind)
                row.add(deadline)
                row.add(completed)
                allReminders[id] = row
            }
        }

        cursor.close()
        db.close()
        return allReminders

    }

    fun updateGroupName(oldGroupName: String, newGroupName: String): Boolean{

        val db = this.writableDatabase

        val valueToUpdate = ContentValues().apply {
            put(NAME_COL, newGroupName)
        }

        val selection = "${NAME_COL} = ?"
        val selectionValue = arrayOf(oldGroupName)

        val count = db.update(GROUP_TABLE, valueToUpdate,selection,selectionValue)

        if(count == 0){
            return false
        }

        return true
    }

    fun deleteGroup(groupName: String): Boolean{

        val db = this.writableDatabase

        val selection = "${NAME_COL} = ?"
        val selectionArgs = arrayOf(groupName)

        val deletedRow = db.delete(GROUP_TABLE, selection, selectionArgs)

        if(deletedRow == 0){
            return false
        }

        return true
    }

    fun deleteCompletedReminders(): Boolean{

        val db = this.writableDatabase

        val selection = "${COMPLETED_COL} = ?"
        val selectionArgs = arrayOf("1")

        val deletedRow = db.delete(REMINDER_TABLE, selection, selectionArgs)

        if(deletedRow == 0){
            return false
        }

        return true
    }

    fun deleteAllRemindersofParticularGroup(groupName: String): Boolean{

        val db = this.writableDatabase

        val selection = "${GROUP_COL} = ?"
        val selectionArgs = arrayOf(groupName)

        val deletedRow = db.delete(REMINDER_TABLE, selection, selectionArgs)

        if(deletedRow == 0){
            return false
        }

        return true
    }

}