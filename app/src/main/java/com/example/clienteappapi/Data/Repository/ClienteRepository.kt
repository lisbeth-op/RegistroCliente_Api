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
            emit(Resource.Loading())

            val cliente = api.getClientes()

            emit(Resource.Success(cliente))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {

            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }

    fun getClienteId(id:Int): Flow<Resource <ClienteDto>> = flow {
        try {
            emit(Resource.Loading())

            val clienteId = api.getClientesId(id)
            emit(Resource.Success(clienteId))
        } catch (e: HttpException) {

            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {

            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }

    suspend fun PstCliente(cliente: ClienteDto):ClienteDto= api.postClientes(cliente)
    suspend fun deleteCliente(id:Int, cliente: ClienteDto)=api.deleteCliente(id,cliente)
}


