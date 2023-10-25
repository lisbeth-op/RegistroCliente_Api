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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
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

    private val _state = MutableStateFlow(ClienteListState())
    val state=_state.asStateFlow()

    private val _isMessageShown = MutableSharedFlow<Boolean>()
    val isMessageShownFlow = _isMessageShown.asSharedFlow()
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

    init {
        clienteRepository.getCliente().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                   _state.update {
                       it.copy(
                           isLoading =true
                       )
                   }
                }

                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading =false,
                            cliente = result.data?: emptyList()
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading =false,
                            error = result.message?: "Error no reconocido"
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

     fun limpiar() {
        nombre=""
       rnc=""
         direccion=""
        limiteCredito=""
    }



    val clientes: StateFlow<Resource<List<ClienteDto>>> = clienteRepository.getCliente().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = Resource.Loading()
    )
    fun saveCliente() {
        if (validarCampos()) {
            viewModelScope.launch {
                val cliente= ClienteDto(
                    nombre = nombre,
                    rnc=rnc,
                    direcion=direccion,
                    limiteCredito = 0


                )

                limpiar()
            }
        }
    }


}