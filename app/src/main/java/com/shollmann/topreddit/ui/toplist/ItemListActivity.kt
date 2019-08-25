package com.shollmann.topreddit.ui.toplist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.shollmann.topreddit.R
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ItemListActivity : AppCompatActivity() {

    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        val viewmodel = ViewModelProviders.of(this)[TopListViewModel::class.java]
        viewmodel.topPosts.observe(
            this,
            Observer {
                val adapter = item_list.adapter as PostListAdapter
                adapter.values = it
                adapter.notifyDataSetChanged()
            })

        GlobalScope.launch(Dispatchers.Main) {
            viewmodel.loadTopPosts()
        }

        if (item_detail_container != null) {
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        item_list.adapter = PostListAdapter(this, emptyList(), twoPane)
    }
}
