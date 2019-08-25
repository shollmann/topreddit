package com.shollmann.topreddit.model

data class Post(
    val title: String,
    val permalink: String,
    val numComments: Int,
    val author: String,
    val created_utc: Long,
    val thumbnail: String?,
    val name: String
)
