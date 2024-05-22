package com.example.tanlam.common

fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$"
    return email.matches(emailRegex.toRegex())
}

fun isEmptyString(input: String): Boolean {
    val regex = Regex("""^\s*$""")
    return regex.matches(input)
}

fun isValidDayOfBirth(dayOfBirth: String): Boolean {
    val regex = Regex("""^\d{2}-\d{2}-\d{4}$""")
    return regex.matches(dayOfBirth)
}