package com.jogger.module_home.view.delegate

import android.text.TextUtils
import android.view.Gravity
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.jogger.entity.TextCard
import com.jogger.event.PlayerStatusEvent
import com.jogger.manager.MusicPlayer
import com.jogger.module_home.R
import com.jogger.module_home.databinding.HomeDetailMusicViewBinding
import com.jogger.module_home.view.fragment.TextCardDetailFragment
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by jogger on 2020/3/20
 * 描述：
 */
class MusicProxy(binding: HomeDetailMusicViewBinding, context: TextCardDetailFragment) :
    BaseProxy<HomeDetailMusicViewBinding>(binding, context) {

    override fun initView() {
        registEvent(true)
        val card = mBinding.textCard!!
        if (!TextUtils.isEmpty(card.picpath)) {
            mBinding.ivHeader.visibility = View.VISIBLE
            Glide.with(mContext)
                .load(card.picpath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mBinding.ivHeader)
        } else {
            mBinding.ivHeader.visibility = View.GONE
        }
        mBinding.tvTextTitle.gravity = Gravity.CENTER_HORIZONTAL
        if (!TextUtils.isEmpty(card.showtime)) {
            val date = card.showtime!!.split("-", " ")
            mBinding.tabView.tvDate.text = "${date[1]}-${date[2]}"
        }
        mBinding.proxy = this
        mBinding.tabView.proxy = this
        mBinding.bottomAction.proxy = this
        checkLiked(card.textcardid!!)
        setupBottomAction(mBinding.bottomAction)
    }

    fun playMusic(card: TextCard) {
        val url = if (TextUtils.isEmpty(card.musicurl)) card.musicurl2 else card.musicurl
        if (!TextUtils.isEmpty(url)) {
            MusicPlayer.play(url!!)
        }
    }

    fun nextSong(card: TextCard) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onPlayerStatusEvent(event: PlayerStatusEvent) {
        when (event.mAction) {
            PlayerStatusEvent.STATUS_NONE -> {
                mBinding.sbProgress.isEnabled = false
                mBinding.ibtnPlay.setImageResource(R.drawable.music_play)
            }
            PlayerStatusEvent.STATUS_PAUSE -> {
                mBinding.sbProgress.isEnabled = false
                mBinding.ibtnPlay.setImageResource(R.drawable.music_pause)
            }
            PlayerStatusEvent.STATUS_PLAYINT -> {
                mBinding.sbProgress.isEnabled = true
                mBinding.ibtnPlay.setImageResource(R.drawable.music_pause)
                val progress = event.getIntExtra(ex.PROGRESS, 0)
                val startTime = event.getStringExtra(ex.START_TIME)
                val endTime = event.getStringExtra(ex.END_TIME)
                mBinding.tvStartTime.text = startTime
                mBinding.tvEndTime.text = endTime
                mBinding.sbProgress.progress = mBinding.sbProgress.getMax() * progress / 100
            }
        }
    }

}