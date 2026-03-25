package com.example.gestorgastos.screens.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestorgastos.data.datastore.Preferencias
import com.example.gestorgastos.data.entity.Categoria
import com.example.gestorgastos.data.repository.CategoriaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfigViewModel @Inject constructor(
    private val preferencias: Preferencias,
    private val categoriaRepository: CategoriaRepository
) : ViewModel() {

    val monedaActual: StateFlow<String> = preferencias.moneda
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = "€"
        )

    val temaActual: StateFlow<String> = preferencias.tema
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = "sistema"
        )

    val categoriasDestacadasIds: StateFlow<Set<Int>> = preferencias.categoriasDestacadas
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptySet()
        )

    val todasCategorias: StateFlow<List<Categoria>> = categoriaRepository.obtenerTodas()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    fun guardarMoneda(moneda: String) {
        viewModelScope.launch {
            preferencias.guardarMoneda(moneda)
        }
    }

    fun guardarTema(tema: String) {
        viewModelScope.launch {
            preferencias.guardarTema(tema)
        }
    }

    fun guardarCategoriasDestacadas(ids: Set<Int>) {
        viewModelScope.launch {
            preferencias.guardarCategoriasDestacadas(ids)
        }
    }

    fun actualizarObjetivo(categoriaId: Int, nuevoObjetivo: Double) {
        viewModelScope.launch {
            val categoria = categoriaRepository.obtenerPorId(categoriaId)
            categoria?.let {
                val categoriaActualizada = it.copy(objetivo = nuevoObjetivo)
                categoriaRepository.actualizar(categoriaActualizada)
            }
        }
    }

    // 🆕 Función para crear nueva categoría
    fun crearCategoria(nombre: String, icono: String, color: Int, tipo: String, objetivo: Double) {
        viewModelScope.launch {
            val nuevaCategoria = Categoria(
                nombre = nombre,
                icono = icono,
                color = color,
                tipo = tipo,
                objetivo = objetivo
            )
            categoriaRepository.insertar(nuevaCategoria)
        }
    }
}