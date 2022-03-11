package com.example.streamlinelife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //add fragment as a home page
        supportFragmentManager.commit {
            setReorderingAllowed(true);
            add<HomePage>(R.id.homePage)
        }
    }
}