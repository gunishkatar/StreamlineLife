package com.example.streamlinelife.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.streamlinelife.R
import com.example.streamlinelife.persistence.DBSupport
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [editReminder.newInstance] factory method to
 * create an instance of this fragment.
 */
class editReminder : Fragment() {
    lateinit var reminderDateInputField: TextView
    private var addTime = ""
    private var deadlinenum = 0
    private var completenum = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_reminder, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //buttons
        val backbutton = view.findViewById<Button>(R.id.cancel_buttonInCreateReminder)
        backbutton.text = "Back"
        val updatebutton = view.findViewById<Button>(R.id.saveReminderInCreateReminder)
        updatebutton.text = "Update"

        /**
         * getting key from the custom adapter
         * bundle is taken from the given so that i can update the values with repect to the key
         * “Pass data between destinations &nbsp;: &nbsp; android developers,” Android Developers. [Online]. Available: https://developer.android.com/guide/navigation/navigation-pass-data. [Accessed: 27-Mar-2022].
         * */
        val key = arguments?.getString("key")?.toInt()

        // reminder title
        val reminderTitleInputField = view.findViewById<TextView>(R.id.reminderTitleInputFieldInCreateReminder)

        // reminder description
        val reminderDescriptionInputField = view.findViewById<TextView>(R.id.reminderDescriptionInputFieldInCreateReminder)

        // reminder Date and time input
        reminderDateInputField = view.findViewById(R.id.reminderDateInputFieldInCreateReminder)

        // reminder location input
        val locationInputField = view.findViewById<TextView>(R.id.locationInputFieldInCreateReminder)

        val groupName = view.findViewById<TextView>(R.id.reminderGroupNameInCreateReminder)

        /**
         * Reference for dropdown menu:
         * “Exposed drop-down menu in Android,” GeeksforGeeks, 23-Jun-2021. [Online]. Available: https://www.geeksforgeeks.org/exposed-drop-down-menu-in-android/. [Accessed: 24-Mar-2022].
         * “MultiAutoCompleteTextView in Android with example,” GeeksforGeeks, 18-Feb-2021. [Online]. Available: https://www.geeksforgeeks.org/multiautocompletetextview-in-android-with-example/. [Accessed: 24-Mar-2022].
         * */
        // importance dropdown
        val reminderImportance = resources.getStringArray(R.array.reminder_importance)
        val arrayAdapterImportance = ArrayAdapter(view.context, R.layout.dropdown_item, reminderImportance)
        val autocompleteImportance = view.findViewById<AutoCompleteTextView>(R.id.reminderPriorityInCreateReminder)
        autocompleteImportance.setAdapter(arrayAdapterImportance)

        // repeat reminder
        val repeatDays = resources.getStringArray(R.array.reminder_repeat)
        val arrayAdapterRepeatDays: ArrayAdapter<String> = ArrayAdapter(view.context, R.layout.dropdown_item, repeatDays)
        val multiAutocompleteRepeatDays = view.findViewById<MultiAutoCompleteTextView>(R.id.reminderRepeatDaysInCreateReminder)
        multiAutocompleteRepeatDays.setAdapter(arrayAdapterRepeatDays)
        multiAutocompleteRepeatDays.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())

        // remind me drop down
        val reminderOccurrence = resources.getStringArray(R.array.reminder_occurrence)
        val arrayAdapterForOccurrence: ArrayAdapter<String> = ArrayAdapter(view.context, R.layout.dropdown_item, reminderOccurrence)
        val multiAutocompleteOccurrence = view.findViewById<MultiAutoCompleteTextView>(R.id.autoCompleteTextViewInCreateReminder)
        multiAutocompleteOccurrence.setAdapter(arrayAdapterForOccurrence)
        multiAutocompleteOccurrence.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())

        // db for the edit page
        val database = DBSupport(requireContext())
        val getallReminderFromDatabase: Map<String,ArrayList<String>> = database.getAllReminders()
        for ((pos,value) in getallReminderFromDatabase){
            if (pos.toInt() == key){
                when (value[4]) {
                    "3" -> {
                        value[4] = "High"
                    }
                    "2" -> {
                        value[4] = "Medium"
                    }
                    "1" -> {
                        value[4] = "Low"
                    }
                    else ->{
                        value[4] = ""
                    }
                }
                reminderTitleInputField.text = value[0]
                reminderDescriptionInputField.text = value[1]
                reminderDateInputField.text = value[2]
                locationInputField.text = value[3]
                // i just took "false" from the comments in the given link -->  https://github.com/material-components/material-components-android/issues/1007
                autocompleteImportance.setText(value[4],false)
                multiAutocompleteRepeatDays.setText(value[5])
                groupName.text = value[6]
                multiAutocompleteOccurrence.setText(value[7])
                deadlinenum = value[8].toInt()
                completenum = value[9].toInt()
            }
        }

        /**
         *
         * Reference for the Date and Time
         * Hendy, HendyHendy 25511 gold badge55 silver badges2727 bronze badges, Siim PunisteSiim Puniste  1901111 bronze badges, and Vishnu SainiVishnu Saini 12955 bronze badges, “Date picker dialog not showing in Android fragment,” Stack Overflow, 01-Jun-1966. [Online]. Available: https://stackoverflow.com/questions/51646283/date-picker-dialog-not-showing-in-android-fragment. [Accessed: 24-Mar-2022].
         * “Datepicker in Kotlin,” GeeksforGeeks, 21-Jan-2022. [Online]. Available: https://www.geeksforgeeks.org/datepicker-in-kotlin/. [Accessed: 24-Mar-2022].
         * “How to disable previous or future dates in Datepicker in Android?,” GeeksforGeeks, 15-Jul-2021. [Online]. Available: https://www.geeksforgeeks.org/how-to-disable-previous-or-future-dates-in-datepicker-in-android/. [Accessed: 24-Mar-2022].
         * “Pickers &nbsp;: &nbsp; android developers,” Android Developers. [Online]. Available: https://developer.android.com/guide/topics/ui/controls/pickers. [Accessed: 24-Mar-2022].
         * “Time Picker Dialog in Android,” GeeksforGeeks, 06-Jun-2021. [Online]. Available: https://www.geeksforgeeks.org/time-picker-dialog-in-android/. [Accessed: 24-Mar-2022].
         *
         * */
        reminderDateInputField.inputType = InputType.TYPE_NULL
        reminderDateInputField.keyListener = null
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        reminderDateInputField.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                {
                        view, year, monthOfYear, dayOfMonth ->
                        val month = monthOfYear + 1 // month count given less one e.g. august then give month no 7
                        var dayStr = dayOfMonth.toString()
                        var monthStr = month.toString()
                        if (dayStr.length == 1) dayStr = "0$dayStr"
                        if (monthStr.length == 1) monthStr = "0$monthStr"

                        // dd/mm/yyyy format set.
                        reminderDateInputField.text = "$year-$monthStr-$dayStr,$addTime"
                }, year, month, day
            )

            // select from the current date to future date they cant select old date for adding reminders
            datePickerDialog.datePicker.minDate = c.timeInMillis
            datePickerDialog.show()

            val timePickerDialog = TimePickerDialog(
                requireContext(),
                timerpick,
                12,
                10,
                false
            )
            timePickerDialog.show()
        }

        // button back and update
        backbutton.setOnClickListener{
            findNavController().navigate(R.id.action_editPage_to_allReminderPage)
        }
        updatebutton.setOnClickListener {
            val updateValues = ArrayList<String>()
            updateValues.add(0,reminderTitleInputField.text.toString())
            updateValues.add(1,reminderDescriptionInputField.text.toString())
            updateValues.add(2, reminderDateInputField.text.toString())
            updateValues.add(3,locationInputField.text.toString())
            when (autocompleteImportance.text.toString()) {
                "High" -> {
                    updateValues.add(4,"3")
                }
                "Medium" -> {
                    updateValues.add(4,"2")
                }
                "Low" -> {
                    updateValues.add(4,"1")
                }
                else ->{
                    updateValues.add(4,"0")
                }
            }
            val inputsRepeatDays: List<String> =  multiAutocompleteRepeatDays.text.toString().split("\\s*,\\s*")
            var saverepeatDaysList = ""
            for(i in inputsRepeatDays){
                saverepeatDaysList += i
            }
            val inputsOccurances: List<String> =  multiAutocompleteOccurrence.text.toString().split("\\s*,\\s*")
            var saveremindmeList = ""
            for(i in inputsOccurances){
                saveremindmeList += i
            }

            updateValues.add(5, saverepeatDaysList)
            updateValues.add(6, saveremindmeList)

            //change it later
            var savegroupName = groupName.text.toString()
            updateValues.add(7, savegroupName)

            if(reminderTitleInputField.text.toString().trim().length != 0){
                if(updateValues[1].isEmpty()){
                    updateValues[1] = ""
                }
                if(updateValues[2].isEmpty()){
                    updateValues[2] = ""
                }
                else{
                    updateValues[2] = updateValues[2].replace("  ","/").replace(",","/")

                    // string date to Date
                    val getdateFromString = updateValues[2].substring(0,updateValues[2].indexOf("/"))
                    val date = LocalDate.parse(getdateFromString, DateTimeFormatter.ISO_DATE)

                    //current date
                    val currentDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
                    val currentDateFormated = LocalDate.parse(currentDate , DateTimeFormatter.ISO_DATE)

                    // string time to time
                    val gettimeFromString = updateValues[2].substring(updateValues[2].indexOf("/") + 1,updateValues[2].length)
                    val convertinTime = LocalTime.parse(gettimeFromString,DateTimeFormatter.ISO_LOCAL_TIME)

                    val currentTime = LocalTime.parse(LocalTime.now().toString(),DateTimeFormatter.ISO_LOCAL_TIME)

                    if(deadlinenum == 1 && completenum == 0){
                        if(date.isBefore(currentDateFormated)){
                            deadlinenum = 1
                        }
                        else if (date.isEqual(currentDateFormated)){
                            if (convertinTime.isBefore(currentTime)){
                                Snackbar.make(view,"Time Cant be in Past or Add Repeat Days",Snackbar.LENGTH_SHORT).show()
                                deadlinenum = 1
                            }
                        }
                        else{
                            deadlinenum = 0
                        }
                    }
                    else if (deadlinenum == 0 && completenum == 1){
                        if(date.isBefore(currentDateFormated)){
                            completenum = 1
                        }
                        else if (date.isEqual(currentDateFormated)){
                            if (currentTime.isBefore(currentTime)){
                                Snackbar.make(view,"Time Cant be in Past or Add Repeat Days",Snackbar.LENGTH_SHORT).show()
                                completenum = 1
                            }
                        }
                        else{
                            completenum = 0
                        }
                    }
                    else if(deadlinenum == 0 && completenum == 0){
                        if (date.isEqual(currentDateFormated)){
                            if (currentTime.isBefore(currentTime)){
                                Snackbar.make(view,"Time Cant be in Past or Add Repeat Days",Snackbar.LENGTH_SHORT).show()
                            }
                            else{
                                completenum = 0
                                deadlinenum = 0
                            }
                        }
                        else{
                            completenum = 0
                            deadlinenum = 0
                        }
                    }
                }
                if(updateValues[3].isEmpty()){
                    updateValues[3] = ""
                }
                if (updateValues[5].isEmpty()){
                    updateValues[5] = ""
                }
                if(updateValues[6].isEmpty()){
                    updateValues[6] = ""
                }
                if(updateValues[7].isEmpty()){
                    updateValues[7] = ""
                }

                database.updateReminder(
                    key!!,
                    updateValues[0],
                    updateValues[1],
                    updateValues[2],
                    updateValues[3],
                    updateValues[4].toInt(),
                    updateValues[5],
                    updateValues[6],
                    updateValues[7],
                    deadlinenum,
                    completenum
                )
                Snackbar.make(view,"Update Successfully Reminder $key", Snackbar.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_editPage_to_allReminderPage)
            }
            else{
                // taken from the lab
                Snackbar.make(view,"Can't Store Reminder Without Title", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    /**
     * time picker taken from the given link
     * “Time Picker Dialog in Android,” GeeksforGeeks, 06-Jun-2021. [Online]. Available: https://www.geeksforgeeks.org/time-picker-dialog-in-android/. [Accessed: 24-Mar-2022].
     */
    private val timerpick: TimePickerDialog.OnTimeSetListener =
        TimePickerDialog.OnTimeSetListener { p0, hours, minutes ->
        /**
         * taken date and time format from the given link
         * JUNSJUNS 7766 bronze badges, aminographyaminography 20.3k1313 gold badges6262 silver badges7070 bronze badges, Shalu T DShalu T D                    3, and Vishal PawarVishal Pawar                    4, “Android studio Kotlin - how to display 2 digit number in text?,” Stack Overflow, 01-May-1968. [Online]. Available: https://stackoverflow.com/questions/63010209/android-studio-kotlin-how-to-display-2-digit-number-in-text. [Accessed: 27-Mar-2022].
         * */
        val formattime: NumberFormat = DecimalFormat("00")
        val formatedTime = "${formattime.format(hours)}:${formattime.format(minutes)}:00"
        addTime = formatedTime
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment editReminder.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            editReminder().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}