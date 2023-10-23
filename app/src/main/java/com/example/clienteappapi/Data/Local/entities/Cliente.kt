package com.example.clienteappapi.Data.Local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="Clientes")
data class Cliente (
    @PrimaryKey
    val clienteId: Int?=null,
            var nombre:String="",
             var rnc:String="",
             var direcion: String="",
          var limiteCredito:Int?=null

)