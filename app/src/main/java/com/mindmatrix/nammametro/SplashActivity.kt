package com.mindmatrix.nammametro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.mindmatrix.nammametro.databinding.ActivitySplashBinding
import com.mindmatrix.nammametro.util.BaseActivity

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }, 1500)
    }
}
