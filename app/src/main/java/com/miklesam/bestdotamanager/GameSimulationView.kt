package com.miklesam.bestdotamanager

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class GameSimulationView : View {


    private var mDrawable: Drawable? = null
    internal var myPaint = Paint()
    internal var sizeX: Float = 0.toFloat()
    internal var sizeY: Float = 0.toFloat()


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        mDrawable = ContextCompat.getDrawable(context, R.drawable.minimap_7_23)
    }


    override fun onSizeChanged(width: Int, height: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(width, height, oldw, oldh)
        sizeX = getWidth().toFloat()
        sizeY = getHeight().toFloat()
        myPaint.setColor(Color.rgb(0, 0, 0))
        myPaint.setStyle(Paint.Style.STROKE)
        myPaint.setStrokeWidth(1f)


    }

    override fun onDraw(canvas: Canvas) {

        canvas.drawRGB(129, 156, 169)
        mDrawable?.setBounds(
            -200,
            -200,
            200,
            200
        )

        mDrawable?.draw(canvas)


    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }




}