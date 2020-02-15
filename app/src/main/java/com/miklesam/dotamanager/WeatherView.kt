package com.miklesam.dotamanager

import android.animation.TimeAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class WeatherView : View {

    internal var kubikSize: Float = 0.toFloat()
    internal var sizeX: Float = 0.toFloat()
    internal var sizeY: Float = 0.toFloat()

    private var mTimeAnimator: TimeAnimator? = null
    internal var myPaint = Paint()

    internal var noStroke = Paint()
    internal var choosePaint = Paint()
    private var mBaseSpeed: Float = 0.toFloat()
    private var mCurrentPlayTime: Long = 0
    private var mDrawable: Drawable? = null

    private var mPosition: Int = 0
    private var Night = false
    //viewingview

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
        mDrawable = ContextCompat.getDrawable(context, R.drawable.sun)
        mBaseSpeed = BASE_SPEED * resources.displayMetrics.density
    }

    fun setBaseSpeed(speed: Int) {
        BASE_SPEED = speed
        mBaseSpeed = BASE_SPEED * resources.displayMetrics.density
    }

    override fun onSizeChanged(width: Int, height: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(width, height, oldw, oldh)
        sizeX = getWidth().toFloat()
        sizeY = getHeight().toFloat()
        kubikSize = getWidth().toFloat() / 10
        myPaint.setColor(Color.rgb(0, 0, 0))
        myPaint.setStyle(Paint.Style.STROKE)
        myPaint.setStrokeWidth(1f)

        choosePaint.setColor(Color.rgb(195, 167, 153))
        choosePaint.setStyle(Paint.Style.STROKE)
        choosePaint.setStrokeWidth(35f)



        noStroke.setColor(Color.rgb(195, 167, 153))

    }

    override fun onDraw(canvas: Canvas) {
        mDrawable?.setBounds(
            (mPosition + 9*sizeX/10 + (-kubikSize).toInt()).toInt(),
            (sizeY / 2 + (-kubikSize).toInt()).toInt(),
            (mPosition + 9*sizeX/10 + kubikSize.toInt()).toInt(),
            (sizeY / 2 + (kubikSize).toInt()).toInt()
        )
        mDrawable?.draw(canvas)
        canvas.drawLine(
            sizeX / 2,
            sizeY / 2 - kubikSize, sizeX / 2, sizeY / 2 - kubikSize + 2 * kubikSize, choosePaint
        )

        canvas.drawLine(
            29*sizeX / 100,
            sizeY / 2 - kubikSize, 29*sizeX / 100, sizeY / 2 - kubikSize + 2 * kubikSize, choosePaint
        )
        canvas.drawLine(
            71*sizeX / 100,
            sizeY / 2 - kubikSize, 71*sizeX / 100, sizeY / 2 - kubikSize + 2 * kubikSize, choosePaint
        )

        canvas.drawLine(
            0F,
            0F, sizeX, 0F, choosePaint
        )


        canvas.drawRect(0F,
            0F, 11*sizeX/100, sizeY, noStroke)


    }


    fun Start() {
        mTimeAnimator = TimeAnimator()
        mTimeAnimator!!.setTimeListener(object : TimeAnimator.TimeListener {
            override fun onTimeUpdate(animation: TimeAnimator, totalTime: Long, deltaTime: Long) {
                if (!isLaidOut) {
                    // Ignore all calls before the view has been measured and laid out.
                    return
                }
                updateState(deltaTime.toFloat())
                invalidate()
            }
        })
        mTimeAnimator!!.start()
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mTimeAnimator!!.cancel()
        mTimeAnimator!!.setTimeListener(null)
        mTimeAnimator!!.removeAllListeners()
        mTimeAnimator = null
    }

    /**
     * Pause the animation if it's running
     */
    fun pause() {
        if (mTimeAnimator != null && mTimeAnimator!!.isRunning()) {
            // Store the current play time for later.
            mCurrentPlayTime = mTimeAnimator!!.getCurrentPlayTime()
            mTimeAnimator!!.pause()
        }
    }

    /**
     * Resume the animation if not already running
     */
    fun resume() {
        if (mTimeAnimator != null && mTimeAnimator!!.isPaused()) {
            mTimeAnimator!!.start()
            // Why set the current play time?
            // TimeAnimator uses timestamps internally to determine the delta given
            // in the TimeListener. When resumed, the next delta received will the whole
            // pause duration, which might cause a huge jank in the animation.
            // By setting the current play time, it will pick of where it left off.
            mTimeAnimator!!.setCurrentPlayTime(mCurrentPlayTime)
        }
    }

    /**
     * Progress the animation by moving the stars based on the elapsed time
     * @param deltaMs time delta since the last frame, in millis
     */
    private fun updateState(deltaMs: Float) {
        // Converting to seconds since PX/S constants are easier to understand
        val deltaSeconds = deltaMs / 1000f
        val viewWidth = width.toFloat()
        val viewHeight = height
        mPosition--
        if (mPosition<-8*sizeX/10){
            swap()
            mPosition=0
        }


    }
    fun swap(){
        Night=!Night
        mDrawable = if (Night){
            ContextCompat.getDrawable(context, R.drawable.moon)
        }else{
            ContextCompat.getDrawable(context, R.drawable.sun)
        }

    }

    companion object {
        private var BASE_SPEED = 1200
        private val COUNT = 35
    }


}
