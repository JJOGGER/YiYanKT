package com.jogger.constant

enum class CARD_CATEGORY(value: Int) {
    TYPE_ALL(-1),
    TYPE_ORIGIN(-2),//原创
    TYPE_TEXT(0),//文字
    TYPE_RECORD(1),//语录
    TYPE_WORD(2),//歌词
    TYPE_MUSIC(2000),//音乐
    TYPE_POETRY(1000),//诗
    TYPE_FILM(3),//电影
    TYPE_HOT_CARD(110000),
    TYPE_HOT(100000),//热门
    TYPE_TOPIC(100002),//话题
    TYPE_TOPIC_FIND(100003),//话题-发现
    TYPE_TOPIC_RELATE(100004);//话题-参与的

    var _value: Int = value
}