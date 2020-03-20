package com.jogger.entity

data class ArticleData(
    val moreextra: String? = null,
    val textcardlist: MutableList<TextCard>? = null
) {
    override fun toString(): String {
        return "ArticleData(textcardlist=$textcardlist)"
    }
}
