package com.example.gestorgastos.data.repository

import com.example.gestorgastos.data.dao.GastoDao
import com.example.gestorgastos.data.entity.Gasto
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow


class GastoRepository @Inject constructor(
    private val gastoDao: GastoDao
) {

    fun obtenerTodos(): Flow<List<Gasto>> = gastoDao.obtenerTodos()

    fun obtenerPorCategoria(categoriaId: Int): Flow<List<Gasto>> = gastoDao.obtenerPorCategoria(categoriaId)

    fun obtenerEntreFechas(inicio: Long, fin: Long): Flow<List<Gasto>> = gastoDao.obtenerEntreFechas(inicio, fin)

    suspend fun sumarEntreFechas(inicio: Long, fin: Long): Double = gastoDao.sumarEntreFechas(inicio, fin)

    suspend fun insertar(gasto: Gasto) = gastoDao.insertar(gasto)

    suspend fun eliminar(gasto: Gasto) = gastoDao.eliminar(gasto)
}