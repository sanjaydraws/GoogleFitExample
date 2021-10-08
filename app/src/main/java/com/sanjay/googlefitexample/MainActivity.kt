package com.sanjay.googlefitexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sanjay.googlefitexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var binding:ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding?.apply {
            setContentView(this.root)
        }

    }
}