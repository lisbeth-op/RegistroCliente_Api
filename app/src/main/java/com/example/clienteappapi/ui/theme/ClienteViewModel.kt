package com.example.clienteappapi.ui.theme

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clienteappapi.Data.Remote.ClienteDto
import com.example.clienteappapi.Data.Repository.ClienteRepository
import com.example.customerapi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class  ClienteListState(
    val isLoading:Boolean=false,
    val cliente:List<ClienteDto> = emptyList(),
    val error: String =""
)
@HiltViewModel
class ClienteApiViewModel @Inject constructor(
    private val clienteRepository: ClienteRepository
) : ViewModel() {
    var nombre by mutableStateOf("")
    var rnc by mutableStateOf("")
    var direccion by mutableStateOf("")
    var limiteCredito by mutableStateOf("")

    var nombreError by mutableStateOf(true)
    var rncError by mutableStateOf(true)
    var direccionError by mutableStateOf(true)
    var limiteCreditoError by mutableStateOf(true)


    private val _isMessageShown = MutableSharedFlow<Boolean>()
    fun setMessageShown() {
        viewModelScope.launch {
            _isMessageShown.emit(true)
        }
    }
    fun validarCampos(): Boolean {
        nombreError = nombre.isBlank() || nombre.length < 3
        rncError = rnc.isBlank()
        direccionError = direccion.isBlank()
        limiteCreditoError = limiteCredito.isBlank()
        return !(nombreError || rncError  || direccionError|| limiteCreditoError)
    }
    private fun limpiar() {
        nombre=""
       rnc=""
        direccion=""
        limiteCredito=""
    }

//    init {
//        clienteRepository.getCliente().onEach { result ->
//            when (result) {
//                is Resource.Loading -> {
//                    _state.value = ClienteListState(isLoading = true)
//                }
//
//                is Resource.Success -> {
//                    _state.value = ClienteListState(cliente = result.data ?: emptyList())
//                }
//
//                is Resource.Error -> {
//                    _state.value = ClienteListState(error = result.message ?: "Error desconocido")
//                }
//            }
//        }.launchIn(viewModelScope)
//    }
    fun saveCliente() {
        if (validarCampos()) {
            viewModelScope.launch {
                val cliente= ClienteDto(
                    nombre = nombre,
                    rnc=rnc,
                    direcion=direccion,
                    //limiteCredito = limiteCredito


                )

                limpiar()
            }
        }
    }


}