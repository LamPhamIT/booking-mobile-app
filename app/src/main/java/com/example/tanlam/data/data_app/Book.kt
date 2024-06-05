package com.example.tanlam.data.data_app

import kotlinx.serialization.Serializable

data class Book(
    val username: String,
    val location: String,
    val destination : String,
    val date: String,
    val nameTruck: String,
    val price: String,
    val typeRoom: String,
    val km: String
)
