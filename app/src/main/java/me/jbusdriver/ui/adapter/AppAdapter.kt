package me.jbusdriver.ui.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import me.jbusdriver.common.AppContext
import me.jbusdriver.common.KLog
import java.lang.ref.WeakReference

abstract class BaseAppAdapter<T, K : BaseViewHolder> : BaseQuickAdapter<T, K> {

    private val Tag by lazy { this::class.java.name ?: error("must have a class name") }

    constructor(layoutResId: Int, data: MutableList<T>?) : super(layoutResId, data)
    constructor(data: MutableList<T>?) : super(data)
    constructor(layoutResId: Int) : super(layoutResId)

    override fun bindToRecyclerView(recyclerView: RecyclerView?) {
        recyclerView?.let {
            (it as? LinearLayoutManager)?.recycleChildrenOnDetach = true

            val ref = AppContext.instace.recycledViewPoolHolder.getOrPut(Tag) {
                WeakReference(RecyclerView.RecycledViewPool())
            }

            val doGet =  ref.get() ?: RecyclerView.RecycledViewPool().apply {  AppContext.instace.recycledViewPoolHolder.put(Tag, WeakReference(this)) }
            it.recycledViewPool = doGet
        }
        KLog.t(Tag).i("bindToRecyclerView ${AppContext.instace.recycledViewPoolHolder.size} : ${AppContext.instace.recycledViewPoolHolder.keys.joinToString()}")
        super.bindToRecyclerView(recyclerView)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        KLog.t(Tag).i("onAttachedToRecyclerView")
    }

    override fun onViewAttachedToWindow(holder: K) {
        super.onViewAttachedToWindow(holder)
        KLog.t(Tag).i("onViewAttachedToWindow")
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
        super.onDetachedFromRecyclerView(recyclerView)
        KLog.t(Tag).i("onDetachedFromRecyclerView")
    }

    override fun onViewDetachedFromWindow(holder: K) {
        super.onViewDetachedFromWindow(holder)
        KLog.t(Tag).i("onViewDetachedFromWindow")
    }

}

abstract class BaseMultiItemAppAdapter<T : MultiItemEntity, K : BaseViewHolder>(data: List<T>?) : BaseMultiItemQuickAdapter<T, K>(data) {

    private val Tag by lazy { this::class.java.name ?: error("must have a class name") }


    override fun bindToRecyclerView(recyclerView: RecyclerView?) {
        recyclerView?.let {
            (it as? LinearLayoutManager)?.recycleChildrenOnDetach = true
            val ref = AppContext.instace.recycledViewPoolHolder.getOrPut(Tag) {
                WeakReference(RecyclerView.RecycledViewPool())
            }

            val doGet =  ref.get() ?: RecyclerView.RecycledViewPool().apply {  AppContext.instace.recycledViewPoolHolder.put(Tag, WeakReference(this)) }
            it.recycledViewPool = doGet
        }
        KLog.t(Tag).i("bindToRecyclerView ${AppContext.instace.recycledViewPoolHolder.size} : ${AppContext.instace.recycledViewPoolHolder.keys.joinToString()}")
        super.bindToRecyclerView(recyclerView)
    }

}