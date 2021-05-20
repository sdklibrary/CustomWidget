package com.pretty.library.widget.pager

import android.content.Context
import android.util.AttributeSet
import android.util.SparseArray
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.pretty.library.widget.R

@Suppress("UNCHECKED_CAST")
class PagerStatusView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private val defaultValue = 0

    @PagerStatus
    private var mStatus = PagerStatus.GONE

    //空页面
    private lateinit var emptyView: View
    private val emptyChildView = SparseArray<View>()

    //错误页面
    private lateinit var errorView: View
    private val errorChildView = SparseArray<View>()

    //加载
    private lateinit var loadingView: View
    private val loadingChildView = SparseArray<View>()

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.PagerStatusView)
        val loadingLayoutId = array.getResourceId(
            R.styleable.PagerStatusView_loading_layoutId, loadingLayoutId
        )
        val emptyLayoutId = array.getResourceId(
            R.styleable.PagerStatusView_empty_layoutId, emptyLayoutId
        )
        val errorLayoutId = array.getResourceId(
            R.styleable.PagerStatusView_error_layoutId, errorLayoutId
        )
        val status = array.getInt(R.styleable.PagerStatusView_status, PagerStatus.GONE)
        if (loadingLayoutId != defaultValue) {
            setLoadingView(loadingLayoutId)
        }
        if (emptyLayoutId != defaultValue) {
            setEmptyView(emptyLayoutId)
        }
        if (errorLayoutId != defaultValue) {
            setErrorView(errorLayoutId)
        }
        setStatus(status)
        array.recycle()
    }

    private fun findLoadingChildView(
        childViewId: Int
    ): View? {
        return loadingChildView[childViewId] ?: try {
            val loadingView = getLoadingStatusView()
            check(loadingView != null) {
                "Loading view is not initialize. Please call setLoadingView"
            }
            val childView = loadingView.findViewById<View>(childViewId)
            loadingChildView.put(childViewId, childView)
            childView
        } catch (e: Exception) {
            null
        }
    }

    private fun findEmptyChildView(
        childViewId: Int
    ): View? {
        return emptyChildView[childViewId] ?: try {
            val emptyView = getEmptyStatusView()
            check(emptyView != null) {
                "Empty view is not initialize. Please call setEmptyView"
            }
            val childView = emptyView.findViewById<View>(childViewId)
            emptyChildView.put(childViewId, childView)
            childView
        } catch (e: Exception) {
            null
        }
    }

    private fun findErrorChildView(
        childViewId: Int
    ): View? {
        return errorChildView[childViewId] ?: try {
            val errorView = getErrorStatusView()
            check(errorView != null) {
                "Error view is not initialize. Please call setErrorView"
            }
            val childView = errorView.findViewById<View>(childViewId)
            errorChildView.put(childViewId, childView)
            childView
        } catch (e: Exception) {
            null
        }
    }

    /**
     * 获取loadingStatusView
     */
    fun getLoadingStatusView(): View? {
        if (::loadingView.isInitialized) {
            return loadingView
        }
        if (loadingStatusView != null) {
            loadingView = loadingStatusView!!
            return loadingView
        }
        return null
    }

    /**
     * 设置加载Loading页面布局ID
     */
    fun setLoadingView(
        @LayoutRes layoutId: Int
    ) {
        this.loadingChildView.clear()
        this.loadingView = inflate(context, layoutId, null)
    }

    /**
     * 初始化Loading页面的子View
     */
    fun <T : View> initLoadingChildView(
        @IdRes childViewId: Int,
        initializeValue: (T.() -> Unit)? = null,
        clickViewAction: (T.() -> Unit)? = null
    ) {
        val loadingView = getLoadingStatusView()
        check(loadingView != null) {
            "Loading view is not initialize. Please call setLoadingView"
        }
        val childView = loadingView.findViewById<T>(childViewId)
        check(childView != null) {
            "Not found corrected view by childViewId=$childViewId"
        }
        initializeValue?.invoke(childView)
        clickViewAction?.invoke(childView)
        this.loadingChildView.put(childViewId, childView)
    }

    /**
     * 设置空页面中childView的数据
     */
    fun <T : View> setLoadingChildData(
        @IdRes childViewId: Int,
        init: (T.() -> Unit)? = null
    ) {
        val view = findLoadingChildView(childViewId)
        check(view != null) {
            "Not found corrected view by childViewId=$childViewId"
        }
        init?.invoke(view as T)
    }

    /***************************************************************/

    /**
     * 获取空状态的view
     */
    fun getEmptyStatusView(): View? {
        if (::emptyView.isInitialized) {
            return emptyView
        }
        if (emptyStatusView != null) {
            emptyView = emptyStatusView!!
            return emptyView
        }
        return null
    }

    /**
     * 设置空页面的布局ID
     */
    fun setEmptyView(@LayoutRes layoutId: Int) {
        this.emptyChildView.clear()
        this.emptyView = inflate(context, layoutId, null)
    }

    /**
     * 初始化空页的子View
     */
    fun <T : View> initEmptyChildView(
        @IdRes childViewId: Int,
        initializeValue: (T.() -> Unit)? = null,
        clickViewAction: (T.() -> Unit)? = null
    ) {
        val emptyView = getEmptyStatusView()
        check(emptyView != null) {
            "Empty view is not initialize. Please call setEmptyView"
        }
        val childView = emptyView.findViewById<T>(childViewId)
        check(childView != null) {
            "Not found corrected view by childViewId=$childViewId"
        }
        initializeValue?.invoke(childView)
        clickViewAction?.invoke(childView)
        this.emptyChildView.put(childViewId, childView)
    }

    /**
     * 设置空页面中childView的数据
     */
    fun <T : View> setEmptyChildData(
        @IdRes childViewId: Int,
        init: (T.() -> Unit)? = null
    ) {
        val view = findEmptyChildView(childViewId)
        check(view != null) {
            "Not found corrected view by childViewId=$childViewId"
        }
        init?.invoke(view as T)
    }

    /**
     * 设置空页面的点击事件
     */
    fun setEmptyClick(
        @IdRes childViewId: Int,
        action: View.() -> Unit
    ) {
        val view = findEmptyChildView(childViewId)
        check(view != null) {
            "Not found corrected view by childViewId=$childViewId"
        }
        view.setOnClickListener { action.invoke(view) }
    }

    /***************************************************************/

    /**
     * 获取错误状态View
     */
    fun getErrorStatusView(): View? {
        if (::errorView.isInitialized) {
            return errorView
        }
        if (errorStatusView != null) {
            errorView = errorStatusView!!
            return errorView
        }
        return null
    }

    /**
     * 设置错误页面布局ID
     */
    fun setErrorView(@LayoutRes layoutId: Int) {
        this.errorChildView.clear()
        this.errorView = inflate(context, layoutId, null)
    }

    /**
     * 始化错误页面的子View
     */
    fun <T : View> initErrorChildView(
        @IdRes childViewId: Int,
        initializeValue: (T.() -> Unit)? = null,
        clickViewAction: (T.() -> Unit)? = null
    ) {
        val errorView = getErrorStatusView()
        check(errorView != null) {
            "Error view is not initialize. Please call setErrorView"
        }
        val childView = errorView.findViewById<T>(childViewId)
        initializeValue?.invoke(childView)
        clickViewAction?.invoke(childView)
        this.errorChildView.put(childViewId, childView)
    }

    /**
     * 设置错误专状态数据的数据
     */
    fun <T : View> setErrorChildData(
        @IdRes childViewId: Int,
        init: (T.() -> Unit)? = null
    ) {
        val view = findErrorChildView(childViewId)
        check(view != null) {
            "Not found corrected view by childViewId=$childViewId"
        }
        init?.invoke(view as T)
    }

    /**
     * 设置错误页面的点击事件
     */
    fun setErrorClick(
        @IdRes childViewId: Int,
        click: View.() -> Unit
    ) {
        val view = findErrorChildView(childViewId)
        check(view != null) {
            "Not found corrected view by childViewId=$childViewId"
        }
        view.setOnClickListener { click.invoke(view) }
    }

    /*******************************************************************************/

    /**
     * 设置页面的状态
     */
    fun setStatus(@PagerStatus status: Int) {
        visibility = if (status == PagerStatus.GONE) {
            GONE
        } else {
            if (status != this.mStatus) {
                removeAllViews()
                when (status) {
                    PagerStatus.LOADING -> {
                        val loadingView = getLoadingStatusView()
                        if (loadingView != null) {
                            addView(loadingView)
                            VERTICAL
                        } else {
                            GONE
                        }
                    }
                    PagerStatus.EMPTY -> {
                        val emptyView = getEmptyStatusView()
                        if (emptyView != null) {
                            addView(emptyView)
                            VERTICAL
                        } else {
                            GONE
                        }
                    }
                    PagerStatus.ERROR -> {
                        val errorView = getErrorStatusView()
                        if (errorView != null) {
                            addView(errorView)
                            VERTICAL
                        } else {
                            GONE
                        }
                    }
                    else -> GONE
                }
            } else {
                VISIBLE
            }
        }
        this.mStatus = status
    }

    companion object {

        internal var loadingStatusView: View? = null
        internal var loadingLayoutId = 0
        internal var emptyStatusView: View? = null
        internal var emptyLayoutId = 0
        internal var errorStatusView: View? = null
        internal var errorLayoutId = 0

        fun setLoadingLayout(@LayoutRes layoutId: Int) {
            this.loadingLayoutId = layoutId
        }

        fun setLoadingStatusView(statusView: View) {
            this.loadingStatusView = statusView
        }

        fun setEmptyLayout(@LayoutRes layoutId: Int) {
            this.emptyLayoutId = layoutId
        }

        fun setEmptyStatusView(statusView: View) {
            this.emptyStatusView = statusView
        }

        fun setErrorLayout(@LayoutRes layoutId: Int) {
            this.errorLayoutId = layoutId
        }

        fun setErrorStatusView(statusView: View) {
            this.errorStatusView = statusView
        }
    }
}