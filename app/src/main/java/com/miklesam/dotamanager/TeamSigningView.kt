package com.miklesam.dotamanager

import android.animation.TimeAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class TeamSigningView : View {

    internal var kubikSize: Float = 0.toFloat()
    internal var sizeX: Float = 0.toFloat()
    internal var sizeY: Float = 0.toFloat()

    internal var choosePaint = Paint()

    private var mDrawable: Drawable? = null
    private var plaerOne: Drawable? = null
    private var plaerTwo: Drawable? = null
    private var plaerThree: Drawable? = null
    private var plaerFour: Drawable? = null
    private var plaerFive: Drawable? = null


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
        mDrawable = ContextCompat.getDrawable(context, R.drawable.without_head_champ)
    }


    override fun onSizeChanged(width: Int, height: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(width, height, oldw, oldh)
        sizeX = getWidth().toFloat()
        sizeY = getHeight().toFloat()
        kubikSize = getWidth().toFloat() / 10

        choosePaint.setColor(Color.rgb(195, 167, 153))
        choosePaint.setStyle(Paint.Style.STROKE)
        choosePaint.setStrokeWidth(35f)

    }

    override fun onDraw(canvas: Canvas) {

        mDrawable?.setBounds(0, 0, sizeX.toInt(), sizeY.toInt())
        mDrawable?.draw(canvas)

        plaerOne?.setBounds((7*sizeX/100).toInt(),(12*sizeY/100).toInt(), (17*sizeX/100).toInt(), (32*sizeY/100).toInt())
        plaerOne?.draw(canvas)

        plaerTwo?.setBounds((29*sizeX/100).toInt(),(15*sizeY/100).toInt(), (39*sizeX/100).toInt(), (35*sizeY/100).toInt())
        plaerTwo?.draw(canvas)

        plaerThree?.setBounds((47*sizeX/100).toInt(),(16*sizeY/100).toInt(), (57*sizeX/100).toInt(), (36*sizeY/100).toInt())
        plaerThree?.draw(canvas)


        plaerFour?.setBounds((63*sizeX/100).toInt(),(15*sizeY/100).toInt(), (73*sizeX/100).toInt(), (36*sizeY/100).toInt())
        plaerFour?.draw(canvas)



        plaerFive?.setBounds((83*sizeX/100).toInt(),(18*sizeY/100).toInt(), (93*sizeX/100).toInt(), (40*sizeY/100).toInt())
        plaerFive?.draw(canvas)


    }

    fun setPlayer(player1:Drawable,player2:Drawable,player3:Drawable,player4:Drawable,player5:Drawable){
        plaerOne= player1
        plaerTwo=player2
        plaerThree=player3
        plaerFour=player4
        plaerFive=player5
        invalidate()
    }





}
