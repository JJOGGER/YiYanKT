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

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.SparseIntArray;
import androidx.collection.ArrayMap;
import com.jogger.base.R;
import com.qmuiteam.qmui.qqface.IQMUIQQFaceManager;
import com.qmuiteam.qmui.qqface.QQFace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author cginechen
 * @date 2016-12-21
 */

public class QDQQFaceManager implements IQMUIQQFaceManager {
    private static final HashMap<String, Integer> sQQFaceMap = new HashMap<>();
    private static final List<QQFace> mQQFaceList = new ArrayList<>();
    private static final SparseIntArray sEmojisMap = new SparseIntArray(846);
    private static final SparseIntArray sSoftbanksMap = new SparseIntArray(471);
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection") private static final ArrayMap<String, String> mQQFaceFileNameList = new ArrayMap<>();//存储QQ表情对应的文件名,方便混淆后可以获取到原文件名

    private static QDQQFaceManager sQDQQFaceManager = new QDQQFaceManager();

    static {
        long start = System.currentTimeMillis();

        mQQFaceList.add(new QQFace("[icon]", R.drawable.icon_topicmark_3x));

        for (QQFace face : mQQFaceList) {
            sQQFaceMap.put(face.getName(), face.getRes());
        }
        mQQFaceFileNameList.put("[icon]", "smiley_0");

        Log.d("emoji", String.format("init emoji cost: %dms", (System.currentTimeMillis() - start)));
    }

    public static QDQQFaceManager getInstance() {
        return sQDQQFaceManager;
    }

    @Override
    public Drawable getSpecialBoundsDrawable(CharSequence text) {
        return null;
    }

    @Override
    public int getSpecialDrawableMaxHeight() {
        return 0;
    }

    @Override
    public boolean maybeSoftBankEmoji(char c) {
        return ((c >> 12) == 0xe);
    }

    @Override
    public boolean maybeEmoji(int codePoint) {
        return codePoint > 0xff;
    }

    @Override
    public int getEmojiResource(int codePoint) {
        return sEmojisMap.get(codePoint);
    }

    @Override
    public int getSoftbankEmojiResource(char c) {
        return sSoftbanksMap.get(c);
    }

    @Override
    public int getDoubleUnicodeEmoji(int currentCodePoint, int nextCodePoint) {
        return 0;
    }

    @Override
    public int getQQfaceResource(CharSequence text) {
        Integer integer = sQQFaceMap.get(text.toString());
        if (integer == null) {
            return 0;
        }
        return integer;
    }
}
