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
    val readPosts = MutableLiveData<HashMap<String, Boolean>>()

    suspend fun loadTopPosts() {
        val result = getTopPost()
        val response = result.response as Listing?

        if (response != null) {
            topPosts.value = response.getPosts()
            if (readPosts.value == null) {
                readPosts.value = HashMap()
            }
        }
    }

    private suspend fun getTopPost(): ServiceResponse {
        return withContext(Dispatchers.IO) {
            RedditRepository.getTop()
        }
    }

    fun markAsRed(post: Post) {
        if (readPosts.value == null) {
            readPosts.value = HashMap()
        }

        readPosts.value?.put(post.name, true)
    }

    fun dismissPost(position: Int) {
        if (topPosts.value == null) {
            return
        }

        (topPosts.value as ArrayList).removeAt(position)
    }
}
