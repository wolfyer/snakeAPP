package com.wolfyer.snake

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wolfyer.snake.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val viewModel = ViewModelProvider(this)[SnakeViewModel::class.java]
        viewModel.body.observe(this, Observer {
            game_view.snakeBody = it
            game_view.invalidate()

        })
        viewModel.score.observe(this, Observer {
            score.text = it.toString()

        })
        viewModel.gameState.observe(this, Observer {gs ->
            if ( gs== GameState.GAMEOVER){
                AlertDialog.Builder(this)
                    .setTitle("Game")
                    .setMessage("Game Over")
                    .setPositiveButton("OK",null)
                    .show()

            }
        })
        viewModel.apple.observe(this, Observer {
            game_view.apple = it
            game_view.invalidate()
        })
        viewModel.start()
        iv_up.setOnClickListener { viewModel.move(Direction.TOP) }
        iv_down.setOnClickListener { viewModel.move(Direction.DOWN) }
        iv_left.setOnClickListener { viewModel.move(Direction.LEFT) }
        iv_right.setOnClickListener { viewModel.move(Direction.RIGHT) }

        binding.fab.setOnClickListener {
            viewModel.reset()
        }
    }


}