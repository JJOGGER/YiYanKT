package com.jogger.event

class CardActionEvent(action:Int) :BaseActionEvent(action){

    companion object{
        const val CARD_DELETE_SUCCESS=100
    }
}