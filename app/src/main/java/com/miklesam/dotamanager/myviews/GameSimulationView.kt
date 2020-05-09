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
import android.util.Log
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.datamodels.Hero
import com.miklesam.dotamanager.datamodels.Heroes
import com.miklesam.dotamanager.utils.Lanes


class GameSimulationView : View {


    private var mDrawable: Drawable? = null
    val mHero:Array<Drawable?> = arrayOfNulls(10)
    internal var Win = 0
    internal var myPaint = Paint()
    internal var radiantPaint = Paint()
    internal var direPaint = Paint()

    internal var victoryRad = Paint()
    internal var victoryDire = Paint()
    internal var sizeX: Float = 0.toFloat()
    internal var sizeY: Float = 0.toFloat()
    internal var myDeltaSec: Float = 0.toFloat()

    internal val deltaX =arrayOf(0,0,0,0,0,0,0,0,0,0)
    internal val deltaY =arrayOf(0,0,0,0,0,0,0,0,0,0)
    internal val blockX =arrayOf(0,0,0,0,0,0,0,0,0,0)
    internal val blockY =arrayOf(0,0,0,0,0,0,0,0,0,0)
    internal val reverseX =arrayOf(false,false,false,false,false,false,false,false,false,false)
    internal val reverseY =arrayOf(false,false,false,false,false,false,false,false,false,false)

    internal var RadiantTowers = listOf(true,true,true,true,true,true,true,true,true,true)
    internal var DireTowers = listOf(true,true,true,true,true,true,true,true,true,true)

    val mTimeAnimator = TimeAnimator()
    var mCurrentPlayTime:Long=0

    internal val hero =arrayOf(
        Hero(0, 0),
        Hero(0, 0),
        Hero(0, 0),
        Hero(0, 0),
        Hero(0, 0),
        Hero(0, 0)
        ,
        Hero(0, 0),
        Hero(0, 0),
        Hero(0, 0),
        Hero(0, 0)
    )

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
            R.drawable.minimap_7_23
        )

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
        radiantPaint.setColor(Color.rgb(76,255,0))
        direPaint.setColor(Color.rgb(255,0,0))



        victoryRad.setAntiAlias(true);
        victoryRad.setColor(Color.rgb(76,255,0))
        victoryRad.setTextSize(75.0f);
        victoryRad.setStrokeWidth(2.0f);
        victoryRad.setStyle(Paint.Style.STROKE);
        victoryRad.setShadowLayer(5.0f, 10.0f, 10.0f, Color.BLACK);

        victoryDire.setAntiAlias(true);
        victoryDire.setColor(Color.rgb(255,0,0))
        victoryDire.setTextSize(75.0f);
        victoryDire.setStrokeWidth(2.0f);
        victoryDire.setStyle(Paint.Style.STROKE);
        victoryDire.setShadowLayer(5.0f, 10.0f, 10.0f, Color.BLACK);
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRGB(63, 63, 63)
        mDrawable?.setBounds(
            0,
            0,
            (sizeX).toInt(),
            sizeY.toInt()
        )

        mDrawable?.draw(canvas)
        for(i in mHero.indices){
            mHero[i]?.setBounds(hero[i].positionX,hero[i].positionY,
                (0.07*sizeX+hero[i].positionX).toInt(), (0.07*sizeY+hero[i].positionY).toInt()
            )
            mHero[i]?.draw(canvas)
        }

        if(RadiantTowers[9])canvas.drawCircle(14*sizeX/100,82*sizeY/100,4*sizeX/100,radiantPaint)

        if(RadiantTowers[0]) canvas.drawRect(41*sizeX/100, 53*sizeY/100, 44*sizeX/100, 56*sizeY/100,radiantPaint)    //RMT1
        if(RadiantTowers[1])canvas.drawRect(29*sizeX/100, 64*sizeY/100, 32*sizeX/100, 67*sizeY/100,radiantPaint)    //RMT2
        if(RadiantTowers[2])canvas.drawRect(21*sizeX/100, 72*sizeY/100, 24*sizeX/100, 75*sizeY/100,radiantPaint)    //RMT3

        if(RadiantTowers[3])canvas.drawRect(26*sizeX/100, 85*sizeY/100, 29*sizeX/100, 88*sizeY/100,radiantPaint)    //RBT3
        if(RadiantTowers[4])canvas.drawRect(46*sizeX/100, 86*sizeY/100, 49*sizeX/100, 89*sizeY/100,radiantPaint)    //RBT2
        if(RadiantTowers[5])canvas.drawRect(83*sizeX/100, 85*sizeY/100, 86*sizeX/100, 88*sizeY/100,radiantPaint)    //RBT1

        if(RadiantTowers[6])canvas.drawRect(8*sizeX/100, 68*sizeY/100, 11*sizeX/100, 71*sizeY/100,radiantPaint)    //RTT3
        if(RadiantTowers[7])canvas.drawRect(9*sizeX/100, 54*sizeY/100, 12*sizeX/100, 57*sizeY/100,radiantPaint)    //RTT2
        if(RadiantTowers[8])canvas.drawRect(9*sizeX/100, 36*sizeY/100, 12*sizeX/100, 39*sizeY/100,radiantPaint)    //RTT1

        if(DireTowers[0])canvas.drawRect(51*sizeX/100, 44*sizeY/100, 54*sizeX/100, 47*sizeY/100,direPaint)    //DMT1
        if(DireTowers[1])canvas.drawRect(63*sizeX/100, 34*sizeY/100, 66*sizeX/100, 37*sizeY/100,direPaint)    //DMT2
        if(DireTowers[2])canvas.drawRect(74*sizeX/100, 25*sizeY/100, 77*sizeX/100, 28*sizeY/100,direPaint)    //DMT3


        if(DireTowers[3])canvas.drawRect(87*sizeX/100, 32*sizeY/100, 90*sizeX/100, 35*sizeY/100,direPaint)    //DBT3
        if(DireTowers[4])canvas.drawRect(87*sizeX/100, 45*sizeY/100, 90*sizeX/100, 48*sizeY/100,direPaint)    //DBT2
        if(DireTowers[5])canvas.drawRect(87*sizeX/100, 62*sizeY/100, 90*sizeX/100, 65*sizeY/100,direPaint)    //DBT1

        if(DireTowers[6])canvas.drawRect(69*sizeX/100, 13*sizeY/100, 72*sizeX/100, 16*sizeY/100,direPaint)    //DTT3
        if(DireTowers[7])canvas.drawRect(47*sizeX/100, 11*sizeY/100, 50*sizeX/100, 14*sizeY/100,direPaint)    //DTT2
        if(DireTowers[8])canvas.drawRect(19*sizeX/100, 12*sizeY/100, 22*sizeX/100, 15*sizeY/100,direPaint)    //DTT1

        if(DireTowers[9])canvas.drawCircle(84*sizeX/100,20*sizeY/100,4*sizeX/100,direPaint)

        when(Win) {
            1->canvas.drawText("Radiant Victory", 20*sizeX/100, 10*sizeY/100, victoryRad)
            2->canvas.drawText("Dire Victory", 25*sizeX/100, 10*sizeY/100, victoryDire)
        }


    }

    fun setBasePosition(){
        for (i in 0 until 5){
            hero[i].positionX= ((0.05+i*0.01)*sizeX).toInt()
            hero[i].positionY= ((0.85-i*0.01)*sizeY).toInt()
            hero[i+5].positionX= ((0.9-i*0.01)*sizeX).toInt()
            hero[i+5].positionY= ((0.1+i*0.01)*sizeY).toInt()
        }

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

        for (i in 0 until hero.size){
            if(reverseX[i]){
                if (hero[i].positionX>blockX[i]){
                    hero[i].positionX+=deltaX[i]
                }
            }else{
                if (blockX[i]>hero[i].positionX){
                    hero[i].positionX+=deltaX[i]
                }
            }
            if(reverseY[i]){
                if (hero[i].positionY>blockY[i]){
                    hero[i].positionY+=deltaY[i]
                }
            }else{
                if (blockY[i]>hero[i].positionY){
                    hero[i].positionY+=deltaY[i]
                }
            }
        }
    }

    fun CalcilateSpeed(position:Array<Int>){
        for(i in 0 until hero.size){
            val currentX=hero[i].positionX
            val currentY=hero[i].positionY
            reverseX[i]=(Lanes.values()[position[i]].positionX)*sizeX/100<hero[i].positionX
            reverseY[i]=(Lanes.values()[position[i]].positionY*sizeY)/100<hero[i].positionY
            blockX[i]= ((Lanes.values()[position[i]].positionX)*sizeX/100).toInt()
            blockY[i]=((Lanes.values()[position[i]].positionY)*sizeY/100).toInt()
            deltaX[i]= ((blockX[i]-currentX)/130)
            deltaY[i]=((blockY[i]-currentY)/130)
        }
    }

    fun setTowers(towersBuild:List<Boolean>){
           RadiantTowers=towersBuild.subList(0,10)
           DireTowers=towersBuild.subList(10,20)
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

    fun initiateWin(side:Int){
        Win=side
    }



    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

    }

    override fun onDetachedFromWindow() {
        mTimeAnimator.cancel()
        super.onDetachedFromWindow()
    }


    fun initHeroes(radiant:ArrayList<Int>,dire:ArrayList<Int>){
        mHero[0]=ContextCompat.getDrawable(context, Heroes.values().find { it.id==radiant[0]}!!.mipmap)
        mHero[1]=ContextCompat.getDrawable(context,Heroes.values().find { it.id==radiant[1]}!!.mipmap)
        mHero[2]=ContextCompat.getDrawable(context,Heroes.values().find { it.id==radiant[2]}!!.mipmap)
        mHero[3]=ContextCompat.getDrawable(context,Heroes.values().find { it.id==radiant[3]}!!.mipmap)
        mHero[4]=ContextCompat.getDrawable(context,Heroes.values().find { it.id==radiant[4]}!!.mipmap)

        mHero[5]=ContextCompat.getDrawable(context, Heroes.values().find { it.id==dire[0]}!!.mipmap)
        mHero[6]=ContextCompat.getDrawable(context, Heroes.values().find { it.id==dire[1]}!!.mipmap)
        mHero[7]=ContextCompat.getDrawable(context, Heroes.values().find { it.id==dire[2]}!!.mipmap)
        mHero[8]=ContextCompat.getDrawable(context, Heroes.values().find { it.id==dire[3]}!!.mipmap)
        mHero[9]=ContextCompat.getDrawable(context, Heroes.values().find { it.id==dire[4]}!!.mipmap)
    }


}