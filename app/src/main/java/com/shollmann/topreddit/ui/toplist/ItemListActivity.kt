package com.shollmann.topreddit.ui.toplist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.shollmann.topreddit.R
import com.shollmann.topreddit.model.Post
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ItemListActivity : AppCompatActivity() {

    private lateinit var viewmodel: TopListViewModel
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        viewmodel = ViewModelProviders.of(this)[TopListViewModel::class.java]
        viewmodel.topPosts.observe(
            this,
            Observer {
                displayPosts(it)
            })

        swipe_refresh_layout.isRefreshing = true
        fetchPosts(viewmodel)

        if (item_detail_container != null) {
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        item_list.adapter = PostListAdapter(this, emptyList(), HashMap(), twoPane)

        swipe_refresh_layout.setOnRefreshListener {
            fetchPosts(viewmodel)
        }
    }

    private fun displayPosts(it: List<Post>) {
        swipe_refresh_layout.isRefreshing = false
        val adapter = item_list.adapter as PostListAdapter
        if (viewmodel.readPosts.value != null) {
            adapter.readPosts = viewmodel.readPosts.value!!
        }
        adapter.values = it
        adapter.notifyDataSetChanged()

    }

    private fun fetchPosts(viewmodel: TopListViewModel) {
        GlobalScope.launch(Dispatchers.Main) {
            viewmodel.loadTopPosts()
        }
    }

    fun markAsRed(post: Post) {
        viewmodel.markAsRed(post)
    }

}
