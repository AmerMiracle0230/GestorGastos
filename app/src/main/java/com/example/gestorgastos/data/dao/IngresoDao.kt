// archivo: IngresoDao.kt
// que hace: acceso a la tabla ingresos en la base de datos
// operaciones: obtener, filtrar, sumar, insertar, eliminar

package com.example.gestorgastos.data.dao

import androidx.room.*
import com.example.gestorgastos.data.entity.Ingreso
import kotlinx.coroutines.flow.Flow

@Dao
interface IngresoDao {

    // obtener todos los ingresos ordenados por fecha (más reciente primero)
    @Query("SELECT * FROM ingresos ORDER BY fecha DESC")
    fun obtenerTodos(): Flow<List<Ingreso>>

    // obtener ingresos de una categoria específica
    @Query("SELECT * FROM ingresos WHERE categoriaId = :categoriaId")
    fun obtenerPorCategoria(categoriaId: Int): Flow<List<Ingreso>>

    // obtener ingresos entre dos fechas
    @Query("SELECT * FROM ingresos WHERE fecha BETWEEN :inicio AND :fin")
    fun obtenerEntreFechas(inicio: Long, fin: Long): Flow<List<Ingreso>>

    // sumar los ingresos entre dos fechas
    @Query("SELECT SUM(cantidad) FROM ingresos WHERE fecha BETWEEN :inicio AND :fin")
    suspend fun sumarEntreFechas(inicio: Long, fin: Long): Double

    // insertar un ingreso
    @Insert
    suspend fun insertar(ingreso: Ingreso)

    // eliminar un ingreso
    @Delete
    suspend fun eliminar(ingreso: Ingreso)
}