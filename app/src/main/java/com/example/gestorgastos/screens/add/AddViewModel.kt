// archivo: AddViewModel.kt
// que hace: maneja la logica de la pantalla Add
// funciones: obtener categorias, guardar movimiento (gasto/ingreso)

package com.example.gestorgastos.screens.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestorgastos.data.datastore.Preferencias
import com.example.gestorgastos.data.entity.Categoria
import com.example.gestorgastos.data.entity.Gasto
import com.example.gestorgastos.data.entity.Ingreso
import com.example.gestorgastos.data.repository.CategoriaRepository
import com.example.gestorgastos.data.repository.GastoRepository
import com.example.gestorgastos.data.repository.IngresoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val gastoRepository: GastoRepository,
    private val ingresoRepository: IngresoRepository,
    private val categoriaRepository: CategoriaRepository,
    preferencias: Preferencias
) : ViewModel() {

    // moneda actual desde DataStore
    val monedaActual: StateFlow<String> = preferencias.moneda
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = "€"
        )

    // obtener categorias segun tipo (gasto/ingreso)
    fun obtenerCategoriasPorTipo(tipo: String): Flow<List<Categoria>> {
        return categoriaRepository.obtenerPorTipo(tipo)
    }

    // guardar movimiento (gasto o ingreso) en la base de datos
    fun guardarMovimiento(
        tipo: String,
        nombre: String,
        cantidad: Double,
        categoriaId: Int,
        fecha: Long,
        detalle: String
    ) {
        viewModelScope.launch {
            if (tipo == "gasto") {
                val gasto = Gasto(
                    nombre = nombre,
                    cantidad = cantidad,
                    categoriaId = categoriaId,
                    fecha = fecha,
                    detalle = detalle
                )
                gastoRepository.insertar(gasto)
            } else {
                val ingreso = Ingreso(
                    nombre = nombre,
                    cantidad = cantidad,
                    categoriaId = categoriaId,
                    fecha = fecha,
                    detalle = detalle
                )
                ingresoRepository.insertar(ingreso)
            }
        }
    }
}