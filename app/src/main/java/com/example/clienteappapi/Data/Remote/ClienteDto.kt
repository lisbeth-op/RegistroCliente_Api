package com.example.clienteappapi.Data.Remote


data class ClienteDto (
    val clienteId: Int?=null,
    var nombres:String="",
    var rnc:String="",
    var direccion: String="",
    var limiteCredito:Int=0

)