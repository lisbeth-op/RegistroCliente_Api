package com.example.clienteappapi.Data.Repository

import com.example.clienteappapi.Data.Remote.ClienteApi
import com.example.clienteappapi.Data.Remote.ClienteDto
import com.example.customerapi.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ClienteRepository @Inject constructor(private val api: ClienteApi) {
    fun getCliente(): Flow<Resource<List<ClienteDto>>> = flow {
        try {
            emit(Resource.Loading()) //indicar que estamos cargando

            val cliente = api.getClientes() //descarga las monedas de internet, se supone quedemora algo

            emit(Resource.Success(cliente)) //indicar que se cargo correctamente.
        } catch (e: HttpException) {
            //error general HTTP
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            //debe verificar tu conexion a internet
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }

    fun getClienteId(id:Int): Flow<Resource <ClienteDto>> = flow {
        try {
            emit(Resource.Loading()) //indicar que estamos cargando

            val clienteId = api.getClientesId(id) //descarga las monedas de internet, se supone quedemora algo

            emit(Resource.Success(clienteId)) //indicar que se cargo correctamente.
        } catch (e: HttpException) {
            //error general HTTP
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            //debe verificar tu conexion a internet
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }

    suspend fun PstCliente(cliente: ClienteDto) = api.postClientes(cliente)
    suspend fun deleteCliente(id:Int, cliente: ClienteDto)=api.deleteCliente(id,cliente)
}


