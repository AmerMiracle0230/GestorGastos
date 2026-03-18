package com.example.gestorgastos.screens.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestorgastos.data.entity.Gasto
import com.example.gestorgastos.data.repository.GastoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val repository: GastoRepository
) : ViewModel() {

    fun guardarGasto(
        descripcion: String,
        cantidad: Double,
        categoria: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val gasto = Gasto(
                cantidad = cantidad,
                descripcion = descripcion,
                categoria = categoria
            )
            repository.insertarGasto(gasto)
            onSuccess()
        }
    }
}