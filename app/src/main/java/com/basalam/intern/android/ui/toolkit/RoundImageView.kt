package com.basalam.intern.android.ui.toolkit

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.basalam.intern.android.R
import kotlin.math.min

/**
 * By the grace of Allah, Created by Sayed-MohammadReza Kazemi
 * on 11/13/20.
 */
class RoundImageView(context: Context, attr: AttributeSet) : AppCompatImageView(context, attr) {

    companion object {
        const val circle: String = "cir"
        const val rectangle = "rect"

    }

    private var type = rectangle
    private var radius = resources.getDimension(R.dimen._15sdp)
    private lateinit var rect: RectF
    private val path = Path()

    init {

        scaleType = ScaleType.CENTER_CROP


    }

    override fun onDraw(canvas: Canvas) {


        rect = RectF(0f, 0f, width.toFloat(), height.toFloat())

        if (type == circle) {
            path.addCircle(
                (width / 2).toFloat(),
                (height / 2).toFloat(),
                calculateRadius(width, height),
                Path.Direction.CW
            )

        } else if (type == rectangle) {
            path.addRoundRect(rect, radius, radius, Path.Direction.CW)
        }

        canvas.clipPath(path)

        super.onDraw(canvas)
    }

    private fun calculateRadius(width: Int, height: Int): Float {

        return if (radius == 0f) {

            (min(width, height) / 2.1).toFloat()

        } else {

            radius

        }

    }


}