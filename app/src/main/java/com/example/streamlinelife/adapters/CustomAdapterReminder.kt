package com.example.streamlinelife.adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.os.Build
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import java.util.ArrayList
import androidx.navigation.Navigation.findNavController
import com.example.streamlinelife.R
import com.example.streamlinelife.persistence.DBSupport

//  S. Naveed, “Listing activity using ListView in Android studio - kotlin,” Handy Opinion, 22-May-2021. [Online]. Available: https://handyopinion.com/listing-activity-using-listview-in-android-studio-kotlin/. [Accessed: 25-Mar-2022].
class CustomAdapterReminder(
    context: Context,
    saveInArray: Array<String>,
    saveIDs: Array<Int>,
    pageName: String,
    ) : BaseAdapter() {

    lateinit var context: Context
    var saveInArray: Array<String>
    var saveIDs: Array<Int>
    var inflater: LayoutInflater
    var pageName: String

    override fun getCount(): Int {
        return saveInArray.size
    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint( "ResourceType", "ViewHolder", "InflateParams")
    override fun getView(index: Int, view: View?, p2: ViewGroup?): View {
        var view = view
        view = inflater.inflate(R.layout.showreminderlistview, null)

        //set data in the listview
        val reminder = view.findViewById<TextView>(R.id.showreminderlistview)
        reminder.text = saveInArray[index]
        val saveIDs = saveIDs[index]

        //button in the list view
        val editbutton = view.findViewById<ImageButton>(R.id.editReminderInAllPages)
        val deletebutton = view.findViewById<ImageButton>(R.id.deleteReminderInAllPages)

        editbutton.setOnClickListener {
            /**
             * bundle is taken from the given so that i can update the values with repect to the key
             * “Pass data between destinations &nbsp;: &nbsp; android developers,” Android Developers. [Online]. Available: https://developer.android.com/guide/navigation/navigation-pass-data. [Accessed: 27-Mar-2022].
             * */
            val bundle = bundleOf("key" to saveIDs.toString())
            findNavController(view).navigate(R.id.editPage, bundle)
        }

        deletebutton.setOnClickListener {
            alertfunction(view, saveIDs)
        }

        val completebutton = view.findViewById<ImageButton>(R.id.comletebuttonReminderInAllPages)
        /**
         *  Article title How to hide a button programmatically?
         *   URL: https://stackoverflow.com/questions/6173400/how-to-hide-a-button-programmatically
         *  Website title  Stack Overflow
         *  Date accessed  March 31, 2022
         *  Date published March 01, 1959
         */
        if (pageName.equals("CompletedPage")){
           completebutton.visibility = View.GONE
        }
        completebutton.setOnClickListener {
            completeReminder(view,saveIDs)
        }

        //hide the button in the calender view
        val hidebutton = DBSupport(view.context)
        val getallReminderFromDatabase: Map<String, ArrayList<String>> =  hidebutton.getAllReminders()
        for((key,value) in getallReminderFromDatabase.entries){
            if(value[9].toInt() == 1 && pageName == "CalenderPage"){
                completebutton.visibility = View.GONE
            }
        }

        return view
    }

    private fun completeReminder(view: View, saveIDs: Int, ) {
        val completeReminder = DBSupport(view.context)
        val getallReminderFromDatabase: Map<String, ArrayList<String>> =  completeReminder.getAllReminders()
        for((key,value) in getallReminderFromDatabase.entries){
            if(key.toInt() == saveIDs){
                completeReminder.updateReminder(
                    key.toInt(),
                    value[0],
                    value[1] ,
                    value[2],
                    value[3],
                    value[4].toInt(),
                    "",
                    value[6],
                    value[7],
                    0,
                    1
                )
                break
            }
        }
        Toast.makeText(view.context,"Completed Reminder",Toast.LENGTH_LONG).show()
        findNavController(view).navigate(R.id.completedPage)
    }

    /**
     * “Android Alert Dialog using Kotlin,” JournalDev, 12-Jun-2020. [Online]. Available: https://www.journaldev.com/309/android-alert-dialog-using-kotlin. [Accessed: 25-Mar-2022].
     * Min Soo KimMin Soo Kim 2, Arve WaltinArve Waltin 3, cheechee 1, NantokaNantoka 4, saigopi.mesaigopi.me 11.4k11 gold badge7171 silver badges4949 bronze badges, phapha  53755 silver badges66 bronze badges, Joseph EarlJoseph Earl 23.1k1111 gold badges7575 silver badges8888 bronze badges, Amir Hossein GhasemiAmir Hossein Ghasemi  14.7k99 gold badges5050 silver badges4747 bronze badges, rflexorrflexor 42444 silver badges99 bronze badges, user2671902user2671902, BlundellBlundell 72.6k3030 gold badges201201 silver badges227227 bronze badges, Steven LSteven L 15.1k11 gold badge2020 silver badges1515 bronze badges, 6rchid6rchid 8781212 silver badges1616 bronze badges, EricEric 14.8k77 gold badges6060 silver badges6868 bronze badges, AkhAkh 5, and Sanchit PanchwatikarSanchit Panchwatikar 1, “How to change theme for alertdialog,” Stack Overflow, 01-Jan-1958. [Online]. Available: https://stackoverflow.com/questions/2422562/how-to-change-theme-for-alertdialog. [Accessed: 31-Mar-2022].
     * */
    private fun alertfunction(view: View, saveIDsForDelete: Int) {
        val alertDialog = AlertDialog.Builder(ContextThemeWrapper(view.context, R.style.textcolorInDarkMode))
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
        this.pageName = pageName
        inflater = LayoutInflater.from(context)
    }
}

