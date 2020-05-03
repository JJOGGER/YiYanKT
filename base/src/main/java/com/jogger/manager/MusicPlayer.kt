package com.jogger.manager

import android.media.AudioManager
import android.media.MediaPlayer
import com.danikula.videocache.HttpProxyCacheServer
import com.jogger.event.PlayerStatusEvent
import ex.launchOnlyresult
import ex.postEvent
import ex.sApplication
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

object MusicPlayer : MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnPreparedListener,
    MediaPlayer.OnCompletionListener {
    private var mPlayStatus = PlayerStatusEvent.STATUS_NONE
    private val mPlayer by lazy {
        MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            isLooping = true
            setOnBufferingUpdateListener(this@MusicPlayer)
            setOnPreparedListener(this@MusicPlayer)
            setOnCompletionListener(this@MusicPlayer)
        }
    }
    private val sProxy by lazy {
        HttpProxyCacheServer.Builder(sApplication)
            .maxCacheSize(1024 * 1024 * 1024)
            .build()
    }

    fun play(url: String) {
        when (mPlayStatus) {
            PlayerStatusEvent.STATUS_NONE -> {
                val proxyUrl = sProxy.getProxyUrl(url)
                mPlayer.reset()
                mPlayer.setDataSource(proxyUrl)
                mPlayer.prepare()
                mPlayStatus = PlayerStatusEvent.STATUS_PLAYINT
            }
            PlayerStatusEvent.STATUS_PAUSE -> {
                try {
                    mPlayer.prepare()
                } catch (e: Exception) {

                }
                mPlayStatus = PlayerStatusEvent.STATUS_PLAYINT
            }
            PlayerStatusEvent.STATUS_PLAYINT -> {
                if (mPlayer.isPlaying)
                    mPlayer.pause()
                mPlayStatus = PlayerStatusEvent.STATUS_PAUSE
            }
        }
        postEvent(PlayerStatusEvent(mPlayStatus))
    }

    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
        val playerStatusEvent =
            PlayerStatusEvent(mPlayStatus)
        playerStatusEvent.putExtra(ex.PROGRESS, percent)
        postEvent(playerStatusEvent)
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mPlayer.start()
        GlobalScope.launch {
            while (mPlayStatus == PlayerStatusEvent.STATUS_PLAYINT) {
                val playerStatusEvent =
                    PlayerStatusEvent(mPlayStatus)
                val current =
                    (mPlayer.currentPosition * 100 / mPlayer.duration.toFloat()).toInt()
                playerStatusEvent.putExtra(
                    ex.PROGRESS,
                    current
                )
                playerStatusEvent.putExtra(
                    ex.START_TIME,
                    formatTime(mPlayer.currentPosition.toLong())
                )
                playerStatusEvent.putExtra(ex.END_TIME, formatTime(mPlayer.duration.toLong()))
                postEvent(playerStatusEvent)
            }
        }
    }

    fun formatTime(time: Long): String {
        var min = (time / (1000 * 60)).toString()
        var sec: String = (time % (1000 * 60)).toString()
        if (min.length < 2) {
            min = "0" + time / (1000 * 60) + ""
        } else {
            min = (time / (1000 * 60)).toString();
        }
        if (sec.length == 4) {
            sec = "0" + (time % (1000 * 60)) + "";
        } else if (sec.length == 3) {
            sec = "00" + (time % (1000 * 60)) + "";
        } else if (sec.length == 2) {
            sec = "000" + (time % (1000 * 60)) + "";
        } else if (sec.length == 1) {
            sec = "0000" + (time % (1000 * 60)) + "";
        }
        return min + ":" + sec.trim().substring(0, 2);
    }

    override fun onCompletion(mp: MediaPlayer?) {
    }
}