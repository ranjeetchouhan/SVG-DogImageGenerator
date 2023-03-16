package com.ranjeet.svg_task

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ranjeet.svg_task.databinding.ItemviewImageBinding

class GalleryAdapter(
    var mContext: Context,
    val imageList: List<DogImage>,
) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemviewImageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryAdapter.ViewHolder {
        var binding = ItemviewImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryAdapter.ViewHolder, position: Int) {
        with(holder) {
            with(imageList.get(position)) {
                loadImageFromGlide(mContext, this.imageUrl, binding.ivDogImg)
            }
        }
    }

    private fun loadImageFromGlide(mContext: Context, imageUrl: String, ivDogImg: ImageView) {
        Glide.with(mContext)
            .load(imageUrl)
            .apply(RequestOptions())
            .into(ivDogImg)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}
