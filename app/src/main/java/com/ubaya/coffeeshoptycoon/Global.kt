package com.ubaya.coffeeshoptycoon

object Global {
    var weather = arrayOf("Sunny", "Rainy", "Thunderstorm")

    val location:Array<Location> = arrayOf(
        Location(0, "University", 100000),
        Location(1, "Business District", 350000),
        Location(2, "Beach", 500000)
    )

    var sellPrice = 0
    var costCoffee = 0
    var cupsTotal = 0
    var costTotal = 0
}