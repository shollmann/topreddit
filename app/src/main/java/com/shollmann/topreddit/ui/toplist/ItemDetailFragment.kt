package com.shollmann.topreddit.ui.toplist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shollmann.topreddit.R
import com.shollmann.topreddit.model.Post
import com.shollmann.topreddit.ui.utils.TimeUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.post_list_item.view.*


class ItemDetailFragment : Fragment() {

    private var rootView: View? = null
    private var post: Post? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_POST)) {
                post = it.getSerializable(ARG_POST) as Post?
                activity?.toolbar_layout?.title = getString(R.string.post_detail)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.item_detail, container, false)

        post.let {
            rootView?.title?.text = post?.title
            rootView?.author_and_time?.text = getAuthorAndTimeAgo(post)
            rootView?.comments?.text = post?.numComments.toString()
            bindThumbnail(post)
            rootView?.thumbnail?.setOnClickListener {
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://www.reddit.com+${post?.permalink}")
                        )
                    )
                } catch (e: Exception) {
                    //TODO Show a log
                }
            }

        }

        return rootView
    }

    companion object {
        const val ARG_POST = "post_id"
    }

    private fun bindThumbnail(post: Post?) {
        if (post?.thumbnail.isNullOrBlank()) {
            rootView?.thumbnail?.visibility = View.GONE
        } else {
            Picasso.get().load(post?.thumbnail).into(rootView?.thumbnail)
            rootView?.thumbnail?.visibility = View.VISIBLE
        }
    }

    private fun getAuthorAndTimeAgo(post: Post?): String {
        val authorTimeString = context?.resources?.getString(R.string.author_and_time_ago)
        if (authorTimeString.isNullOrBlank() || post == null) return ""

        return String.format(
            authorTimeString,
            post.author,
            TimeUtils.getTimeAgo(post.created_utc, context!!)
        )
    }

}
