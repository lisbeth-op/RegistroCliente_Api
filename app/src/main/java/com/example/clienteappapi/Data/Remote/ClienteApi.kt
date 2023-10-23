package com.example.clienteappapi.Data.Remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ClienteApi {
@GET ("/api/Clientes")
suspend fun getClientes():List<ClienteDto>
@GET("api/Clientes/{id}")
suspend fun getClientesId(@Path("clienteId") clienteId: Int):ClienteDto
@POST("/api/Clientes")
suspend fun postClientes(@Body cliente: ClienteDto):Response<ClienteDto>

@DELETE("api/Clientes/{id}")
suspend fun deleteCliente(@Path("clienteId") clienteId: Int,@Body clienteDto: ClienteDto):Response<Unit>
}