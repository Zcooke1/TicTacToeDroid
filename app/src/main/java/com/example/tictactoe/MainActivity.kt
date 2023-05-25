package com.example.tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tictactoe.ui.theme.TicTacToeTheme


class MainActivity : ComponentActivity() {

    private var playerXTurn = true //set true if it is player X's turn, false if player O's turn
    private lateinit var buttons: Array<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBoard()
        setupNewGameButton()
    }

    private fun setupBoard() {
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)
        val button5 = findViewById<Button>(R.id.button5)
        val button6 = findViewById<Button>(R.id.button6)
        val button7 = findViewById<Button>(R.id.button7)
        val button8 = findViewById<Button>(R.id.button8)
        val button9 = findViewById<Button>(R.id.button9)
        buttons = arrayOf(button1, button2, button3, button4, button5, button6, button7, button8, button9)

        buttons.forEach { button ->
            button.setOnClickListener {
                playGame(button)
            }
        }
    }

    private fun setupNewGameButton() {
        val newGameButton = findViewById<Button>(R.id.newgamebtn)
        newGameButton.setOnClickListener {
            resetGame()
        }
    }

    private fun playGame(button: Button) {
        if (button.text.isBlank()) {
            button.text = if (playerXTurn) "X" else "O"
            playerXTurn = !playerXTurn
            updatePlayerTurnTextView()

            if (checkForWin()){
                endGame()

            }else if(isBoardFull()){
                drawGame()
            }
        }
    }

    private fun resetGame() {
        playerXTurn = true
        buttons.forEach { button ->
            button.text = ""
            button.isEnabled = true
        }
        updatePlayerTurnTextView()
    }

    private fun updatePlayerTurnTextView() {
        val playerTurnTextView = findViewById<TextView>(R.id.player_text)
        playerTurnTextView.text = if (playerXTurn) "Player X's Turn" else "Player O's Turn"
    }

    private fun checkForWin(): Boolean {
        val board = Array(3) { arrayOfNulls<String?>(3) }
        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j] = buttons[(i * 3) + j].text?.toString()
            }
        }

        // Check rows
        for (i in 0..2) {
            if (board[i][0] == board[i][1] && board[i][0] == board[i][2] && !board[i][0].isNullOrBlank()) {
                return true
            }
        }

        // Check columns
        for (i in 0..2) {
            if (board[0][i] == board[1][i] && board[0][i] == board[2][i] && !board[0][i].isNullOrBlank()) {
                return true
            }
        }

        // Check diagonals
        if (board[0][0] == board[1][1] && board[0][0] == board[2][2] && !board[0][0].isNullOrBlank()) {
            return true
        }
        if (board[0][2] == board[1][1] && board[0][2] == board[2][0] && !board[0][2].isNullOrBlank()) {
            return true
        }

        return false
    }

    private fun isBoardFull(): Boolean {
        return buttons.all { button ->
            button.text.isNotBlank()
        }
    }

    private fun endGame() {
        val playerTurnTextView = findViewById<TextView>(R.id.player_text)
        playerTurnTextView.text = if (playerXTurn) "Player O Wins!" else "Player X Wins!"
        disableButtons()
    }

    private fun drawGame() {
        val playerTurnTextView = findViewById<TextView>(R.id.player_text)
        playerTurnTextView.text = "It's a Draw!"
        disableButtons()
    }

    private fun disableButtons() {
        buttons.forEach { button ->
            button.isEnabled = false
        }
    }
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TicTacToeTheme {
        Greeting("Android")
    }
}