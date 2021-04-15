package com.axe.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class ArticleListAdapter :
    PagingDataAdapter<ArticleBean, ArticleViewHolder>(ArticleDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent,false)
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.run {
            val article = getItem(position)
            article?.run {
                titleText?.text = article.title
                articleTime?.setText(article.time)
            }
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