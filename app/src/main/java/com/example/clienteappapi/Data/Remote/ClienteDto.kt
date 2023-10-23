package com.example.clienteappapi.Data.Remote

import androidx.room.Entity


data class ClienteDto (
    val clienteId: Int?=null,
    var nombre:String="",
    var rnc:String="",
    var direcion: String="",
    var limiteCredito:Int?=null

)