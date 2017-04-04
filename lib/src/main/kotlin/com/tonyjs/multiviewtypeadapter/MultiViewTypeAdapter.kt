package com.tonyjs.multiviewtypeadapter

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlin.reflect.KClass

/**
 * Created by tonyjs on 2017. 4. 2..
 */
abstract class MultiViewTypeAdapter : RecyclerView.Adapter<MultiViewTypeAdapter.ViewHolder>() {

    private val items = mutableListOf<Pair<Any, Int>>()

    val bindersMap = hashMapOf<Int, Binder>()

    inline fun <ITEM> registerBinder(viewType: Int, @LayoutRes layoutRes: Int,
                                     crossinline binder: (holder: ViewHolder, ITEM) -> Unit) {
        bindersMap.put(viewType, object : Binder {
            override val layoutResId: Int
                get() = layoutRes

            @Suppress("UNCHECKED_CAST")
            override fun <T> bind(holder: ViewHolder, item: T) {
                binder(holder, item as ITEM)
            }
        })
    }

    abstract fun registerBinders()

    fun addAll(items: List<Pair<Any, Int>>) {
        this.items.addAll(items)
    }

    fun <T : Any> addItem(item: T, viewType: Int) {
        items.add(Pair(item, viewType))
    }

    fun <T : Any> addItem(vararg pairs: Pair<T, Int>) {
        addAll(pairs.toMutableList())
    }

    fun removeItemAt(index: Int) {
        items.removeAt(index)
    }

    fun <T : Any> getItemAt(index: Int, clazz: KClass<T>): T {
        val item = items[index].first
        if (!(item.javaClass.isAssignableFrom(clazz.java))) {
            throw ClassCastException("item at index is not assignable from $clazz")
        }
        @Suppress("UNCHECKED_CAST") return item as T
    }

    fun <T : Any> setItemAt(index: Int, item: T, viewType: Int) {
        items[index] = Pair(item, viewType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val resId = bindersMap[viewType]?.layoutResId ?:
                throw NullPointerException("require layout resource id")
        val itemView = LayoutInflater.from(parent.context).inflate(resId, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        bindersMap[viewType]?.bind(holder, items[position].first)
    }

    override fun getItemViewType(position: Int): Int {
        if (bindersMap.isEmpty()) {
            registerBinders()
        }
        return items[position].second
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface Binder {

        @get:LayoutRes
        val layoutResId: Int

        fun <T> bind(holder: ViewHolder, item: T)

    }

}