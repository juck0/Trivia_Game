package com.example.triviagame
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.triviagame.R
import com.example.triviagame.databinding.ActivityMainBinding
import com.example.triviagame.ui.HomeFragment


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val fragmentHome = HomeFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }
    private fun initView() {
        supportFragmentManager.beginTransaction().add(R.id.container, fragmentHome).commit()
    }
}