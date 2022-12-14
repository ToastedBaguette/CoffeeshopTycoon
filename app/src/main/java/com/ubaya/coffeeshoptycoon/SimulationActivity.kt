package com.ubaya.coffeeshoptycoon

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_preperation.*
import kotlinx.android.synthetic.main.activity_simulation.*
import java.text.NumberFormat
import java.util.*
import kotlin.math.ceil
import kotlin.math.floor

class SimulationActivity : AppCompatActivity() {
    companion object{
        val REVENUES = "REVENUES"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simulation)
        var sharedFile = "com.ubaya.utsnmp"
        var shared: SharedPreferences = getSharedPreferences(sharedFile,
            Context.MODE_PRIVATE )

        val day = shared.getInt("DAY",0)
        var numCup = Global.cupsTotal
        var sellPrice = Global.sellPrice
        var weather = Global.weather[0]
        var location = intent.getStringExtra(PreparationActivity.LOCATIONNAME)
        var revenues = 0

        var marginPrice = Global.costCoffee + (Global.costCoffee*0.7)

        lblDaySim.setText("DAY $day")
        lblWeatherSim.setText(weather)

        var outcomes : Array<String> = arrayOf()


        //Outcomes Algorithm

        var currentCup = numCup
        var randNum = 0
        for (i in 7..18){
            if (location == "University"){
                randNum = (0..15).random()
            }else if (location == "Business District"){
                randNum = (0..30).random()
            }else if (location == "Beach"){
                randNum = (0..60).random()
            }

            if (currentCup < randNum){
                randNum = (0..currentCup).random()
            }

            if(weather == "Rainy"){
                randNum = ceil(randNum*0.6).toInt()
            }else if(weather == "Thunderstorm"){
                randNum = ceil(randNum*0.4).toInt()
            }

            if (sellPrice > marginPrice){
                val outProb = marginPrice/sellPrice
                randNum = (randNum*outProb).toInt()
            }

            var itemDesc = ""

            //Set Timer
            if (i < 10){
                itemDesc += "0$i.00 - "
            }else{
                itemDesc += "$i.00 - "
            }

            //Set Outcomes
            if (currentCup <= 0){
                itemDesc += "Out of Stock"
            }else if (randNum == 0){
                itemDesc += "No customer"
            }else{
                itemDesc += "$randNum customers"
                //Revenues calculation
                revenues += (randNum*sellPrice)
                //set ranNum
                currentCup -= randNum
            }

            outcomes += itemDesc
        }


        //RecyclerView initialization
        val lm: LinearLayoutManager = LinearLayoutManager(this)
        recycleOutcomes.layoutManager = lm
        recycleOutcomes.setHasFixedSize(true)
        recycleOutcomes.adapter = SimulationAdapter(this, outcomes)

        btnResult.setOnClickListener(){
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("REVENUES", revenues)
            startActivity(intent)
            finish()
        }
    }

}