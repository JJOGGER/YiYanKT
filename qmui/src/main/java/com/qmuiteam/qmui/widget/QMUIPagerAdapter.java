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

package com.qmuiteam.qmui.widget;

import android.util.SparseArray;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

/**
 * @author cginechen
 * @date 2017-09-13
 */

public abstract class QMUIPagerAdapter extends PagerAdapter {
    private SparseArray<Object> mScrapItems = new SparseArray<>();

    public QMUIPagerAdapter() {
    }


    /**
     * Hydrating an object is taking an object that exists in memory,
     * that doesn't yet contain any domain data ("real" data),
     * and then populating it with domain data.
     */
    protected abstract Object hydrate(ViewGroup container, int position);

    protected abstract void populate(ViewGroup container, Object item, int position);

    protected abstract void destroy(ViewGroup container, int position, Object object);

    @Override
    public final Object instantiateItem(ViewGroup container, int position) {
        Object item = mScrapItems.get(position);
        if (item == null) {
            item = hydrate(container, position);
            mScrapItems.put(position, item);
        }
        populate(container, item, position);
        return item;
    }

    @Override
    public final void destroyItem(ViewGroup container, int position, Object object) {
        destroy(container, position, object);

    }

    /**
     * sometimes you may need to perform some operations on all items,
     * such as perform cleanup when the ViewPager is destroyed
     * notice: this not
     *
     * @param action
     */
    public void each(@NonNull Action action) {
        int size = mScrapItems.size();
        for (int i = 0; i < size; i++) {
            Object item = mScrapItems.indexOfValue(i);
            if (action.call(item)) {
                break;
            }
        }
    }

    public interface Action {
        /**
         * @return true to intercept forEach
         */
        boolean call(Object item);
    }
}
