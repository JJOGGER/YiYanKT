package com.jogger.event

class PublishEvent(action:Int) :BaseActionEvent(action){

    companion object{
        const val PUBLISH_SUCCESS=100
    }
}