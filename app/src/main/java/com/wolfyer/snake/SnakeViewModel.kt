package com.wolfyer.snake

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.concurrent.fixedRateTimer
import kotlin.random.Random

class SnakeViewModel:ViewModel() {

    val body = MutableLiveData<List<Positison>>()
    val apple = MutableLiveData<Positison>()
    val score = MutableLiveData<Int>()
    val gameState = MutableLiveData<GameState>()
    private  val snakeBody = mutableListOf<Positison>()
    private lateinit var applePos: Positison
    private var direction = Direction.LEFT
    private var direction_state =Direction.LEFT
    private var point :Int = 0
    fun start(){
        score.postValue(point)
        snakeBody.apply {
            add(Positison(10,10))
            add(Positison(11,10))
            add(Positison(12,10))
            add(Positison(13,10))
        }.also {//還要針對這個傢伙做改變
            body.value = it
        }
        generateApple()
        //一個timer的寫法
        fixedRateTimer("timer",true,500,500){
            val pos = snakeBody.first().copy().apply {
                if (direction_state == Direction.LEFT && direction ==Direction.RIGHT){ direction =Direction.LEFT}
                else if (direction_state == Direction.RIGHT && direction ==Direction.LEFT){ direction =Direction.RIGHT}
                else if (direction_state == Direction.TOP && direction ==Direction.DOWN){ direction =Direction.TOP}
                else if (direction_state == Direction.DOWN && direction ==Direction.TOP){ direction =Direction.DOWN}

                when(direction){
                    Direction.LEFT -> x--
                    Direction.RIGHT -> x++
                    Direction.TOP -> y--
                    Direction.DOWN -> y++
                }
                direction_state = direction
                if(snakeBody.contains(this) || x<0 || x>20 || y<0 ||y>20){
                    //撞到自己contains
                    cancel()//timer不會跑的方法
                    gameState.postValue(GameState.GAMEOVER)

                }
            }
            snakeBody.add(0,pos)
            //位置跟apple一樣就不用remove last，然後頻果被吃掉applePos要消失=產生下一個位置
            if (pos != applePos){
                snakeBody.removeLast()
            }else{
                point+=100
                score.postValue(point)
                generateApple()//產生下一個apple
            }
            body.postValue(snakeBody) //告知他
        }

    }
    fun generateApple(){
        //產生蘋果位置 applePos = Positison(Random.nextInt(20),Random.nextInt(20))
        //產生頻果位置但不要在身上
        val spots = mutableListOf<Positison>().apply {
            for (i in 0..19){
                for (j in 0..19){
                    add(Positison(i,j))
                }
            }
        }
        spots.removeAll(snakeBody)//排除body
        spots.shuffle()//打亂
        applePos =spots[0]//取一個亂數
        apple.postValue(applePos)
    }
    fun reset(){
        point = 0
        direction = Direction.LEFT
        direction_state =Direction.LEFT
        snakeBody.clear()
        start()
    }
    fun move(dir:Direction){
        direction = dir
    }
}
data class Positison(var x:Int,var y:Int)
enum class Direction{
    TOP,DOWN,LEFT,RIGHT
}
enum class GameState{
    ONGOING,GAMEOVER
}