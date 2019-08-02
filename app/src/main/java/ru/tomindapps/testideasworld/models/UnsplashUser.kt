package ru.tomindapps.testideasworld.models

data class UnsplashUser(
    val id: String,
    val username: String,
    val name: String,
    val portfolio_url: String? = null,
    val bio: String? = null,
    val location: String? = null,
    val total_likes: Int,
    val total_photos: Int,
    val total_collection: Int//,
    //val profile_image: UnsplashUrls,
    //val links: UnsplashLinks
)