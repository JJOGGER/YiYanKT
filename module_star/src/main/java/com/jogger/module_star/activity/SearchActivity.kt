package com.jogger.module_star.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import com.jogger.base.BaseActivity
import com.jogger.module_star.R
import com.jogger.module_star.viewmodel.SearchViewModel
import ex.showToast
import kotlinx.android.synthetic.main.star_activity_search.*

class SearchActivity : BaseActivity<SearchViewModel, ViewDataBinding>() {
    override fun getLayoutId(): Int=R.layout.star_activity_search

    override fun init(savedInstanceState: Bundle?) {
        et_search.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId==EditorInfo.IME_ACTION_SEARCH){
                    if (TextUtils.isEmpty(et_search.text.toString().trim())){
                        showToast("请输入搜索文字或用户名")
                        return true
                    }
                    return true
                }
                return false
            }
        })
    }

}
