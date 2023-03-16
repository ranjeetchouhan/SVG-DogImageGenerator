package com.ranjeet.svg_task

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ranjeet.svg_task.databinding.ActivityGenerateBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class GenerateActivity : AppCompatActivity() {
    var dispatcherMain = Dispatchers.Main
    var dispatcherIO = Dispatchers.IO
    var binding: ActivityGenerateBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenerateBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupToolbar()
        binding?.let { binding ->
            binding.generateImages.setOnClickListener {
                lifecycleScope.launch(dispatcherMain) {
                    val imageUrl = fetchDogImage()
                    if (imageUrl != null) {
                        loadImageFromNetwork(imageUrl, binding.ivDogImage)
                        putIntoCache(imageUrl)
                    }
                }
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding?.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun putIntoCache(imageUrl: String) {
        DogImageCache.put(DogImage(imageUrl))
    }

    private fun loadImageFromNetwork(imageUrl: String, imageView: ImageView) {
        val circularProgressDrawable = CircularProgressDrawable(this@GenerateActivity)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        Glide.with(this@GenerateActivity)
            .load(imageUrl)
            .apply(RequestOptions().placeholder(circularProgressDrawable))
            .into(imageView)
    }

    private suspend fun fetchDogImage(): String? {
        return withContext(dispatcherIO) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://dog.ceo/api/breeds/image/random")
                .build()
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) return@withContext null
            val json = JSONObject(response.body?.string() ?: return@withContext null)
            json.getString("message")
        }
    }
}
