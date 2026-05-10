// archivo: GastoDao.kt
// que hace: acceso a la tabla gastos en la base de datos
// operaciones: obtener, filtrar, sumar, insertar, eliminar

package com.example.gestorgastos.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.gestorgastos.data.entity.Gasto
import kotlinx.coroutines.flow.Flow

@Dao
interface GastoDao {

    // obtener todos los gastos ordenados por fecha (más reciente primero)
    @Query("SELECT * FROM gastos ORDER BY fecha DESC")
    fun obtenerTodos(): Flow<List<Gasto>>

    // obtener gastos de una categoria específica
    @Query("SELECT * FROM gastos WHERE categoriaId = :categoriaId")
    fun obtenerPorCategoria(categoriaId: Int): Flow<List<Gasto>>

    // obtener gastos entre dos fechas
    @Query("SELECT * FROM gastos WHERE fecha BETWEEN :inicio AND :fin")
    fun obtenerEntreFechas(inicio: Long, fin: Long): Flow<List<Gasto>>

    // sumar los gastos entre dos fechas
    @Query("SELECT SUM(cantidad) FROM gastos WHERE fecha BETWEEN :inicio AND :fin")
    suspend fun sumarEntreFechas(inicio: Long, fin: Long): Double

    // insertar un gasto
    @Insert
    suspend fun insertar(gasto: Gasto)

    // eliminar un gasto
    @Delete
    suspend fun eliminar(gasto: Gasto)
}