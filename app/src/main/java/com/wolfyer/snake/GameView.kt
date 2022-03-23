package com.wolfyer.snake

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint

class GameView(context:Context,attrs:AttributeSet): View(context, attrs) {
    var snakeBody: List<Positison>? = null
    var apple:Positison? = null
    var size = 0
    val gap = 3
    private val paint = Paint().apply { color = Color.Black }
    private val paintAapple = Paint().apply { color = Color.Red }

    //客製化的view可以畫出我想要的東西 為了要畫圖複寫ondraw
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.run {
            apple?.run{
                drawRect((x*size).toFloat()+gap,(y*size).toFloat()+gap,
                    ((x+1)*size).toFloat()-gap,((y+1)*size).toFloat()-gap,paintAapple.asFrameworkPaint()
                )
            }
            snakeBody?.forEach{
                drawRect(
                    (it.x*size).toFloat()+gap,(it.y*size).toFloat()+gap,
                    ((it.x+1)*size).toFloat()-gap,((it.y+1)*size).toFloat()-gap,paint.asFrameworkPaint()
                )
            }
        }
    }
    //想知道一格寬度高度多少才知道畫幾格
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        size = width/20
    }
}