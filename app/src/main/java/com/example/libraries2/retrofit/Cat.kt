package com.example.libraries2.retrofit

class Cat (
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
){
    override fun toString(): String {
        return "url: $url"
    }
}