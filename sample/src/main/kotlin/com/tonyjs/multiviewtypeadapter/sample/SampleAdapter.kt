package com.tonyjs.multiviewtypeadapter.sample

import com.tonyjs.multiviewtypeadapter.MultiViewTypeAdapter
import kotlinx.android.synthetic.main.item_header.view.*
import kotlinx.android.synthetic.main.item_text.view.*

/**
 * Created by junseon on 2017. 4. 3..
 */
class SampleAdapter : MultiViewTypeAdapter() {

    enum class ViewType(val value: Int) {
        HEADER(0),
        TEXT(1),
        DIVIDER(2)
    }

    override fun registerBinders() {
        registerBinder<String>(ViewType.HEADER.value, R.layout.item_header) { holder, text ->
            holder.itemView.tvHeaderTitle.text = text
        }
        registerBinder<String>(ViewType.TEXT.value, R.layout.item_text) { holder, text ->
            holder.itemView.tvTitle.text = text
        }
        registerBinder<Float>(ViewType.DIVIDER.value, R.layout.item_divider) { _, _ ->

        }
    }

}