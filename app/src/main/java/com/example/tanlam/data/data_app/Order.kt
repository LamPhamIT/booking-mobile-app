package com.example.tanlam.data.data_app

import com.example.tanlam.data.UserInformation

data class Order(
    val id: Int = 0,
    val date: String = "",
    val account: String = "",
    val struckName: String = "",
    val location: String = "",
    val destination: String = "",
    val km: Double = 0.0,
    val price: Double = 0.0,
    val payment: String = "",
    val typeOfPlace: String = "",
    val userInformation: UserInformation = UserInformation(),
    var isConfirm:Boolean ?= null,
)
