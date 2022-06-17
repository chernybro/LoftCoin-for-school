package com.chernybro.loftcoin.utils.handlers

import android.content.Context
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener

class OnItemClick(context: Context, listener: View.OnClickListener) : OnItemTouchListener {
    private val gestureDetector: GestureDetectorCompat
    private val listener: View.OnClickListener
    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val view = rv.findChildViewUnder(e.x, e.y)
        if (view != null && gestureDetector.onTouchEvent(e)) {
            listener.onClick(view)
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

    init {
        gestureDetector = GestureDetectorCompat(context,
            object : SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    return true
                }
            })
        this.listener = listener
    }
}