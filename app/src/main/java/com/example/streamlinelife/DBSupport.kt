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

    fun updateReminderTitle(oldTitle: String, newTitle: String): Boolean{

        val db = this.writableDatabase

        val valueToUpdate = ContentValues().apply {
            put(TITLE_COL, newTitle)
        }

        val selection = "${TITLE_COL} = ?"
        val selectionValue = arrayOf(oldTitle)

        val count = db.update(REMINDER_TABLE, valueToUpdate,selection,selectionValue)

        if(count == 0){
            return false
        }

        return true
    }

    fun updateReminderDesc(title: String, oldDesc: String, newDesc: String): Boolean{

        val db = this.writableDatabase

        val valueToUpdate = ContentValues().apply {
            put(DESCRIPTION_COL, newDesc)
        }

        val selection = "${TITLE_COL} = ? AND ${DESCRIPTION_COL} = ?"
        val selectionValue = arrayOf(title,oldDesc)

        val count = db.update(REMINDER_TABLE, valueToUpdate,selection,selectionValue)

        if(count == 0){
            return false
        }

        return true
    }

    fun updateReminderLocation(title: String, desc: String, oldLoc: String, newLoc: String): Boolean{

        val db = this.writableDatabase

        val valueToUpdate = ContentValues().apply {
            put(LOCATION_COL, newLoc)
        }

        val selection = "${TITLE_COL} = ? AND ${LOCATION_COL} = ? AND ${DESCRIPTION_COL} = ?"
        val selectionValue = arrayOf(title,oldLoc,desc)

        val count = db.update(REMINDER_TABLE, valueToUpdate,selection,selectionValue)

        if(count == 0){
            return false
        }

        return true
    }

    fun updateReminderDateTime(title: String, desc: String, oldDatatime: String, newDatatime: String): Boolean{

        val db = this.writableDatabase

        val valueToUpdate = ContentValues().apply {
            put(DATETIME_COL, newDatatime)
        }

        val selection = "${TITLE_COL} = ? AND ${DATETIME_COL} = ? AND ${DESCRIPTION_COL} = ?"
        val selectionValue = arrayOf(title,oldDatatime,desc)

        val count = db.update(REMINDER_TABLE, valueToUpdate,selection,selectionValue)

        if(count == 0){
            return false
        }

        return true
    }

    fun updateReminderImportance(title: String, desc: String, oldImp: Int, newImp: Int): Boolean{

        val db = this.writableDatabase

        val valueToUpdate = ContentValues().apply {
            put(IMPORTANCE_COL, newImp)
        }

        val selection = "${TITLE_COL} = ? AND ${DESCRIPTION_COL} = ? AND ${IMPORTANCE_COL} = ?"
        val selectionValue = arrayOf(title, desc, oldImp.toString())

        val count = db.update(REMINDER_TABLE, valueToUpdate,selection,selectionValue)

        if(count == 0){
            return false
        }

        return true
    }

    fun updateReminderRepeat(title: String, desc: String, oldRep: String, newRep: String): Boolean{

        val db = this.writableDatabase

        val valueToUpdate = ContentValues().apply {
            put(REPEAT_COL, newRep)
        }

        val selection = "${TITLE_COL} = ? AND ${DESCRIPTION_COL} = ? AND ${REPEAT_COL} = ?"
        val selectionValue = arrayOf(title,desc,oldRep)

        val count = db.update(REMINDER_TABLE, valueToUpdate,selection,selectionValue)

        if(count == 0){
            return false
        }

        return true
    }

    fun updateReminderGroup(title: String, desc: String, oldGroup: String, newGroup: String): Boolean{

        val db = this.writableDatabase

        val valueToUpdate = ContentValues().apply {
            put(GROUP_COL, newGroup)
        }

        val selection = "${TITLE_COL} = ? AND ${DESCRIPTION_COL} = ? AND ${GROUP_COL} = ?"
        val selectionValue = arrayOf(title,desc,oldGroup)

        val count = db.update(REMINDER_TABLE, valueToUpdate,selection,selectionValue)

        if(count == 0){
            return false
        }

        return true
    }

    fun updateReminderRemind(title: String, desc: String, oldRemind: String, newRemind: String): Boolean{

        val db = this.writableDatabase

        val valueToUpdate = ContentValues().apply {
            put(REMIND_COL, newRemind)
        }

        val selection = "${TITLE_COL} = ? AND ${DESCRIPTION_COL} = ? AND ${REMIND_COL} = ?"
        val selectionValue = arrayOf(title,desc,oldRemind)

        val count = db.update(REMINDER_TABLE, valueToUpdate,selection,selectionValue)

        if(count == 0){
            return false
        }

        return true
    }

    fun updateReminderDeadline(title: String, desc: String, oldDeadline: Int, newDeadline: Int): Boolean{

        val db = this.writableDatabase

        val valueToUpdate = ContentValues().apply {
            put(DEADLINE_COL, newDeadline)
        }

        val selection = "${TITLE_COL} = ? AND ${DESCRIPTION_COL} = ? AND ${DEADLINE_COL} = ?"
        val selectionValue = arrayOf(title,desc,oldDeadline.toString())

        val count = db.update(REMINDER_TABLE, valueToUpdate,selection,selectionValue)

        if(count == 0){
            return false
        }

        return true
    }

    fun updateReminderCompleted(title: String, desc: String, oldCompleted: Int, newCompleted: Int): Boolean{

        val db = this.writableDatabase

        val valueToUpdate = ContentValues().apply {
            put(COMPLETED_COL, newCompleted)
        }

        val selection = "${TITLE_COL} = ? AND ${DESCRIPTION_COL} = ? AND ${COMPLETED_COL} = ?"
        val selectionValue = arrayOf(title,desc,oldCompleted.toString())

        val count = db.update(REMINDER_TABLE, valueToUpdate,selection,selectionValue)

        if(count == 0){
            return false
        }

        return true
    }

    fun deleteReminder(title: String, desc: String, datetime: String, loc: String, imp: Int, repeat: String, group: String, completed: Int): Boolean{

        val db = this.writableDatabase

        val selection = "${TITLE_COL} = ? AND ${DESCRIPTION_COL} = ? AND ${DATETIME_COL} = ? AND ${LOCATION_COL} = ? AND ${IMPORTANCE_COL} = ? AND ${REPEAT_COL} = ? AND ${GROUP_COL} = ? AND ${COMPLETED_COL} = ?"
        val selectionArgs = arrayOf(title, desc, datetime, loc, imp.toString(), repeat, group, completed.toString())

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