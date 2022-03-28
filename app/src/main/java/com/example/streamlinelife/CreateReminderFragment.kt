package com.example.streamlinelife

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateReminderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateReminderFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    /**
     * store date and time here
     */
    lateinit var reminderDateInputField: TextView
    private var addTime = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_reminder, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Create Reminders"

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

        // Reference
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

        // home button
        view.findViewById<Button>(R.id.cancel_buttonInCreateReminder).setOnClickListener {
            findNavController().navigate(R.id.action_createReminderFragment_to_homePage)
        }
        // create button
        view.findViewById<Button>(R.id.saveReminderInCreateReminder).setOnClickListener {
            var savetitle = reminderTitleInputField.text.toString()
            var savedescription = reminderDescriptionInputField.text.toString()
            var saveDate_Time =  reminderDateInputField.text.toString()
            var savelocation = locationInputField.text.toString()
            var saveimportance = ""
            when (autocompleteImportance.text.toString()) {
                "High" -> {
                    saveimportance += "3"
                }
                "Medium" -> {
                    saveimportance += "2"
                }
                "Low" -> {
                    saveimportance += "1"
                }
                else ->{
                    saveimportance += "0"
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

            //change it later
            var savegroupName = groupName.text.toString()

            if(savetitle.trim().length != 0){
                val addreminder = DBSupport(requireContext())
                if(savedescription.isEmpty()){
                    savedescription = ""
                }
                if(saveDate_Time.isEmpty()){
                    saveDate_Time = ""
                }
                if(savelocation.isEmpty()){
                    savelocation = ""
                }
                if (saverepeatDaysList.isEmpty()){
                    saverepeatDaysList = ""
                }
                if(savegroupName.isEmpty()){
                    savegroupName = ""
                }
                if(saveremindmeList.isEmpty()){
                    saveremindmeList = ""
                }

                addreminder.addReminder(savetitle,savedescription,saveDate_Time,savelocation,saveimportance.toInt(),saverepeatDaysList,savegroupName,saveremindmeList,0,0)

                // taken from the lab
                Snackbar.make(view,"Create Reminder Successfully", Snackbar.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_createReminderFragment_to_allReminderPage)
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
        val formatedTime: String =
            when {
                hours == 0 ->{
                    if (minutes < 10){
                        "${hours + 12}:0${minutes}:00"
                    }
                    else{
                        "${hours+12}:${minutes}:00"
                    }
                }
                hours == 12 -> {
                    if (minutes < 10){
                        "${hours}:0${minutes}:00"
                    }
                    else{
                        "${hours}:${minutes}:00"
                    }
                }
                hours > 12 ->{
                    if (minutes < 10){
                        "${hours - 12}:0${minutes}:00"
                    }
                    else{
                        "${hours - 12}:${minutes}:00"
                    }
                }
                else ->{
                    "${hours}:${minutes}:00"
                }
            }
        addTime = formatedTime
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreateReminderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateReminderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}