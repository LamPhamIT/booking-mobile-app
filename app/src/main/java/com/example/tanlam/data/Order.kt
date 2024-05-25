package com.example.tanlam.data

data class Order(
    val id: Int = 0,
    val account: String = "",
    val struckName: String = "",
    val location: String = "37 Yersin St., Khue My, Ngu Hanh Son, Da Nang",
    val destination: String = "12/6 So tra, Da Nang",
    val km: Double = 0.0,
    val price: Double = 0.0,
    val payment: String = "",
    val typeOfPlace: String = "",
    val userInformation: UserInformation = UserInformation()
)
