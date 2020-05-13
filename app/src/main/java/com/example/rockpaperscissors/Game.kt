package com.example.rockpaperscissors

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_game.playerN
import kotlinx.android.synthetic.main.round_popup.*


class Game : AppCompatActivity() {
    var playerdone = false
    var player1op = 0
    var player2op = 0
    var player1Sco = 0
    var player2Sco = 0
    var playerN1 = ""
    var playerN2 = ""
    var roundNum = 0
    var cRound = 1
    var againstComp = true
    lateinit var screen: ConstraintLayout
    lateinit var roundPop:Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        home.visibility = View.INVISIBLE


        againstComp = intent.getBooleanExtra("comp",false)
        playerN1 = intent.getStringExtra("playerN1")
        if(againstComp){
            playerN2 = "Comp"
        }
        else{
            playerN2 = intent.getStringExtra("playerN2")
        }
        roundNum = intent.getIntExtra("roundNum",0)
        //
        roundPop = Dialog(this)
        roundPop.setContentView(R.layout.round_popup)
        roundPop.player1.text = playerN1
        roundPop.player2.text = playerN2
        //
        screen = findViewById(com.example.rockpaperscissors.R.id.Screen)
        //
        name()
        rock.setOnClickListener{
            if(!playerdone){
                player1op = 1
                playerdone = true
            }
            else{
                player2op = 1
                playerdone = false
                disp()
            }
            name()
        }
        paper.setOnClickListener{
            if(!playerdone){
                player1op = 2
                playerdone = true
            }
            else{
                player2op = 2
                playerdone = false
                disp()
            }

            name()

        }
        scissors.setOnClickListener{
            if(!playerdone){
                player1op = 3
                playerdone = true
            }
            else{
                player2op = 3
                playerdone = false
                disp()

            }
            name()

        }

    }
    @SuppressLint("SetTextI18n")
    fun name(){
        if(cRound<=roundNum){
            round.text = "$cRound/$roundNum"
            if(!playerdone){
                playerN.text = "$playerN1's turn"
            }
            else{
                playerN.text = "$playerN2's turn"
                if(againstComp){
                    Handler().postDelayed(
                        {
                            player2op = arrayListOf<Int>(1, 2, 3).random()
                            playerdone = false
                            disp()
                            name()
                        },
                        1000 // value in milliseconds
                    )
                }
            }
        }
        else{
            gameOver()
        }
    }
    fun disp(){
        if(cRound <= roundNum){

            roundPop.player1C.setImageResource(playerchoice(player1op))
            roundPop.player2C.setImageResource(playerchoice(player2op))
            if(player1op-player2op == 1 || player1op-player2op == -2){
                roundPop.player.text = "$playerN1 won round $cRound"
                player1Sco += 1
            }
            else if (player1op-player2op == 0){
                roundPop.player.text = "Round $cRound draw!"

            }
            else{
                roundPop.player.text = "$playerN2 won round $cRound"
                player2Sco += 1
            }
            roundPop.player1S.text = player1Sco.toString()
            roundPop.player2S.text = player2Sco.toString()

            if(cRound == roundNum){
                gameOver()
            }
            else{
                roundPop.show()
            }
            cRound += 1

        }
    }

    fun playerchoice(ch:Int): Int {
        if(ch==1) {
            return R.drawable.rock
        }
        else if(ch ==2){
            return R.drawable.paper
        }
        else{
            return R.drawable.scissors
        }
    }

    @SuppressLint("SetTextI18n")
    fun gameOver(){
        home.visibility = View.VISIBLE
        home.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        if(player1Sco > player2Sco){
            playerN.text = "$playerN1 WON!"
            screen.setBackgroundColor(Color.rgb(0, 230, 118))

        }
        else if(player1Sco == player2Sco){
            playerN.text = "DRAW!"
        }
        else{
            playerN.text = "$playerN2 WON!"
            screen.setBackgroundColor(Color.rgb(255, 23, 68))
        }
    }


}
