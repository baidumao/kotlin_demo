package com.baai.kotlin_demo.base

import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.cxz.wanandroid.base.IPresenter
import com.cxz.wanandroid.widget.SpaceItemDecoration

abstract class BaseMvpListFragment <in V:IView,P:IPresenter<V>>:BaseMvpFragment<V,P>() {
    /**
     * 每页数据的个数
     */
    protected var pageSize = 20

    /**
     * 是否是下拉刷新
     */
    protected var isRefresh = true

    /**
     * LinearLayoutManager
     */
    protected val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    /**
     * RecyclerView Divider
     */
    private val recyclerViewItemDecoration by lazy {
        activity?.let {
            SpaceItemDecoration(it)
        }
    }
    /**
     * RefreshListener
     */
    protected val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        isRefresh = true
        onRefreshList()
    }
    /**
     * LoadMoreListener
     */
    protected val onRequestLoadMoreListener = BaseQuickAdapter.RequestLoadMoreListener {
        isRefresh = false
//        swipeRefreshLayout.isRefreshing = false
        onLoadMoreList()
    }

    /**
     * 下拉刷新
     */
    abstract fun onRefreshList()

    /**
     * 上拉加载更多
     */
    abstract fun onLoadMoreList()

    override fun initView(view: View) {
        super.initView(view)

//        mLayoutStatusView = multiple_status_view
//
//        swipeRefreshLayout.run {
//            setOnRefreshListener(onRefreshListener)
//        }
//        recyclerView.run {
//            layoutManager = linearLayoutManager
//            itemAnimator = DefaultItemAnimator()
//            recyclerViewItemDecoration?.let { addItemDecoration(it) }
//        }

    }

    override fun showLoading() {
        // swipeRefreshLayout.isRefreshing = isRefresh
    }

    override fun hideLoading() {
//        swipeRefreshLayout?.isRefreshing = false
    }

    override fun showError(errorMsg: String) {
        super.showError(errorMsg)
        mLayoutStatusView?.showError()
    }
}