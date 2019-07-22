package com.example.notificationservice

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

//data class Notification(var email: String, var message: String)
data class Notification @JsonCreator constructor(
        @JsonProperty("email") var email: String,
        @JsonProperty("message") var message: String
)