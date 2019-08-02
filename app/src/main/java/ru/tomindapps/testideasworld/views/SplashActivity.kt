package ru.tomindapps.testideasworld.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import ru.tomindapps.testideasworld.R

class SplashActivity : AppCompatActivity() {

    private val SPLASH_DURATION = 500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        scheduleSplashScreen()
    }

    private fun scheduleSplashScreen() {
        val splashScreenDuration = SPLASH_DURATION
        Handler().postDelayed(
            {
                // После Splash Screen перенаправляем в MainActivity
                routeToMainActivity()
                finish()
            },
            splashScreenDuration
        )
    }

    private fun routeToMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
