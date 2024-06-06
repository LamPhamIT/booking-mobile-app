package com.example.tanlam.controller.collection


sealed class Collections(
    val name: String
) {
    object Account: Collections(name = "Accounts")
    object Struck: Collections(name = "Struck")
    object Order: Collections(name = "Orders")
}