package com.shollmann.topreddit.model

data class Listing(val data: Data) {
    fun getPosts(): ArrayList<Post> {
        val listPost = arrayListOf<Post>()
        for (children in data.children) {
            listPost.add(children.data)
        }
        return listPost
    }
}

data class Data(val children: List<Children>)

data class Children(val data: Post)
