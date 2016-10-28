package com.example.li.demo.RecyclerViewDemo.CardLayoutManager;

import android.support.v4.util.SparseArrayCompat;

/**这个类好像是创建对象用的?
 * Created by qibin on 16-9-25.
 */

public class Pool<T> {
    private SparseArrayCompat<T> mPool;
    private New<T> mNewInstance;

    public Pool(New<T> newInstance) {
        mPool = new SparseArrayCompat<>();
        mNewInstance = newInstance;
    }

    public T get(int key) {
        T res = mPool.get(key);
        if (res == null) {
            res = mNewInstance.get();
            mPool.put(key, res);
        }
        return res;
    }

    public interface New<T> {
        T get();
    }
}
