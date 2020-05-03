package com.jogger.event

class PlayerStatusEvent(action: Int) : BaseActionEvent(action) {
    constructor() : this(STATUS_NONE)

    companion object {
        const val STATUS_NONE = -1
        const val STATUS_PAUSE = 0
        const val STATUS_PLAYINT = 1
    }
}