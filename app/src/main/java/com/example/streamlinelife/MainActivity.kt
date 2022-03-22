package com.example.streamlinelife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.add
import androidx.fragment.app.commit

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //add fragment as a home page
//        supportFragmentManager.commit {
//            setReorderingAllowed(true)
//            add<HomePage>(R.id.fragment_container_view)
//        }

        val toolbar: Toolbar = findViewById(R.id.titleInAppBar)
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
            item.itemId.equals(R.id.home) -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view,  HomePage()).commit()
                Toast.makeText(this, "Home Page", Toast.LENGTH_SHORT).show()
            }
            item.itemId.equals(R.id.create) -> {
//                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, CreatePage()).commit()
                Toast.makeText(this, "Create Reminder Page", Toast.LENGTH_SHORT).show()
            }
            item.itemId.equals(R.id.showallreminder) -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, AllReminderPage()).commit()
                Toast.makeText(this, "All Reminder Page", Toast.LENGTH_SHORT).show()
            }
            item.itemId.equals(R.id.complete) -> {
//                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, CreatePage()).commit()
                Toast.makeText(this, "Completed Reminder Page", Toast.LENGTH_SHORT).show()
            }
            item.itemId.equals(R.id.deadline) -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, DeadlinePage()).commit()
                Toast.makeText(this, "Deadline Reminder Page", Toast.LENGTH_SHORT).show()
            }
            item.itemId.equals(R.id.calenderview) -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, CalenderPage()).commit()
                Toast.makeText(this, "Calender View Page", Toast.LENGTH_SHORT).show()
            }
            item.itemId.equals(R.id.grouplist) -> {
//                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, CreatePage()).commit()
                Toast.makeText(this, "Group List Page", Toast.LENGTH_SHORT).show()
            }
            item.itemId.equals(R.id.Setting) -> {
//                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, CreatePage()).commit()
                Toast.makeText(this, "Setting Page", Toast.LENGTH_SHORT).show()
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