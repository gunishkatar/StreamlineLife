package com.example.streamlinelife

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.DatePicker
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController

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
        super.onViewCreated(view, savedInstanceState)
        val reminderImportance = resources.getStringArray(R.array.reminder_importance)
        val arrayAdapter = ArrayAdapter(view.context, R.layout.dropdown_item, reminderImportance)
        val autocompleteTV = view.findViewById<AutoCompleteTextView>(R.id.reminderPriority)
        autocompleteTV.setAdapter(arrayAdapter)

        val reminderOccurrence = resources.getStringArray(R.array.reminder_occurrence)
        val arrayAdapterForOccurrence = ArrayAdapter(view.context, R.layout.dropdown_item, reminderOccurrence)
        val autocompleteRO = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextViewRO)
        autocompleteRO.setAdapter(arrayAdapterForOccurrence)
        val toolBar = view.findViewById<Toolbar>(R.id.topAppBarCreateReminderFragment)

        val reminderTitleInputField = view.findViewById<TextView>(R.id.reminderTitleInputField)
        val reminderDescriptionInputField = view.findViewById<TextView>(R.id.reminderDescriptionInputField)
        val reminderDateInputField = view.findViewById<DatePicker>(R.id.reminderDateInputField)
        val locationInputField = view.findViewById<TextView>(R.id.locationInputField)
        val reminderPriority = view.findViewById<AutoCompleteTextView>(R.id.reminderPriority)
        val reminderOccurrenceInput = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextViewRO)

        toolBar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_createReminderFragment_to_homePage)
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