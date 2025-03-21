package com.example.barman

import java.sql.Time

data class Drink(
    var Id: Int,
    var name: String,
    var drinkDescription: String,
    var time: Int
)


fun getFakeDrinks() : List<Drink>{
    return listOf(
        Drink(1, "Pumpkin spice latte", "banany z malina", 20),
        Drink(2, "Koktajl z selera naciowego", "banany z malina", 20),
        Drink(3, "Syrop z mięty", "banany z malina", 20),
        Drink(4, "Ayran", "banany z malina", 20),
    )
}