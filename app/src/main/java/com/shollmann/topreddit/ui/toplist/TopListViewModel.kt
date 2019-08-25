package com.shollmann.topreddit.ui.toplist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shollmann.topreddit.model.Listing
import com.shollmann.topreddit.model.Post
import com.shollmann.topreddit.repository.RedditRepository
import com.shollmann.topreddit.repository.ServiceResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class TopListViewModel : ViewModel() {
    val topPosts = MutableLiveData<List<Post>>()

    suspend fun loadTopPosts() {
        val result = getTopPost()
        val response = result.response as Listing?

        if (response != null) {
            topPosts.value = response.getPosts()
        }
    }

    private suspend fun getTopPost(): ServiceResponse {
        return withContext(Dispatchers.IO) {
            RedditRepository.getTop()
        }
    }
}
