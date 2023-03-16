package com.ranjeet.svg_task

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ranjeet.svg_task.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    var binding: ActivityHomeBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.let {
            it.btGallery.setOnClickListener { startActivity(Intent(this, GalleryActivity::class.java)) }
            it.btGenerateDogs.setOnClickListener { startActivity(Intent(this, GenerateActivity::class.java)) }
        }
    }
}
