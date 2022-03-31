package com.example.streamlinelife.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.navigation.fragment.findNavController
import com.example.streamlinelife.R
import com.example.streamlinelife.adapters.CustomAdapterGroups
import com.example.streamlinelife.persistence.DBSupport
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomePage.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomePage : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_page, container, false)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //show groups from the db
        val database = DBSupport(requireContext())
        val getallGroupsFromDatabase: Map<String, ArrayList<String>> = database.getAllGroups()

        val saveIDs: Array<Int> = Array(getallGroupsFromDatabase.count()){0}
        val saveInArrayname: Array<String> = Array(getallGroupsFromDatabase.count()){""}
        val saveInArraynumberofreminder: Array<String> = Array(getallGroupsFromDatabase.count()){""}
        val saveInArraycolor: Array<String> = Array(getallGroupsFromDatabase.count()){""}
        val saveInArraydrawbale: Array<String> = Array(getallGroupsFromDatabase.count()){""}

        var index = 0
        for ((key,value) in getallGroupsFromDatabase){
            if (value[0].trim().isEmpty()){
                value[0] = ""
            }
            if (value[1].trim().isEmpty()){
                value[1] = ""
            }
            if (value[2].trim().isEmpty()){
                value[2] = ""
            }
            if (value[3].trim().isEmpty()){
                value[3] = ""
            }

            saveIDs[index] = key.toInt()
            saveInArrayname[index] = value[0]
            saveInArraynumberofreminder[index] = value[1]
            saveInArraycolor[index] = value[2]
            saveInArraydrawbale[index] = value[3]
            index++
        }

        /**
         * List view show all reminders
         *
         * web_page_person, “Kotlin Android Listview example,” TutorialKart, 21-Jan-2021. [Online]. Available: https://www.tutorialkart.com/kotlin-android/kotlin-android-listview-example/. [Accessed: 24-Mar-2022].
         * S. Naveed, “Listing activity using ListView in Android studio - kotlin,” Handy Opinion, 22-May-2021. [Online]. Available: https://handyopinion.com/listing-activity-using-listview-in-android-studio-kotlin/. [Accessed: 25-Mar-2022].
         *
         * */
        val listview = view.findViewById<ListView>(R.id.showallgroupsnameInHomePage)
        val customadapter =  CustomAdapterGroups(view.context, saveInArrayname, saveInArraycolor, saveInArraydrawbale, saveIDs, saveInArraynumberofreminder)
        listview.adapter = customadapter

        // navigation pages with respect to the button
        view.findViewById<Button>(R.id.createReminderInHomePage).setOnClickListener {
            findNavController().navigate(R.id.action_homePage_to_createReminderFragment)
        }
        view.findViewById<Button>(R.id.deadlineReminderInHomePage).setOnClickListener {
            findNavController().navigate(R.id.action_homePage_to_deadlinePage)
        }
        view.findViewById<Button>(R.id.allReminderInHomePage).setOnClickListener {
            findNavController().navigate(R.id.action_homePage_to_allReminderPage)
        }
        view.findViewById<Button>(R.id.completedReminderInHomePage).setOnClickListener {
            findNavController().navigate(R.id.action_homePage_to_completedPage)
        }
        view.findViewById<FloatingActionButton>(R.id.addGroupInHomePage).setOnClickListener {
            findNavController().navigate(R.id.action_homePage_to_createGroupFragment)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomePage.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomePage().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}