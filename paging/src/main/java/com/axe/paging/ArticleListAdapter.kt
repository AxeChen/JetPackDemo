package com.axe.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ArticleListAdapter :
    ListAdapter<ArticleBean, ArticleViewHolder>(ArticleDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_article, null)
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.run {
            val article = getItem(position)
            titleText?.text = article.title
            articleTime?.setText(article.time)
        }
    }
}

class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var titleText: TextView? = null
    var articleTime: TextView? = null

    init {
        titleText = view.findViewById(R.id.tvArticleTitle)
        articleTime = view.findViewById(R.id.tvArticleTime)
    }

}

class ArticleDiffCallBack : DiffUtil.ItemCallback<ArticleBean>() {
    override fun areItemsTheSame(oldItem: ArticleBean, newItem: ArticleBean): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: ArticleBean, newItem: ArticleBean): Boolean {
        return oldItem == newItem
    }

}