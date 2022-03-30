package com.example.streamlinelife

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.widget.*
import androidx.core.os.bundleOf
import java.util.ArrayList
import androidx.navigation.Navigation.findNavController

//  S. Naveed, “Listing activity using ListView in Android studio - kotlin,” Handy Opinion, 22-May-2021. [Online]. Available: https://handyopinion.com/listing-activity-using-listview-in-android-studio-kotlin/. [Accessed: 25-Mar-2022].
class CustomAdapterReminder(
            context: Context,
            saveInArray: Array<String>,
            saveIDs: Array<Int>,
        ) : BaseAdapter() {
    lateinit var context: Context
    var saveInArray: Array<String>
    var saveIDs: Array<Int>
    var inflater: LayoutInflater

    override fun getCount(): Int {
        return saveInArray.size
    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    @SuppressLint( "ResourceType", "ViewHolder", "InflateParams")
    override fun getView(index: Int, view: View?, p2: ViewGroup?): View {
        var view = view
        view = inflater.inflate(R.layout.showreminderlistview, null)

        //set data in the listview
        val reminder = view.findViewById<TextView>(R.id.showreminderlistview)
        reminder.text = saveInArray[index]
        val saveIDsForDelete = saveIDs[index]

        //button in the list view
        val editbutton = view.findViewById<ImageButton>(R.id.editReminderInAllPages)
        val deletebutton = view.findViewById<ImageButton>(R.id.deleteReminderInAllPages)
        editbutton.setOnClickListener {
            /**
             * bundle is taken from the given so that i can update the values with repect to the key
             * “Pass data between destinations &nbsp;: &nbsp; android developers,” Android Developers. [Online]. Available: https://developer.android.com/guide/navigation/navigation-pass-data. [Accessed: 27-Mar-2022].
             * */
            val bundle = bundleOf("key" to saveIDsForDelete.toString())
            findNavController(view).navigate(R.id.editPage, bundle)
        }
        deletebutton.setOnClickListener {
            alertfunction(view, saveIDsForDelete)
        }

        return view
    }

    /**
     * “Android Alert Dialog using Kotlin,” JournalDev, 12-Jun-2020. [Online]. Available: https://www.journaldev.com/309/android-alert-dialog-using-kotlin. [Accessed: 25-Mar-2022].
     * */
    private fun alertfunction(view: View, saveIDsForDelete: Int) {
        val alertDialog = AlertDialog.Builder(view.context)
        alertDialog.setTitle("Delete this Reminder?")
        alertDialog.setMessage("Do you want to Delete Reminder")
        alertDialog.setPositiveButton("Delete"){
            alertDialog,
            which -> deletefromDB(view, saveIDsForDelete)
        }
        alertDialog.setNegativeButton("Cancel"){
            alertDialog,
            which -> alertDialog.dismiss()
        }
        alertDialog.create()
        alertDialog.show()
    }

    private fun deletefromDB(view: View, saveIDsForDelete: Int) {
        val delelteReminder = DBSupport(view.context)
        val getallReminderFromDatabase: Map<String, ArrayList<String>> = delelteReminder.getAllReminders()
        for((key,value) in getallReminderFromDatabase.entries){
            if(key.toInt()  == saveIDsForDelete){
                delelteReminder.deleteReminder(
                    value[0],
                    value[1] ,
                    value[2],
                    value[3],
                    value[4].toInt(),
                    value[5],
                    value[6],
                    value[7],
                    value[8].toInt(),
                    value[9].toInt()
                )
                break
            }
        }
        Toast.makeText(view.context,"Delete Successfully",Toast.LENGTH_LONG).show()
        findNavController(view).navigate(R.id.allReminderPage)
    }

    init {
        this.saveInArray = saveInArray
        this.saveIDs = saveIDs
        inflater = LayoutInflater.from(context)
    }
}

