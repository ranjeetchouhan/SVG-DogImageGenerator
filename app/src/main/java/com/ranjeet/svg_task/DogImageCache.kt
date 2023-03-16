package com.ranjeet.svg_task

import androidx.collection.LruCache

object DogImageCache {
    private const val MAX_CACHE_SIZE = 20
    private val cache = LruCache<String, DogImage>(MAX_CACHE_SIZE)

    fun put(dogImage: DogImage) {
        cache.put(dogImage.imageUrl, dogImage)
    }

    fun get(imageUrl: String): DogImage? {
        return cache.get(imageUrl)
    }

    fun getAll(): List<DogImage> {
        return cache.snapshot().values.toList()
    }

    fun clear() {
        cache.evictAll()
    }
}
