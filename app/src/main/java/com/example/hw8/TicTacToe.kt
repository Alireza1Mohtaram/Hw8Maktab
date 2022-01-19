package com.example.hw8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class TicTacToe : AppCompatActivity() {

    lateinit var btn00: Button
    lateinit var btn01: Button
    lateinit var btn02: Button

    lateinit var btn10: Button
    lateinit var btn11: Button
    lateinit var btn12: Button

    lateinit var btn20: Button
    lateinit var btn21: Button
    lateinit var btn22: Button

    lateinit var btnReset: Button
    lateinit var resultTv: TextView
    lateinit var resultTurnTv: TextView
    var arrayOfBtns = arrayListOf<Button>()
    var firstPlayer = getRandomPLayer()

    var boardMap = mutableMapOf<Int, Int>()

    companion object {
        var count = 8
    }

    private var matrix = arrayOf(
        intArrayOf(1, 2, 3), intArrayOf(4, 5, 6), intArrayOf(7, 8, 9),
        intArrayOf(1, 4, 7), intArrayOf(2, 5, 8), intArrayOf(3, 6, 9),
        intArrayOf(1, 5, 9), intArrayOf(3, 5, 7)

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tic_tac_toe)
        initViews()

        btn00.setOnClickListener {
            updateBoard(it as Button, 1)
        }
        btn01.setOnClickListener {
            updateBoard(it as Button, 2)
        }
        btn02.setOnClickListener {
            updateBoard(it as Button, 3)
        }
        btn10.setOnClickListener {
            updateBoard(it as Button, 4)
        }
        btn11.setOnClickListener {
            updateBoard(it as Button, 5)
        }
        btn12.setOnClickListener {
            updateBoard(it as Button, 6)
        }
        btn20.setOnClickListener {
            updateBoard(it as Button, 7)
        }
        btn21.setOnClickListener {
            updateBoard(it as Button, 8)
        }
        btn22.setOnClickListener {
            updateBoard(it as Button, 9)
        }
        btnReset.setOnClickListener {
            reset()
        }

    }
    //updating board after each button clicked
    private fun updateBoard(v: Button, index: Int) {
        var winner :Turn
        if (getPlayer(count, firstPlayer) == Turn.O) {
            v.text = "O"
            resultTurnTv.text = "X"
            boardMap[index] = 0

        } else {
            v.text = "X"
            resultTurnTv.text = "O"
            boardMap[index] = 1
        }
        count--
        println(count)

        v.isEnabled = false

        if (count <= 5 || count >= -1) {
            winner = getWinner()
            resultTv.text = getString(R.string.draw)
            if (winner != Turn.DRAW) {
                resultTv.text = "Player $winner Win"
                disableButtons()
            }
        }
    }

    // check win position
    private fun getWinner(): Turn {

        var sum: Int
        for (i in 0..7) {
            val s = matrix[i]
            sum = 0
            for (j in 0..2) {
                if (boardMap[s[j]] == null) boardMap[s[j]] = 10
                sum += boardMap[s[j]]!!

            }
            if (sum == 0) return Turn.O
            else if (sum == 3) return Turn.X
        }
        return Turn.DRAW
    }

    private fun initViews() {

        btn00 = findViewById(R.id.btn_0_0)
        btn01 = findViewById(R.id.btn_0_1)
        btn02 = findViewById(R.id.btn_0_2)

        btn10 = findViewById(R.id.btn_1_0)
        btn11 = findViewById(R.id.btn_1_1)
        btn12 = findViewById(R.id.btn_1_2)

        btn20 = findViewById(R.id.btn_2_0)
        btn21 = findViewById(R.id.btn_2_1)
        btn22 = findViewById(R.id.btn_2_2)

        btnReset = findViewById(R.id.btnReset)
        resultTv = findViewById(R.id.tvResult)
        resultTurnTv = findViewById(R.id.tvResultTurn)
        resultTurnTv.text = firstPlayer.toString()

        arrayOfBtns = arrayListOf(btn00, btn01, btn02, btn10, btn11, btn12, btn20, btn21, btn22)

    }
    private fun getRandomPLayer(): Turn {
        if ((0..10).random() % 2 == 0) return Turn.O
        return Turn.X
    }
    private fun getPlayer(counter: Int, firstPlayer: Turn): Turn {
        if (firstPlayer == Turn.O) {
            return if (counter % 2 == 0) Turn.O
            else Turn.X
        } else {
            return if (counter % 2 == 0) Turn.X
            else Turn.O
        }
    }
    private fun disableButtons() {
        arrayOfBtns.forEach() {
            it.isEnabled = false
        }
    }

    private fun enableButtons() {
        arrayOfBtns.forEach() {
            it.isEnabled = true
            it.text = ""
        }
    }

    private fun reset() {
        boardMap.clear()
        enableButtons()
        resultTv.text = getString(R.string.draw)
        count = 8
        firstPlayer = getRandomPLayer()
        resultTurnTv.text = firstPlayer.toString()
    }
}