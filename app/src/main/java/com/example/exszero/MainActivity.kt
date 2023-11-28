package com.example.exszero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.example.exszero.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var activePlayer = (0..1).random()
    private var isGameActive: Boolean = true
    private var states = arrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1)

    private var scoreX = 0
    private var scoreO = 0
    private var winningPositions = arrayOf(
        arrayOf(0, 1, 2),
        arrayOf(3, 4, 5),
        arrayOf(6, 7, 8),

        arrayOf(0, 3, 6),
        arrayOf(1, 4, 7),
        arrayOf(2, 5, 8),


        arrayOf(0, 4, 8),
        arrayOf(2, 4, 6)
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (activePlayer == 0) {
            binding.txtPlayerTwo.alpha = 0.6f
            binding.txtPlayerTwoSymbol.alpha = 0.6f
        } else {
            binding.txtPlayerOne.alpha = 0.6f
            binding.txtPlayerOneSymbol.alpha = 0.6f
        }

    }

    fun dropIn(view: View) {
        if (isGameActive) {
            val clickedView = view as ImageView
            val tappedState = clickedView.tag.toString().toInt()


            if (states[tappedState] == -1) {
                states[tappedState] = activePlayer

                if (activePlayer == 0) {
                    clickedView.setImageResource(R.drawable.ekso)
                    activePlayer = 1
                    binding.txtPlayerOne.alpha = 0.6f
                    binding.txtPlayerTwoSymbol.alpha = 0.6f

                    binding.txtPlayerTwo.alpha = 1.0f
                    binding.txtPlayerTwoSymbol.alpha = 1.0f
                } else {
                    clickedView.setImageResource(R.drawable.nolli)
                    activePlayer = 0

                    binding.txtPlayerTwo.alpha = 0.6f
                    binding.txtPlayerTwoSymbol.alpha = 0.6f

                    binding.txtPlayerOne.alpha = 1.0f
                    binding.txtPlayerOneSymbol.alpha = 1.0f
                }
                for (winningPosition in winningPositions) {
                    if (states[winningPosition[0]] == states[winningPosition[1]]
                        && states[winningPosition[1]] == states[winningPosition[2]] &&
                        states[winningPosition[0]] != -1
                    ) {
                        isGameActive = false
                        var winner: String
                        if (states[winningPosition[0]] == 1) {
                            winner = "Blue"
                            scoreO++
                        } else {
                            winner = "Red"
                            scoreX++
                        }
                        binding.gameOverLayout.visibility = View.VISIBLE
                        binding.txtWinnerInfo.text = winner
                        binding.txtWinner.text = "WINNER"
                        binding.txtStats.text = "$scoreX:$scoreO"
                    } else {
                        var isGameOver = true
                        for (state in states) {
                            if (state == -1)
                                isGameOver = false
                        }
                        if (isGameOver) {
                            binding.txtWinner.text = "Ooops..."
                            binding.txtWinnerInfo.text = "It is a draw!"
                        }
                    }
                }
            }
        }
    }

    fun restartGame(view: View) {
        isGameActive = true
        binding.gameOverLayout.visibility = View.INVISIBLE
        activePlayer = (0..1).random()
        if (activePlayer == 0) {
            binding.txtPlayerTwo.alpha = 0.6f
            binding.txtPlayerTwoSymbol.alpha = 0.6f

            binding.txtPlayerOne.alpha = 1.0f
            binding.txtPlayerOneSymbol.alpha = 1.0f
        } else {
            binding.txtPlayerOne.alpha = 0.6f
            binding.txtPlayerOneSymbol.alpha = 0.6f
            binding.txtPlayerTwo.alpha = 1.0f
            binding.txtPlayerTwoSymbol.alpha = 1.0f
        }
        for (i in states.indices) {
            states[i] = -1
        }
        for (i in 0 until binding.gridLayout.childCount) {
            (binding.gridLayout.getChildAt(i) as ImageView).setImageResource(0)
        }
    }
}