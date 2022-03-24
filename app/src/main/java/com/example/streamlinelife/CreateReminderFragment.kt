package com.example.streamlinelife

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.*
import androidx.appcompat.widget.Toolbar
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
// Reference : https://stackoverflow.com/questions/30978457/how-to-show-snackbar-when-activity-starts

class CreateReminderFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // Reference fro dropdown menu: https://www.geeksforgeeks.org/exposed-drop-down-menu-in-android/
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val db = DBSupport(view.context)
        super.onViewCreated(view, savedInstanceState)
        val reminderImportance = resources.getStringArray(R.array.reminder_importance)
        val arrayAdapter = ArrayAdapter(view.context, R.layout.dropdown_item, reminderImportance)
        val autocompleteTV = view.findViewById<AutoCompleteTextView>(R.id.reminderPriority)
        autocompleteTV.setAdapter(arrayAdapter)

        val reminderOccurrence = resources.getStringArray(R.array.reminder_occurrence)
        val arrayAdapterForOccurrence = ArrayAdapter(view.context, R.layout.dropdown_item, reminderOccurrence)
        val autocompleteRO = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextViewRO)
        autocompleteRO.setAdapter(arrayAdapterForOccurrence)
        val parentLayout: View = view.findViewById(R.id.createReminderFragment)

        val toolBar = view.findViewById<Toolbar>(R.id.topAppBarCreateReminderFragment)

        val reminderTitleInputField = view.findViewById<TextView>(R.id.reminderTitleInputField)
        val reminderDescriptionInputField = view.findViewById<TextView>(R.id.reminderDescriptionInputField)
        val reminderDateInputField = view.findViewById<TextView>(R.id.reminderDateInputField)
        val locationInputField = view.findViewById<TextView>(R.id.locationInputField)
        val reminderPriority = view.findViewById<AutoCompleteTextView>(R.id.reminderPriority)
        val reminderOccurrenceInput = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextViewRO)

        // Reference : https://stackoverflow.com/questions/51646283/date-picker-dialog-not-showing-in-android-fragment
        reminderDateInputField.setInputType(InputType.TYPE_NULL);
        reminderDateInputField.setKeyListener(null);
        val c = Calendar.getInstance();
        val mYear = c.get(Calendar.YEAR);
        val mMonth = c.get(Calendar.MONTH);
        val mDay = c.get(Calendar.DAY_OF_MONTH);
        reminderDateInputField.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                view.context,
                { view, year, monthOfYear, dayOfMonth ->
                    val month =
                        monthOfYear + 1 // month count given less one e.g. august then give month no 7
                    var dayStr = dayOfMonth.toString()
                    var monthStr = month.toString()
                    if (dayStr.length == 1) dayStr = "0$dayStr"
                    if (monthStr.length == 1) monthStr = "0$monthStr"

                    // dd/mm/yyyy format set.
                    reminderDateInputField.text = "$dayStr/$monthStr/$year"
                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()
        }
        view.findViewById<Button>(R.id.cancel_button).setOnClickListener {
            findNavController().navigate(R.id.action_createReminderFragment_to_homePage)
        }

        view.findViewById<Button>(R.id.saveReminder).setOnClickListener {
//            .setAction("CLOSE",  View.OnClickListener {
//                })
            if(reminderTitleInputField.toString()==null){
                Snackbar.make(parentLayout, "Please add reminder title", Snackbar.LENGTH_LONG).show()
            }
            var createReminderSuccess = db.addReminder(reminderTitleInputField.toString(),reminderDescriptionInputField.toString(),reminderDateInputField.toString(),
                locationInputField.toString(),1,1,"test",1);
            if(createReminderSuccess){
                Snackbar.make(parentLayout, "Reminder Created Successfully", Snackbar.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_createReminderFragment_to_homePage)
            }
        }
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