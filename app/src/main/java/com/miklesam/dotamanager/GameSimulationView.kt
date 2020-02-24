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
import android.util.Log
import android.util.Size


class GameSimulationView : View {


    private var mDrawable: Drawable? = null
    private var mHero: Drawable? = null
    internal var myPaint = Paint()
    internal var radiantPaint = Paint()
    internal var sizeX: Float = 0.toFloat()
    internal var sizeY: Float = 0.toFloat()
    internal var myDeltaSec: Float = 0.toFloat()
    internal var deltaX: Int=0
    internal var deltaY: Int=0
    internal var blockX: Int=0
    internal var blockY: Int=0
    val mTimeAnimator = TimeAnimator()
    var mCurrentPlayTime:Long=0
    var reverseX=false
    var reverseY=false

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
        Log.w("InitSizeX=", sizeX.toString())
        Log.w("InitSizeY=", sizeY.toString())

    }


    override fun onSizeChanged(width: Int, height: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(width, height, oldw, oldh)
        sizeX = getWidth().toFloat()
        sizeY = getHeight().toFloat()
        Log.w("onSizeChangedSizeX=", sizeX.toString())
        Log.w("onSizeChangedSizeY=", sizeY.toString())
        myPaint.setColor(Color.rgb(0, 0, 0))
        myPaint.setStyle(Paint.Style.STROKE)
        myPaint.setStrokeWidth(1f)
        radiantPaint.setColor(Color.rgb(15,215,15))
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

        //canvas.drawRect(10*sizeX/100, 80*sizeY/100, 15*sizeX/100, 85*sizeY/100,radiantPaint)

        //canvas.drawRoundRect(10*sizeX/100, 80*sizeY/100, 15*sizeX/100, 85*sizeY/100,
          //  50F, 50F,radiantPaint)
        canvas.drawCircle(14*sizeX/100,82*sizeY/100,4*sizeX/100,radiantPaint)


        canvas.drawRect(41*sizeX/100, 53*sizeY/100, 44*sizeX/100, 56*sizeY/100,radiantPaint)

        //Radiant[0].endCoordinates(width / 2 - width / 10, height / 2 + 45)//RT1M
        //Radiant[1].endCoordinates(width / 3 - width / 30, 2 * height / 3 - height / 30)//RT2M
        //Radiant[2].endCoordinates(width / 6 + 5 * width / 100, 3 * height / 4 - 4 * height / 100)//RT3M

    }

    fun setBasePosition(){
        heroOne.positionX= (0.05*sizeX).toInt()
        heroOne.positionY= (0.85*sizeY).toInt()

        Log.w("BasePosition X", heroOne.positionX.toString())
        Log.w("BasePosition Y", heroOne.positionY.toString())
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

        if(reverseX){
            if (heroOne.positionX>blockX){
                heroOne.positionX+=deltaX
            }
        }else{
            if (blockX>heroOne.positionX){
                heroOne.positionX+=deltaX
            }
        }
        if(reverseY){
            if (heroOne.positionY>blockY){
                heroOne.positionY+=deltaY
            }
        }else{
            if (blockY>heroOne.positionY){
                heroOne.positionY+=deltaY
            }
        }

    }

    fun CalcilateSpeed(cardPosition:Int){

        val currentX=heroOne.positionX
        val currentY=heroOne.positionY

        reverseX=Lanes.values()[cardPosition].positionX*sizeX/100<heroOne.positionX
        reverseY=Lanes.values()[cardPosition].positionY*sizeY/100<heroOne.positionY
        //reverse = endX<startX
        blockX= (Lanes.values()[cardPosition].positionX*sizeX/100).toInt()
        blockY=(Lanes.values()[cardPosition].positionY*sizeY/100).toInt()

        deltaX= ((blockX-currentX)/130)
        deltaY=((blockY-currentY)/130)
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
        mTimeAnimator.cancel()
        super.onDetachedFromWindow()
    }


}