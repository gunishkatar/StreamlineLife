package com.example.streamlinelife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast

//for navigation bar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.widget.Toolbar

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
        actionbar =  ActionBarDrawerToggle(this,drawerlayout, toolbar,R.string.navigation_open,R.string.navigation_close)
        drawerlayout.addDrawerListener(actionbar)
        actionbar.syncState()

        navigation_menu = findViewById(R.id.navigationbar)
        navigation_menu.setNavigationItemSelectedListener(this)
    }


    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param item The selected item
     * @return true to display the item as the selected item
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when {
            item.itemId == R.id.home -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view,  HomePage()).commit()
                toolbar.title = "Streamline Life"
            }
            item.itemId == R.id.create -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, CreateReminderFragment()).commit()
                toolbar.title = "Create Reminder"
            }
            item.itemId == R.id.showallreminder -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, AllReminderPage()).commit()
                toolbar.title = "All Reminders"
            }
            item.itemId == R.id.complete -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, CompletedPage()).commit()
                toolbar.title = "Complete Reminders"
            }
            item.itemId == R.id.deadline -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, DeadlinePage()).commit()
                toolbar.title = "DeadLine Reminders"
            }
            item.itemId == R.id.calenderview -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, CalenderPage()).commit()
                toolbar.title = "Calender View"
            }
            item.itemId == R.id.grouplist -> {
//                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, CreatePage()).commit()
//                toolbar.title = "Group List"
            }
            item.itemId == R.id.Setting -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, SettingPage()).commit()
                toolbar.title = "Setting"
            }
        }
        drawerlayout.closeDrawer(GravityCompat.START)
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