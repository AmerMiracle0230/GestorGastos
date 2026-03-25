package com.example.gestorgastos.data.repository

import com.example.gestorgastos.data.dao.IngresoDao
import com.example.gestorgastos.data.entity.Ingreso
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IngresoRepository @Inject constructor(
    private val ingresoDao: IngresoDao
) {

    fun obtenerTodos(): Flow<List<Ingreso>> = ingresoDao.obtenerTodos()

    fun obtenerPorCategoria(categoriaId: Int): Flow<List<Ingreso>> = ingresoDao.obtenerPorCategoria(categoriaId)

    fun obtenerEntreFechas(inicio: Long, fin: Long): Flow<List<Ingreso>> = ingresoDao.obtenerEntreFechas(inicio, fin)

    suspend fun sumarEntreFechas(inicio: Long, fin: Long): Double = ingresoDao.sumarEntreFechas(inicio, fin)

    suspend fun insertar(ingreso: Ingreso) = ingresoDao.insertar(ingreso)

    suspend fun eliminar(ingreso: Ingreso) = ingresoDao.eliminar(ingreso)
}