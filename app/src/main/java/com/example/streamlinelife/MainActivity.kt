package com.example.streamlinelife

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate

//for navigation bar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.streamlinelife.persistence.DBSupport
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(){
    /**
     * FOR the navigation Drawer (Navigation)
     *
     * “Navigation drawer with Fragments Part 2 - youtube.” [Online]. Available: https://www.youtube.com/watch?v=zYVEMCiDcmY.[Accessed: 11-Mar-2022].
     *
     * E. Vaati, “How to code a navigation drawer for an Android app,” Code Envato Tuts+, 17-Jun-2021. [Online]. Available: https://code.tutsplus.com/tutorials/how-to-code-a-navigation-drawer-in-an-android-app--cms-30263. [Accessed: 29-Mar-2022].
     *
     */
    private lateinit var drawerlayout: DrawerLayout
    private lateinit var navigation_menu: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navcontroller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set tool bar
        toolbar = findViewById(R.id.titleInAppBar)
        setSupportActionBar(toolbar)

        // layout of the main
        drawerlayout = findViewById(R.id.drawerView)
        navigation_menu = findViewById(R.id.navigationbar)

        val hideCalenderPage: SharedPreferences = getSharedPreferences("HideCalender", Context.MODE_PRIVATE)
        if (hideCalenderPage.getBoolean("HideCalender",true)){
            appBarConfiguration = AppBarConfiguration(
                setOf(R.id.HomePage, R.id.createReminderFragment, R.id.allReminderPage, R.id.completedPage, R.id.deadlinePage, R.id.settingPage),
                drawerlayout
            )
        }
        else{
            appBarConfiguration = AppBarConfiguration(
                setOf(R.id.HomePage, R.id.createReminderFragment, R.id.allReminderPage, R.id.completedPage, R.id.deadlinePage, R.id.calenderPage, R.id.settingPage),
                drawerlayout
            )
        }

        navcontroller = findNavController(R.id.fragment_container_view)
        setupActionBarWithNavController(navcontroller,appBarConfiguration)
        navigation_menu.setupWithNavController(navcontroller)

        /**
         * get values from the  SharedPreferences
         * user1812111, user1812111user1812111 8311 gold badge11 silver badge1111 bronze badges, Pradip TilalaPradip Tilala 1, Rishabh JainRishabh Jain 1581111 bronze badges, and rgaraisayevrgaraisayev 36833 silver badges1212 bronze badges, “How to get value from shared preference in fragment?,” Stack Overflow, 01-Nov-1966. [Online]. Available: https://stackoverflow.com/questions/54360299/how-to-get-value-from-shared-preference-in-fragment. [Accessed: 31-Mar-2022].
         * */
        val sharedPreferences: SharedPreferences = getSharedPreferences("NightMode", Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("NightMode",true)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navcontroller.navigateUp(appBarConfiguration)
    }
}
