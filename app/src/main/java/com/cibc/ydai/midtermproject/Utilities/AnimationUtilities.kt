package com.cibc.ydai.midtermproject.Utilities

import android.animation.ObjectAnimator
import android.support.constraint.ConstraintLayout
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.OvershootInterpolator

class AnimationUtilities {

    companion object {
        // Object animator
        fun animateViewOnY(view: View, startValue: Float, endValue: Float) {
            // animate the container on the screen by it's height
            val animator = ObjectAnimator.ofFloat(view, "translationY",startValue, endValue)
            animator.duration = 500 // 0.5 seconds
            animator.interpolator = OvershootInterpolator()
            animator.start()
        }

        fun animateViewOnX(view: View, startValue: Float, endValue: Float) {
            // animate the container on the screen by it's height
            val animator = ObjectAnimator.ofFloat(view, "translationX",startValue, endValue)
            animator.duration = 500 // 0.5 seconds
            animator.interpolator = AccelerateDecelerateInterpolator()
            animator.start()
        }
    }

}


