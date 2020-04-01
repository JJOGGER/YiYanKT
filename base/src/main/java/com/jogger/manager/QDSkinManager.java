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


import android.content.Context;
import android.content.res.Configuration;
import com.jogger.base.R;
import com.jogger.utils.LogUtils;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import ex.InitConfigKt;

public class QDSkinManager {
    public static final int SKIN_WHITE = 1;
    public static final int SKIN_DARK = 2;


    public static void install(Context context) {
        QMUISkinManager skinManager = QMUISkinManager.defaultInstance(context);
        skinManager.addSkin(SKIN_WHITE, R.style.app_skin_white);
        skinManager.addSkin(SKIN_DARK, R.style.app_skin_dark);
        boolean isDarkMode = (context.getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
        int storeSkinIndex = QDPreferenceManager.getInstance(context).getSkinIndex();
        LogUtils.e("-----------storeSkinIndex:" + storeSkinIndex);
        if (isDarkMode && storeSkinIndex != SKIN_DARK) {
            skinManager.changeSkin(SKIN_DARK);
        } else{
            skinManager.changeSkin(storeSkinIndex);
        }
    }

    public static void changeSkin(int index) {
        QMUISkinManager.defaultInstance(InitConfigKt.getSApplication()).changeSkin(index);
        QDPreferenceManager.getInstance(InitConfigKt.getSApplication()).setSkinIndex(index);
    }

    public static int getCurrentSkin() {
        return QMUISkinManager.defaultInstance(InitConfigKt.getSApplication()).getCurrentSkin();
    }
}
