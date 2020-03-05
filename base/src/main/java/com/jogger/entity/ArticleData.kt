package com.jogger.entity

data class ArticleData (
    val textcardlist: MutableList<TextCard>? = null){
    override fun toString(): String {
        return "ArticleData(textcardlist=$textcardlist)"
    }
}
