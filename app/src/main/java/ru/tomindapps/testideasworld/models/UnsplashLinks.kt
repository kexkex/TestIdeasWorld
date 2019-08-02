package ru.tomindapps.testideasworld.models

data class UnsplashLinks(
    val self: String,
    val html: String,
    val photos: String? = null,
    val likes: String? = null,
    val portfolio: String? = null,
    val download: String? = null,
    val download_location: String? = null)