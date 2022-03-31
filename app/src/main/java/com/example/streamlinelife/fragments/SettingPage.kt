package com.example.streamlinelife.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import com.example.streamlinelife.R
import com.google.android.material.snackbar.Snackbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingPage.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingPage : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting_page, container, false)
    }

    /**
     * “How to toggle between day and night themes programmatically using Kotlin,” How to toggle between day and night themes programmatically using Kotlin | Android | Coders' Guidebook. [Online]. Available: https://codersguidebook.com/how-to-create-an-android-app/day-night-android-themes. [Accessed: 31-Mar-2022].
     * */
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ToggleButton>(R.id.notificationbuttonInSettingPage).setOnCheckedChangeListener { _, boolean ->
            when (boolean){
                true -> {
                    Snackbar.make(view,"There is no notification",Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        view.findViewById<ToggleButton>(R.id.soundbuttonInSettingPage).setOnCheckedChangeListener { _, boolean ->
            when (boolean){
                true -> {
                    Snackbar.make(view,"There is no sound in the app because there is no notification",Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        /**
         * MarkMark 1, Jug6ernautJug6ernaut 8, LeonLeon  3, ed209ed209  79822 gold badges1212 silver badges2929 bronze badges, misbahm3misbahm3 8155 bronze badges, Abdeljabar TaoufikallahAbdeljabar Taoufikallah  5955 bronze badges, KirguduckKirguduck 65911 gold badge88 silver badges1818 bronze badges, user11809346user11809346 1111 bronze badge, StanojkovicStanojkovic  1, Akash VeerappanAkash Veerappan 6611 gold badge11 silver badge66 bronze badges, davejoemdavejoem 4, reza rahmadreza rahmad 96099 silver badges1616 bronze badges, and jayeejayee 911 bronze badge, “Android sharedpreferences in fragment,” Stack Overflow, 01-May-1960. [Online]. Available: https://stackoverflow.com/questions/11741270/android-sharedpreferences-in-fragment. [Accessed: 31-Mar-2022].
         *
         * */
        val sharedPreferences: SharedPreferences = activity!!.getSharedPreferences("NightMode", Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("NightMode",true)){
            view.findViewById<ToggleButton>(R.id.darkmodebuttonInSettingPage).text = "ON"
        }
        else{
            view.findViewById<ToggleButton>(R.id.darkmodebuttonInSettingPage).text = "OFF"
        }
        view.findViewById<ToggleButton>(R.id.darkmodebuttonInSettingPage).setOnCheckedChangeListener { _, boolean ->
            when (boolean){
                true -> {
                    sharedPreferences.edit().putBoolean("NightMode",true).apply()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                false ->{
                    sharedPreferences.edit().putBoolean("NightMode",false).apply()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }

        view.findViewById<Button>(R.id.submitFeedbackInthesettingpage).setOnClickListener {
            val text = view.findViewById<TextView>(R.id.feedbackinputinthesettingpage).text
            if(text.isEmpty()){
                Snackbar.make(view,"Text Cant Be Empty: Form is Not Submitted", Snackbar.LENGTH_SHORT).show()
            }
            else{
                Snackbar.make(view,"Thank you For your Feedback", Snackbar.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_settingPage_to_HomePage)
            }
        }

        view.findViewById<Button>(R.id.homebuttonintheSettingPage).setOnClickListener {
            findNavController().navigate(R.id.action_settingPage_to_HomePage)
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingPage.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingPage().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}