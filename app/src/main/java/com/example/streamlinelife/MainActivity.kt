package com.example.streamlinelife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
//for navigation bar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.widget.Toolbar
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    /**
     * FOR the navigation Drawer
       1. “Navigation drawer with Fragments Part 2 - youtube.”
        [Online]. Available: https://www.youtube.com/watch?v=zYVEMCiDcmY.
        [Accessed: 11-Mar-2022].

     */
    //lateinit --> initialize after declare
    private lateinit var drawerlayout: DrawerLayout
    private lateinit var actionbar: ActionBarDrawerToggle
    private lateinit var navigation_menu: NavigationView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.titleInAppBar)
        setSupportActionBar(toolbar)
        toolbar.title = "Streamline Life"

        drawerlayout = findViewById(R.id.drawerView)

        navigation_menu = findViewById(R.id.navigationbar)

        actionbar =  ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.navigation_open, R.string.navigation_close)
        drawerlayout.addDrawerListener(actionbar)
        actionbar.syncState()

        navigation_menu.setNavigationItemSelectedListener(this)

        //uncommet this to add dummy data
        //once data is added then please comment this line other wise it will add data again and again in the sqlite :)
//        dummyData()
    }

    private fun dummyData() {
        //        DBSupport(this).addReminder("workout","","2022-03-28,6:45:00","",3,"Friday, Wednesday, Tuesday","","15 Mins, 45 Mins,",0,0)
        //        DBSupport(this).addReminder("work","assignments",	"2022-03-27,6:20:00",	"",1,	"Thrusday, Tuesday, Tuesday,","","15 Mins,",0,	0)
        //        DBSupport(this).addReminder("work","assignments","2022-03-29,6:45:00","",1,"Tuesday, Friday, Saturday","","30 Mins, 30 Mins, 10 Mins,",0,0)
        //        DBSupport(this).addReminder("dance","with my friend","2022-03-31,7:10:00","",2,"Saturday, Wednesday,","","15 Mins,",0,0)
        //        DBSupport(this).addReminder("dinner","with family","2022-04-01,6:10:00","halifax",2,"Friday, Wednesday, Tuesday","","15 Mins, 45 Mins,",0,0)
        //        DBSupport(this).addReminder("workout","","2022-03-25,5:45:00","",3,"","","45 Mins",1,0)
        //        DBSupport(this).addReminder("study","","2022-03-10,6:45:00","",3,"","", "10 Mins, 45 Mins",0,1)
        //        DBSupport(this).addReminder("meeting","with co-worker","2022-03-26,9:59:50","",3,"","","15 Mins, 45 Mins",1,0)
        //        DBSupport(this).addReminder("interview","with COVE","2022-03-20,6:45:00","",3,"","", "10 Mins, 45 Mins",0,1)
        //
        //        DBSupport(this).addGroup("travel", 0, "-3179163", "android.graphics.drawable.VectorDrawable@3df9e98")
        //        DBSupport(this).addGroup("work", 0, "317913", "android.graphics.drawable.VectorDrawable@3df9e98")
    }

    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param item The selected item
     * @return true to display the item as the selected item
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerlayout.closeDrawer(GravityCompat.START)

        when (item.itemId){
            R.id.homePage -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view,  HomePage()).commit()
                toolbar.title = "Streamline Life"
            }
            R.id.create -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, CreateReminderFragment()).commit()
                toolbar.title = "Create Reminder"
            }
            R.id.showallreminder -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, AllReminderPage()).commit()
                toolbar.title = "All Reminders"
            }
            R.id.complete -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, CompletedPage()).commit()
                toolbar.title = "Complete Reminders"
            }
            R.id.deadline -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, DeadlinePage()).commit()
                toolbar.title = "DeadLine Reminders"
            }
            R.id.calenderview -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, CalenderPage()).commit()
                toolbar.title = "Calender View"
            }
            R.id.grouplist -> {
//                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, CreatePage()).commit()
//                toolbar.title = "Group List"
            }
            R.id.Setting -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, SettingPage()).commit()
                toolbar.title = "Setting"
            }
        }

        return true
    }

    // back button
    override fun onBackPressed() {
        if (this.drawerlayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerlayout.closeDrawer(GravityCompat.START)
        }
        else{
            super.onBackPressed()
        }
    }
}
