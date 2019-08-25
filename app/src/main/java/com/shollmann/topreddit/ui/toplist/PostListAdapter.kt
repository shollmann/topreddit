package com.shollmann.topreddit.ui.toplist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shollmann.topreddit.R
import com.shollmann.topreddit.model.Post
import com.shollmann.topreddit.ui.utils.TimeUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.post_list_item.view.*

class PostListAdapter(
    private val parentActivity: ItemListActivity,
    var values: List<Post>,
    var readPosts: HashMap<String, Boolean>,
    private val twoPane: Boolean
) :
    RecyclerView.Adapter<PostListAdapter.ViewHolder>() {

    private fun openPostDetailForPhone(v: View, item: Post) {
        val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
            val bundle = Bundle()
            bundle.putSerializable(ItemDetailFragment.ARG_POST, item)
            putExtra(ItemDetailFragment.ARG_POST, bundle)
        }
        v.context.startActivity(intent)
    }

    private fun openPostDetailForTablet(item: Post) {
        val fragment = ItemDetailFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ItemDetailFragment.ARG_POST, item)
            }
        }
        parentActivity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.item_detail_container, fragment)
            .commit()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = values[position]
        holder.bind(post)
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(post: Post) {
            view.title.text = post.title
            view.author_and_time.text = getAuthorAndTimeAgo(post)
            view.comments.text = post.numComments.toString()

            bindThumbnail(post)

            with(view.container) {
                tag = post
                setOnClickListener {
                    val item = view.tag as Post
                    parentActivity.markAsRed(item)
                    readPosts[item.name] = true
                    notifyItemChanged(layoutPosition)

                    if (twoPane) {
                        openPostDetailForTablet(item)
                    } else {
                        openPostDetailForPhone(view, item)
                    }
                }
            }

            with(view.dismiss) {
                setOnClickListener {
                    (values as ArrayList).removeAt(layoutPosition)
                    notifyItemRemoved(layoutPosition)
                }
            }

            view.title.setTextColor(
                view.context.getColor(
                    if (readPosts.containsKey(post.name)) {
                        R.color.light_gray
                    } else {
                        R.color.dark_gray
                    }
                )
            )
        }

        private fun bindThumbnail(post: Post) {
            if (post.thumbnail.isNullOrBlank()) {
                view.thumbnail.visibility = View.GONE
            } else {
                Picasso.get().load(post.thumbnail).into(view.thumbnail)
                view.thumbnail.visibility = View.VISIBLE
            }
        }

        private fun getAuthorAndTimeAgo(post: Post): String {
            val authorTimeString = view.context.resources.getString(R.string.author_and_time_ago)
            return String.format(
                authorTimeString,
                post.author,
                TimeUtils.getTimeAgo(post.created_utc, view.context)
            )
        }

    }
}
