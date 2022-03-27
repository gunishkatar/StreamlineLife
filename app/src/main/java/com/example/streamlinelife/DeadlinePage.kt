package com.example.streamlinelife

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DeadlinePage.newInstance] factory method to
 * create an instance of this fragment.
 */
class DeadlinePage : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deadline_page, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Deadline Reminders"

        /**
         * “Exposed drop-down menu in Android,” GeeksforGeeks, 23-Jun-2021. [Online]. Available: https://www.geeksforgeeks.org/exposed-drop-down-menu-in-android/. [Accessed: 23-Mar-2022].
         * */
        val sortby = resources.getStringArray(R.array.sortby)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,sortby)
        val autocompleteSortBy = view.findViewById<AutoCompleteTextView>(R.id.sortbytextviewInDeadlinePage)
        autocompleteSortBy.setAdapter(arrayAdapter)

        // here i am getting the view of the list and if the user want to they can
        showInListViewAndSort().getfromAutocompleteSortBy(requireView(), autocompleteSortBy, "DeadlinePage")

        view.findViewById<Button>(R.id.completedReminderInDeadlinePage).setOnClickListener {
            findNavController().navigate(R.id.action_deadlinePage_to_completedPage)
        }
        view.findViewById<Button>(R.id.allReminderInDeadlinePage).setOnClickListener {
            findNavController().navigate(R.id.action_deadlinePage_to_allReminderPage)
        }
        view.findViewById<Button>(R.id.groupsReminderInDeadlinePage).setOnClickListener {
            findNavController().navigate(R.id.action_deadlinePage_to_homePage)
        }
        view.findViewById<Button>(R.id.addReminderInDeadlinePage).setOnClickListener {
            findNavController().navigate(R.id.action_deadlinePage_to_createReminderFragment)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DeadlinePage.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DeadlinePage().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}