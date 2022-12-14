package com.ubaya.coffeeshoptycoon

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_result.*
import java.text.NumberFormat
import java.util.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        var sharedFile = "com.ubaya.utsnmp"
        var shared: SharedPreferences = getSharedPreferences(sharedFile,
            Context.MODE_PRIVATE )

        var day = shared.getInt("DAY",0)
        var balance = shared.getInt("BALANCE",0)

        var profit = 0

        val cupCoffee = Global.cupsTotal
        val priceCoffee = Global.sellPrice
        val revenues = intent.getIntExtra(SimulationActivity.REVENUES, 0)

        val costTotal = Global.costTotal

        profit = revenues - costTotal

        lblDayResult.setText("Day $day Result")
        lblCupCoffeeResult.setText("$cupCoffee cup of coffee")
        lblPriceCoffeeResult.setText("@IDR " + NumberFormat.getIntegerInstance(Locale.GERMAN).format(priceCoffee))
        lblRevenues.setText("IDR " + NumberFormat.getIntegerInstance(Locale.GERMAN).format(revenues))

        lblTotalCost.setText("IDR " + NumberFormat.getIntegerInstance(Locale.GERMAN).format(costTotal))

        lblProfit.setText("IDR " + NumberFormat.getIntegerInstance(Locale.GERMAN).format(profit))

        if(profit > 0){
            lblProfit.setTextColor(Color.parseColor("#4CAF50"))
        }else if(profit < 0){
            lblProfit.setTextColor(Color.RED)
        }

        balance += (revenues - costTotal)

        if(balance > 100000){
            lblLose.isVisible = false
            lblLoseDesc.isVisible = false
            imgLose.isVisible = false

            btnStartNew.setOnClickListener(){
                day += 1
                var editor:SharedPreferences.Editor = shared.edit()
                editor.putInt("BALANCE", balance)
                editor.putInt("DAY", day)
                editor.apply()

                val intent = Intent(this, PreparationActivity::class.java)
                startActivity(intent)
                finish()
            }
        }else{
            lblLose.isVisible = true
            lblLoseDesc.isVisible = true
            imgLose.isVisible = true

            btnStartNew.setText("Start New Game")

            btnStartNew.setOnClickListener(){
                var editor:SharedPreferences.Editor = shared.edit()
                editor.clear()
                editor.apply()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }



    }
}