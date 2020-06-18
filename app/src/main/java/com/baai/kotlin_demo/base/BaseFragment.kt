package com.baai.kotlin_demo.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.baai.kotlin_demo.constant.Constant
import com.baai.kotlin_demo.event.NetworkChangeEvent
import com.baai.kotlin_demo.utils.Preferences
import com.cxz.multiplestatusview.MultipleStatusView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseFragment : Fragment() {
    /**
     * 检查登录
     */
    protected var isLogin: Boolean by Preferences(Constant.LOGIN_KEY, false)

    /**
     * 缓存上一次网络状态
     */
    protected var hasNetwork: Boolean by Preferences(Constant.HAS_NETWORK_KEY, false)
    /***
     * 视图是否加载完毕
     */
    private var isViewPrepare=false
    /**
     * 数据是否加载过
     */
    private var hasLoadData=false
    /**
     * 多种状态的View切换
     */
    protected var mLayoutStatusView: MultipleStatusView? = null
    /**
     * 加载布局
     */
    abstract fun attachLayoutRes():Int
    /**
     * 初始化View
     */
   abstract fun  initView(view:View)
    /**
     * 懒加载
     */
    abstract fun lazyLoad()

    /**
     * 是否使用 EventBus
     */
    open fun useEventBus(): Boolean = true

    /**
     * 无网状态—>有网状态 的自动重连操作，子类可重写该方法
     */
    open fun doReConnected() {
        lazyLoad()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater?.inflate(attachLayoutRes(), null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (useEventBus()){
            EventBus.getDefault().register(this)
        }
        isViewPrepare = true
        initView(view)
        lazyLoadDataIfPrepared()
        mLayoutStatusView?.setOnRetryClickListener(mRetryClickListener)
    }


    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
            lazyLoad()
            hasLoadData = true
        }
    }

val mRetryClickListener:View.OnClickListener=View.OnClickListener {
   lazyLoad()
}
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNetworkChangeEvent(event: NetworkChangeEvent) {
        if (event.isConnected) {
            doReConnected()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
         if (useEventBus()){
             EventBus.getDefault().unregister(this)
         }
    }

}