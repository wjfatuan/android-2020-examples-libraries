package com.example.libraries2.sqlite

class Country(
    val code: String,
    var name: String,
    var continent: String
) {
    override fun toString(): String {
        return "{code: $code, name: $name, continent: $continent}"
    }
}