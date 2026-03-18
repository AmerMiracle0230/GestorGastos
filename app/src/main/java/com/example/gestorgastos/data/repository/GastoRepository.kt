package com.example.gestorgastos.data.repository

import com.example.gestorgastos.data.dao.GastoDao
import com.example.gestorgastos.data.entity.Gasto
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GastoRepository @Inject constructor(
    private val gastoDao: GastoDao
) {

    fun obtenerTodosLosGastos(): Flow<List<Gasto>> =
        gastoDao.obtenerTodos()

    fun obtenerGastosPorCategoria(categoria: String): Flow<List<Gasto>> =
        gastoDao.obtenerPorCategoriaFlow(categoria)

    suspend fun obtenerGastosEntreFechas(inicio: Long, fin: Long): List<Gasto> =
        gastoDao.obtenerEntreFechas(inicio, fin)

    suspend fun calcularTotalGastos(): Double =
        gastoDao.sumarTotal() ?: 0.0

    suspend fun calcularTotalPorCategoria(): Map<String, Double> {
        val lista = gastoDao.sumarPorCategoria()
        return lista.associate { it.categoria to it.total }
    }

    fun obtenerTotalPorCategoriaFlow(): Flow<Map<String, Double>> =
        gastoDao.obtenerTodos().map { lista ->
            lista.groupBy { it.categoria }
                .mapValues { (_, gastos) ->
                    gastos.sumOf { it.cantidad }
                }
        }

    fun obtenerUltimosGastos(limite: Int = 5): Flow<List<Gasto>> =
        gastoDao.obtenerTodos().map { lista ->
            lista.take(limite)
        }

    suspend fun insertarGasto(gasto: Gasto) {
        gastoDao.insertar(gasto)
    }

    suspend fun insertarGastos(gastos: List<Gasto>) {
        gastoDao.insertarTodos(gastos)
    }

    suspend fun actualizarGasto(gasto: Gasto) {
        gastoDao.actualizar(gasto)
    }

    suspend fun eliminarGasto(gasto: Gasto) {
        gastoDao.borrar(gasto)
    }

    suspend fun eliminarGastoPorId(id: Int) {
        gastoDao.borrarPorId(id)
    }

    suspend fun eliminarTodosLosGastos() {
        gastoDao.borrarTodos()
    }

    fun validarGasto(gasto: Gasto): Boolean {
        if (gasto.cantidad <= 0) return false
        if (gasto.descripcion.isBlank()) return false
        if (gasto.categoria.isBlank()) return false
        return true
    }

    fun superaLimite(gasto: Gasto, limite: Double = 1000.0): Boolean =
        gasto.cantidad > limite
}
