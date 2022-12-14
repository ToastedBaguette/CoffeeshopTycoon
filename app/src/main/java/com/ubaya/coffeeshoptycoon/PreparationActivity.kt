package com.ubaya.coffeeshoptycoon

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_preperation.*
import java.text.NumberFormat
import java.util.*

class PreparationActivity : AppCompatActivity() {
    companion object{
        var LOCATIONNAME = "LOCATIONNAME"
    }

    var numCoffee = 0
    var numMilk = 0
    var numWater = 0
    var numCup = 0

    private val coffeePrice = 500
    private val milkPrice = 1000
    private val waterPrice = 200
    var costCoffee = 0
    var sellPrice = 0
    var costProduct = 0

    var selectedLocation = Location()
    var locIndex= 0
    var locFee = 0

    var costTotal = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        var sharedFile = "com.ubaya.utsnmp"
        var shared: SharedPreferences = getSharedPreferences(sharedFile,
            Context.MODE_PRIVATE )

        val name = shared.getString("PLAYERNAME","")
        var balance = shared.getInt("BALANCE",0)
        var day = shared.getInt("DAY",0)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preperation)
        Global.weather.shuffle()

        val weather = Global.weather[0]

        lblName.text = "Welcome, $name"
        lblBalance.text = "Balance: IDR " + NumberFormat.getIntegerInstance(Locale.GERMAN).format(balance)
        lblDay.text = "Day $day"
        lblWeather.text = weather

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, Global.location)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerLocation.adapter = adapter

        //Saved recipes template
        if (shared.contains("NUMCOFFEE") && shared.contains("NUMMILK") && shared.contains("NUMWATER")){
            numCoffee = shared.getInt("NUMCOFFEE",0)
            numMilk = shared.getInt("NUMMILK",0)
            numWater = shared.getInt("NUMWATER",0)
            txtInputCoffee.setText(numCoffee.toString())
            txtInputMilk.setText(numMilk.toString())
            txtInputWater.setText(numWater.toString())

            numCup = shared.getInt("NUMCUP",0)
            txtInputSell.setText(numCup.toString())

            costCoffee = shared.getInt("COSTCOFFEE",0)
            lblTotalPrice.text = "IDR " +  NumberFormat.getIntegerInstance(Locale.GERMAN).format(costCoffee)
            lblTodayPrice.text = "@IDR " +  NumberFormat.getIntegerInstance(Locale.GERMAN).format(costCoffee)

            costProduct = shared.getInt("COSPRODUCT",0)
            lblTodayTotalPrice.text = "IDR " +  NumberFormat.getIntegerInstance(Locale.GERMAN).format(costProduct)

            sellPrice = shared.getInt("SELLPRICE",0)
            txtSellPrice.setText(sellPrice.toString())

            locIndex = shared.getInt("LOCINDEX",0)
            spinnerLocation.setSelection(locIndex)
            selectedLocation = spinnerLocation.selectedItem as Location
            locFee = selectedLocation.fee

            lblLocation.text = selectedLocation.name
            lblLocationFee.text = "IDR " + NumberFormat.getIntegerInstance(Locale.GERMAN).format(locFee)


            costTotal = shared.getInt("COSTTOTAL ",0)
            lblTodayTotalCost.text = "IDR " + NumberFormat.getIntegerInstance(Locale.GERMAN).format(costTotal)
            btnStartDay.isEnabled = balance>costTotal

        }

        txtInputCoffee.setText(numCoffee.toString())
        txtInputMilk.setText(numMilk.toString())
        txtInputWater.setText(numWater.toString())

        btnDownCoffee.setOnClickListener {
            if(numCoffee > 0){
                numCoffee -= 1
                txtInputCoffee.setText(numCoffee.toString())
            }
        }

        btnUpCoffee.setOnClickListener {
            if(numCoffee >= 0){
                numCoffee += 1
                txtInputCoffee.setText(numCoffee.toString())
            }
        }

        btnDownMilk.setOnClickListener {
            if(numMilk > 0){
                numMilk -= 1
                txtInputMilk.setText(numMilk.toString())
            }
        }

        btnUpMilk.setOnClickListener {
            if(numMilk >= 0){
                numMilk += 1
                txtInputMilk.setText(numMilk.toString())

            }
        }

        btnDownWater.setOnClickListener {
            if(numWater > 0){
                numWater -= 1
                txtInputWater.setText(numWater.toString())

            }
        }

        btnUpWater.setOnClickListener {
            if(numWater >= 0){
                numWater += 1
                txtInputWater.setText(numWater.toString())

            }
        }

        txtInputCoffee.addTextChangedListener {
            setInputText()
        }

        txtInputMilk.addTextChangedListener {
            setInputText()
        }

        txtInputWater.addTextChangedListener {
            setInputText()
        }

        txtInputSell.addTextChangedListener {
            if(txtInputSell.text.isNotEmpty()){
                numCup = txtInputSell.text.toString().toInt()
                lblCupCoffee.text = txtInputSell.text.toString() + "Cup of Coffee"

                costProduct = costCoffee*numCup
                lblTodayTotalPrice.text = "IDR " +  NumberFormat.getIntegerInstance(Locale.GERMAN).format(costProduct)
            }
        }

        txtSellPrice.addTextChangedListener {
            if(txtSellPrice.text.isNotEmpty()){
                sellPrice = txtSellPrice.text.toString().toInt()
            }
        }

        spinnerLocation.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View, p2: Int, p3: Long) {
                selectedLocation = spinnerLocation.selectedItem as Location
                locIndex = selectedLocation.index
                locFee = selectedLocation.fee
                lblLocation.text = selectedLocation.name
                lblLocationFee.text = "IDR " + NumberFormat.getIntegerInstance(Locale.GERMAN).format(locFee)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        lblLocationFee.addTextChangedListener {
            costTotal = costProduct + locFee
            lblTodayTotalCost.text = "IDR " + NumberFormat.getIntegerInstance(Locale.GERMAN).format(costTotal)
            btnStartDay.isEnabled = balance>costTotal
        }

        lblTodayTotalPrice.addTextChangedListener {
            costTotal = costProduct + locFee
            lblTodayTotalCost.text = "IDR " + NumberFormat.getIntegerInstance(Locale.GERMAN).format(costTotal)
            btnStartDay.isEnabled = balance>costTotal
        }

        btnStartDay.setOnClickListener {
            if( numCoffee <= 0 || numWater <= 0 || numMilk <= 0){
                Toast.makeText(this, "Number of components of coffee, milk, and water is at least 1", Toast.LENGTH_SHORT).show()
            }else if(numCup <= 0){
                Toast.makeText(this, "Minimum number of cups of coffee to be sold is 1", Toast.LENGTH_SHORT).show()
            }else if(sellPrice <= 0){
                Toast.makeText(this, "Selling price of a cup of coffee is at least IDR 1", Toast.LENGTH_SHORT).show()
            }else{
                Global.cupsTotal = numCup
                Global.sellPrice = sellPrice
                Global.costCoffee = costCoffee
                Global.costTotal = costTotal

                val intent = Intent(this, SimulationActivity::class.java)
                intent.putExtra(LOCATIONNAME, selectedLocation.name)
                startActivity(intent)
                finish()
            }
        }

        btnSaveRecipes.setOnClickListener {
            if( numCoffee <= 0 || numWater <= 0 || numMilk <= 0){
                Toast.makeText(this, "Number of components of coffee, milk, and water is at least 1", Toast.LENGTH_SHORT).show()
            }else if(numCup <= 0){
                Toast.makeText(this, "Minimum number of cups of coffee to be sold is 1", Toast.LENGTH_SHORT).show()
            }else if(sellPrice <= 0){
                Toast.makeText(this, "Selling price of a cup of coffee is at least IDR 1", Toast.LENGTH_SHORT).show()
            }else{
                var editor:SharedPreferences.Editor = shared.edit()
                editor.putInt("NUMCOFFEE", numCoffee)
                editor.putInt("NUMMILK", numMilk)
                editor.putInt("NUMWATER", numWater)
                editor.putInt("NUMCUP", numCup)
                editor.putInt("COSTCOFFEE", costCoffee)
                editor.putInt("COSPRODUCT", costProduct)
                editor.putInt("SELLPRICE", sellPrice)
                editor.putInt("LOCINDEX", locIndex)
                editor.putInt("COSTTOTAL", costTotal)
                editor.apply()
                Toast.makeText(this, "Successfully save recipes!", Toast.LENGTH_SHORT).show()
            }
        }


    }

    fun setInputText(){
        costCoffee = numCoffee*coffeePrice + numMilk*milkPrice + numWater*waterPrice
        lblTotalPrice.text = "IDR " +  NumberFormat.getIntegerInstance(Locale.GERMAN).format(costCoffee)
        lblTodayPrice.text = "@IDR " +  NumberFormat.getIntegerInstance(Locale.GERMAN).format(costCoffee)

        costProduct = costCoffee*numCup
        lblTodayTotalPrice.text = "IDR " +  NumberFormat.getIntegerInstance(Locale.GERMAN).format(costProduct)
    }


}