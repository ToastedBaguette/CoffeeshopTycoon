package com.ubaya.coffeeshoptycoon

data class Location(val index:Int = 0, val name: String = "", val fee: Int = 0){
    override fun toString(): String {
        return name
    }
}
