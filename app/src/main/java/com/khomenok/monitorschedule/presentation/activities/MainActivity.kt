package com.khomenok.monitorschedule.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.khomenok.monitorschedule.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

}