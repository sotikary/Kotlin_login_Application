package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token") val token: String?)

