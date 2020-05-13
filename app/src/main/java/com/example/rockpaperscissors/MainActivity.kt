package com.example.rockpaperscissors

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        var comp = false

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setting.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                player2.visibility = View.INVISIBLE
                comp = true
            } else {
                player2.visibility = View.VISIBLE
                comp = false
            }
        })
        start.setOnClickListener {
            if(player1.text.toString().isEmpty() || (player2.text.toString().isEmpty() && !comp)){
                Toast.makeText(applicationContext,"Enter Name",Toast.LENGTH_SHORT).show()
            }
            else if(rounds.text.toString().isEmpty()){
                Toast.makeText(applicationContext,"Enter Rounds",Toast.LENGTH_SHORT).show()
            }
            else{
                var roundNum = rounds.text.toString().toInt()
                var playerN1 = player1.text.toString()
                var playerN2 = player2.text.toString()
                val intent = Intent(this, Game::class.java)
                intent.putExtra("comp",comp)
                intent.putExtra("playerN1",playerN1)
                intent.putExtra("playerN2",playerN2)
                intent.putExtra("roundNum",roundNum)
                startActivity(intent)
            }
        }
    }
}
