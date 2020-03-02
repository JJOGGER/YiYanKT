package com.jogger.entity

data class ArticleData (
    val textcardlist: List<TextCard>? = null){
    override fun toString(): String {
        return "ArticleData(textcardlist=$textcardlist)"
    }
}
