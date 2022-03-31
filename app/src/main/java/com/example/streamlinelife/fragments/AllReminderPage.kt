package com.example.streamlinelife.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.streamlinelife.R
import com.example.streamlinelife.adapters.ListView
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AllReminderPage.newInstance] factory method to
 * create an instance of this fragment.
 */
class AllReminderPage : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_reminder_page, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //current date show
        val simpleDateFormat = SimpleDateFormat("EEEE, MMMM dd, yyy").format(Date())
        view.findViewById<TextView>(R.id.CurrentDateInAllReminderPage).text = simpleDateFormat

        //  “Exposed drop-down menu in Android,” GeeksforGeeks, 23-Jun-2021.
        //  [Online]. Available: https://www.geeksforgeeks.org/exposed-drop-down-menu-in-android/.
        //  [Accessed: 23-Mar-2022].
        val sortby = resources.getStringArray(R.array.sortby)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, sortby)
        val autocompleteSortBy = view.findViewById<AutoCompleteTextView>(R.id.sortbytextview)
        autocompleteSortBy.setAdapter(arrayAdapter)

        // it will go in this class and go in the function getfromAutocompleteSortBy
        ListView().getfromAutocompleteSortBy(requireView(), autocompleteSortBy, "AllReminderPage")

        //button to redirect user to create reminder
        view.findViewById<Button>(R.id.addbuttonInAllReminderPage).setOnClickListener {
            findNavController().navigate(R.id.action_allReminderPage_to_createReminderFragment)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AllReminderPage.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AllReminderPage().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
