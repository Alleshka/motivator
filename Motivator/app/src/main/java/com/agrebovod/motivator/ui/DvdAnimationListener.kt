package com.agrebovod.motivator.ui

import android.animation.Animator
import android.view.View
import android.view.ViewPropertyAnimator

class DvdAnimationListener(item: View, private val layout: View) :
    Animator.AnimatorListener {

    private val animatedItem: View

    private var curX: Float? = null
    private var curY: Float? = null

    private var spX: Float = 0.0f
    private var spY: Float = 0.0f

    var isStopped: Boolean = false

    init {
        animatedItem = item

        spX = 10 * (Math.pow((-1).toDouble(), (0..10).random().toDouble())).toFloat()
        spY = 10 * (Math.pow((-1).toDouble(), (0..10).random().toDouble())).toFloat()
    }


    fun animate(): ViewPropertyAnimator {
        if (this.curX == null || this.curY == null) {
            this.curX = animatedItem.x
            this.curY = animatedItem.y
        }

        this.curX = this.curX!! + spX
        this.curY = this.curY!! + spY

        if (this.curX!! < 0 || this.curX!! + animatedItem.width > layout.width) spX *= -1
        if (this.curY!! < 0 || this.curY!! + animatedItem.height > this.layout.height) spY *= -1

        return this.animatedItem.animate()
            .translationX(this.curX!!)
            .translationY(this.curY!!)
            .setDuration(10)
            .setListener(this)
    }

    override fun onAnimationRepeat(animation: Animator?) {
    }

    override fun onAnimationEnd(animation: Animator?) {
        animate()
    }

    override fun onAnimationCancel(animation: Animator?) {
    }

    override fun onAnimationStart(animation: Animator?) {
    }
}