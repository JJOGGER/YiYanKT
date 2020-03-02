/*
 * Tencent is pleased to support the open source community by making QMUI_Android available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jogger.manager;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.jogger.base.R;
import com.qmuiteam.qmui.span.QMUIBlockSpaceSpan;
import com.qmuiteam.qmui.span.QMUITouchableSpan;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIPackageHelper;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

public class UpgradeTipTask implements UpgradeTask {
    private final int mOldVersion;
    private final int mNewVersion;

    public UpgradeTipTask(int oldVersion, int newVersion) {
        mOldVersion = oldVersion;
        mNewVersion = newVersion;
    }

    @Override
    public void upgrade() {
        throw new RuntimeException("please call upgrade(Activity activity)");
    }


    private void appendBlockSpace(Context context, SpannableStringBuilder builder) {
        int start = builder.length();
        builder.append("[space]");
        QMUIBlockSpaceSpan blockSpaceSpan = new QMUIBlockSpaceSpan(QMUIDisplayHelper.dp2px(context, 6));
        builder.setSpan(blockSpaceSpan, start, builder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

}
