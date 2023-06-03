package com.xwray.groupie.example.item

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.example.currencyapp.R
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

open class HeaderItem(val title: String) : Item<GroupieViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.item_header
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.findViewById<AppCompatTextView>(R.id.tvHeader).setText(title)
    }
}

class ExpandableHeaderItem(titleStringResId: String) : HeaderItem(titleStringResId), ExpandableItem {
    var clickListener: ((ExpandableHeaderItem) -> Unit)? = null
    private lateinit var expandableGroup: ExpandableGroup
    private val actualClickListener = View.OnClickListener {
        clickListener?.invoke(this)
    }
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        super.bind(viewHolder, position)
        viewHolder.itemView.setOnClickListener(actualClickListener)
    }
    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        this.expandableGroup = onToggleListener
    }
}