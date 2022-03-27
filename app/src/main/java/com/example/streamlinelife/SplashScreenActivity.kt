package com.example.streamlinelife
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 *
 * “Splash screens &nbsp;: &nbsp; android developers,” Android Developers. [Online]. Available: https://developer.android.com/guide/topics/ui/splash-screen. [Accessed: 27-Mar-2022].
 * A. Sharma, “Implementing the perfect splash screen in Android,” Medium, 09-Mar-2022. [Online]. Available: https://medium.com/geekculture/implementing-the-perfect-splash-screen-in-android-295de045a8dc. [Accessed: 27-Mar-2022].
 *
 * */

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
        finish()
    }
}