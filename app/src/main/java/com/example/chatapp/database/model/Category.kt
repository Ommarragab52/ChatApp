package com.example.chatapp.database.model

import com.example.chatapp.R

data class Category(
    var id: String? = null,
    var name: String? = null,
    var imageId: Int? = null
) {
    companion object {
        const val MOVIES = "MOVIES"
        const val SPORTS = "SPORTS"
        const val MUSIC = "MUSIC"
        fun getCategoryImageId(roomId: String): Int {
            return when (roomId) {
                MOVIES -> {
                    getCategoriesList()[0].imageId!!
                }

                SPORTS -> {
                    getCategoriesList()[1].imageId!!
                }

                else -> {
                    getCategoriesList()[2].imageId!!
                }
            }
        }

        fun getCategoriesList(): List<Category> {
            return listOf(
                Category(MOVIES, "Movies", R.drawable.ic_movies),
                Category(SPORTS, "Sports", R.drawable.ic_football),
                Category(MUSIC, "Music", R.drawable.ic_music)
            )
        }
    }
}