#MyDemo
FragmentListPageAdapter用来替代FragmentPagerAdapter
 * 如果有很多的page,像listView一样很多数据需要展示的话,
 * 内部多了一个缓存不可见page状态的 SparseArray<Fragment.SavedState> mSavedState
 * 性能更好,更节省内存
 * 原理是把需要显示的page从缓存中取出来

