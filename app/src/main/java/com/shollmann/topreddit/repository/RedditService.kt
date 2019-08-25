package com.shollmann.topreddit.repository

import com.shollmann.topreddit.model.Listing
import retrofit2.Call
import retrofit2.http.GET

interface RedditService {
    @GET("top.json?limit=50&t=hour&count=0")
    fun getTop(): Call<Listing>
}
