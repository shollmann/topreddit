package com.shollmann.topreddit.model

import java.io.Serializable

data class Post(
    val title: String,
    val permalink: String,
    val numComments: Int,
    val author: String,
    val created_utc: Long,
    val thumbnail: String?,
    val name: String
): Serializable
