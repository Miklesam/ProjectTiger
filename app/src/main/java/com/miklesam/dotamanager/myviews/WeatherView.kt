package com.miklesam.dotamanager.myviews

import android.animation.TimeAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.miklesam.dotamanager.R

class WeatherView : View {

    internal var kubikSize: Float = 0.toFloat()
    internal var sizeX: Float = 0.toFloat()
    internal var sizeY: Float = 0.toFloat()

    private var mTimeAnimator: TimeAnimator? = null

    internal var noStroke = Paint()
    internal var choosePaint = Paint()
    private var mCurrentPlayTime: Long = 0
    private var mDrawable: Drawable? = null
    private var mPartition: Drawable? = null
    private var mWall: Drawable? = null
    private var mPosition: Int = 0
    private var Night = false
    private var mView: View? = null
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
        mDrawable = ContextCompat.getDrawable(context,
            R.drawable.sun
        )
        mPartition = ContextCompat.getDrawable(context,
            R.drawable.newpartition
        )
        mWall = ContextCompat.getDrawable(context,
            R.drawable.wall
        )

    }


    override fun onSizeChanged(width: Int, height: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(width, height, oldw, oldh)
        sizeX = getWidth().toFloat()
        sizeY = getHeight().toFloat()
        kubikSize = getWidth().toFloat() / 10

        choosePaint.setColor(Color.rgb(195, 167, 153))
        choosePaint.setStyle(Paint.Style.STROKE)
        choosePaint.setStrokeWidth(35f)
        noStroke.setColor(Color.rgb(195, 167, 153))

    }

    override fun onDraw(canvas: Canvas) {

        mDrawable?.setBounds(
            (mPosition + 98 * sizeX / 100 + (-kubikSize).toInt()).toInt(),
            (30 * sizeY / 100 + (-kubikSize).toInt()).toInt(),
            (mPosition + 98 * sizeX / 100 + kubikSize.toInt()).toInt(),
            (30 * sizeY / 100 + (kubikSize).toInt()).toInt()
        )
        mDrawable?.draw(canvas)

        mPartition?.setBounds(
            (49 * sizeX / 100).toInt(),
            0, (51 * sizeX / 100).toInt(), sizeY.toInt()
        )
        mPartition?.draw(canvas)

        mPartition?.setBounds(
            (28 * sizeX / 100).toInt(),
            0, (30 * sizeX / 100).toInt(), sizeY.toInt()
        )
        mPartition?.draw(canvas)

        mPartition?.setBounds(
            (70 * sizeX / 100).toInt(),
            0, (72 * sizeX / 100).toInt(), sizeY.toInt()
        )
        mPartition?.draw(canvas)

        mWall?.setBounds(
            0,
            0, (10 * sizeX / 100).toInt(), sizeY.toInt()
        )
        mWall?.draw(canvas)


        mWall?.setBounds(
            (90 * sizeX / 100).toInt(),
            0, (sizeX).toInt(), sizeY.toInt()
        )
        mWall?.draw(canvas)
    }


    fun start(view: View) {
        mView = view
        val layout = mView?.findViewById<WeatherView>(
            R.id.weatherAnim
        )
        //layout?.setBackgroundResource(R.drawable.mars_nasa)
        layout?.setBackgroundResource(R.drawable.original_space)
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


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mTimeAnimator!!.cancel()
        mTimeAnimator!!.setTimeListener(null)
        mTimeAnimator!!.removeAllListeners()
        mTimeAnimator = null
    }

    fun pause() {
        if (mTimeAnimator != null && mTimeAnimator!!.isRunning()) {
            // Store the current play time for later.
            mCurrentPlayTime = mTimeAnimator!!.getCurrentPlayTime()
            mTimeAnimator!!.pause()
        }
    }

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
        mPosition--
        if (mPosition < -98 * sizeX / 100) {
            swap()
            mPosition = 0
        }

    }

    private fun swap() {
        Night = !Night
        if (Night) {
            mDrawable = ContextCompat.getDrawable(context,
                R.drawable.moonlite
            )
            val layout = mView?.findViewById<WeatherView>(
                R.id.weatherAnim
            )
            layout?.setBackgroundResource(R.drawable.panoramanew_dark)
        } else {
            mDrawable = ContextCompat.getDrawable(context,
                R.drawable.sun
            )
            val layout = mView?.findViewById<WeatherView>(
                R.id.weatherAnim
            )
            layout?.setBackgroundResource(R.drawable.panoramanew)

        }
    }
}
