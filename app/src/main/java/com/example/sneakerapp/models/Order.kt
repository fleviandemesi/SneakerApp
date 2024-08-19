package com.example.sneakerapp.models

class Order(
    val order_id: String = "",
    val shoe_id: String = "",
    val appointment_date: String = "",
    val appointment_time: String = "",
    val status: String = "",
    val invoice_no: String = "",
    //above names must be same as the API responses
)