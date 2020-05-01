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

class MorfView : View {

    internal var sizeX: Float = 0.toFloat()
    internal var sizeY: Float = 0.toFloat()

    private var mTimeAnimator: TimeAnimator? = null

    private var mCurrentPlayTime: Long = 0
    private var mDrawable: Drawable? = null
    private var mPartition: Drawable? = null
    private var mPosition: Int = 0
    private var mPositionY: Int = 0
    private var reverse: Boolean = false
    private var reverseY: Boolean = false
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
        mDrawable = ContextCompat.getDrawable(context, R.drawable.sweden_big)
        mPartition = ContextCompat.getDrawable(context, R.drawable.morph_transformer)
    }


    override fun onSizeChanged(width: Int, height: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(width, height, oldw, oldh)
        sizeX = getWidth().toFloat()
        sizeY = getHeight().toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        mDrawable?.setBounds(
            0,
            0,sizeX.toInt(), sizeY.toInt()
        )
        mDrawable?.draw(canvas)

        mPartition?.setBounds(
            mPosition,
            mPositionY, mPosition+(40 * sizeX / 100).toInt(), mPositionY+sizeY.toInt()
        )
        mPartition?.draw(canvas)

    }


    fun start() {
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

    fun initStartPosition(){
        mPosition=(-10* sizeX / 100).toInt()
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
            mCurrentPlayTime = mTimeAnimator!!.getCurrentPlayTime()
            mTimeAnimator!!.pause()
        }
    }

    fun resume() {
        if (mTimeAnimator != null && mTimeAnimator!!.isPaused()) {
            mTimeAnimator!!.start()
            mTimeAnimator!!.setCurrentPlayTime(mCurrentPlayTime)
        }
    }

    /**
     * Progress the animation by moving the stars based on the elapsed time
     * @param deltaMs time delta since the last frame, in millis
     */
    private fun updateState(deltaMs: Float) {
        if(!reverse){
            mPosition+=(5*sizeX/1000).toInt()
            if (mPosition > 70 * sizeX / 100) {
                reverse=true
            }
        }else{
            mPosition-=(5*sizeX/1000).toInt()
            if (mPosition < -10* sizeX / 100) {
                reverse=false
            }
        }


        if(!reverseY){
            mPositionY+=(5*sizeY/1000).toInt()
            if(mPositionY>26*sizeY/100){
                reverseY=true
            }
        }else{
            mPositionY-=(5*sizeY/1000).toInt()
            if(mPositionY<-21*sizeY/100){
                reverseY=false
            }
        }


    }
}
