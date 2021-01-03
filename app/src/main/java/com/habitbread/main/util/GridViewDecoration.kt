package com.habitbread.main.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridViewDecoration(spanCount: Int, spacing: Int, includeEdge: Boolean): RecyclerView.ItemDecoration() {
    var spanCount: Int = -1
    var spacing: Int = -1
    var includeEdge: Boolean = false
    init {
        this.spanCount = spanCount
        this.spacing = spacing
        this.includeEdge = includeEdge
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position: Int = parent.getChildAdapterPosition(view)
        val column: Int = position%spanCount

        if(includeEdge) {
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount
//            if(position < spanCount) {
//                outRect.top = spacing
//            }
            //outRect.bottom = spacing
        }else {
            outRect.left = column * spacing / spanCount
            outRect.right = spacing - (column + 1) * spacing / spanCount
            if (position >= spanCount) {
                outRect.top = spacing
            }
        }
    }
}