package com.miklesam.bestdotamanager

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.TimeAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log


class GameSimulationView : View {


    private var mDrawable: Drawable? = null
    private var mHero: Drawable? = null
    internal var myPaint = Paint()
    internal var sizeX: Float = 0.toFloat()
    internal var sizeY: Float = 0.toFloat()
    internal var myDeltaSec: Float = 0.toFloat()
    internal var deltaX: Int=0
    internal var deltaY: Int=0
    internal var blockX: Int=0

    internal var reverse: Boolean=false

    val mTimeAnimator = TimeAnimator()
    var mCurrentPlayTime:Long=0

    val heroOne=Hero(0,0)

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
        mHero=ContextCompat.getDrawable(context,R.drawable.ogremagi_mipmap)

    }


    override fun onSizeChanged(width: Int, height: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(width, height, oldw, oldh)
        sizeX = getWidth().toFloat()
        sizeY = getHeight().toFloat()

        Log.w("SizeX=", sizeX.toString())
        Log.w("SizeY=", sizeY.toString())
        myPaint.setColor(Color.rgb(0, 0, 0))
        myPaint.setStyle(Paint.Style.STROKE)
        myPaint.setStrokeWidth(1f)


    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRGB(63, 63, 63)
        mDrawable?.setBounds(
            0,
            0,
            (sizeX).toInt(),
            sizeY.toInt()
        )
        mHero?.setBounds(heroOne.positionX,heroOne.positionY,
            (0.0875*sizeX+heroOne.positionX).toInt(), (0.0875*sizeY+heroOne.positionY).toInt()
        )
        mDrawable?.draw(canvas)
        mHero?.draw(canvas)
    }





    fun Start() {
        mTimeAnimator.setTimeListener(TimeAnimator.TimeListener { animation, totalTime, deltaTime ->
            if (!isLaidOut) {
                // Ignore all calls before the view has been measured and laid out.
                return@TimeListener
            }
            //Log.w("deltaTime",deltaTime.toString())
            updateState(deltaTime.toFloat())
            invalidate()
        })
        mTimeAnimator.start()
    }


    private fun updateState(deltaMs: Float) {
        // Converting to seconds since PX/S constants are easier to understand
        val deltaSeconds = deltaMs / 1000f
        myDeltaSec=deltaSeconds
        val viewWidth = width.toFloat()
        val viewHeight = height
        if(reverse){
            if (heroOne.positionX>blockX){
                heroOne.positionX+=deltaX
            }
        }else{
            if (blockX>heroOne.positionX){
                heroOne.positionX+=deltaX
            }
        }

        heroOne.positionY+=deltaY

    }

    fun CalcilateSpeed(startX: Float,endX: Float){
        reverse = endX<startX
        blockX= endX.toInt()
        deltaX= ((blockX-startX)/130).toInt()
        Log.w("deltaX = ", deltaX.toString())
        Log.w("Hero.posX", heroOne.positionX.toString())
    }



    fun pause() {
        if (mTimeAnimator != null && mTimeAnimator.isRunning()) {
            // Store the current play time for later.
            mCurrentPlayTime = mTimeAnimator.getCurrentPlayTime()
            mTimeAnimator.pause()
        }
    }

    /**
     * Resume the animation if not already running
     */
    fun resume() {
        if (mTimeAnimator != null && mTimeAnimator.isPaused()) {
            mTimeAnimator.start()
            // Why set the current play time?
            // TimeAnimator uses timestamps internally to determine the delta given
            // in the TimeListener. When resumed, the next delta received will the whole
            // pause duration, which might cause a huge jank in the animation.
            // By setting the current play time, it will pick of where it left off.
            mTimeAnimator.setCurrentPlayTime(mCurrentPlayTime)
        }
    }



    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }


}