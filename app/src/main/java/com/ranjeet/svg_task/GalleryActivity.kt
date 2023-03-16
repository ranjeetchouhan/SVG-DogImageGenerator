package com.ranjeet.svg_task

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ranjeet.svg_task.databinding.ActivityGalleryBinding

class GalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGalleryBinding
    private lateinit var galleryAdapter: GalleryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        val dogImages = getAllDogsImages()
        if (dogImages.isEmpty()) {
            binding.rvDogsImages.visibility = View.GONE
            binding.btClearCache.visibility = View.GONE
            binding.tvNoImages.visibility = View.VISIBLE
        } else {
            binding.tvNoImages.visibility = View.GONE
            galleryAdapter = GalleryAdapter(this, dogImages)
            binding.rvDogsImages.apply {
                layoutManager = LinearLayoutManager(this@GalleryActivity, LinearLayoutManager.HORIZONTAL, false)
                adapter = galleryAdapter
            }
            binding.btClearCache.setOnClickListener {
                DogImageCache.clear()
                galleryAdapter.notifyDataSetChanged()
                binding.tvNoImages.visibility = View.VISIBLE
                binding.rvDogsImages.visibility = View.GONE
                binding.btClearCache.visibility = View.GONE
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }

    private fun getAllDogsImages(): List<DogImage> = DogImageCache.getAll()
}
