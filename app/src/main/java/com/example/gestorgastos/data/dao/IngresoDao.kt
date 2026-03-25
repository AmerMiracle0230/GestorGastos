package com.example.gestorgastos.data.dao

import androidx.room.*
import com.example.gestorgastos.data.entity.Ingreso
import kotlinx.coroutines.flow.Flow

@Dao
interface IngresoDao {

    @Query("SELECT * FROM ingresos ORDER BY fecha DESC")
    fun obtenerTodos(): Flow<List<Ingreso>>

    @Query("SELECT * FROM ingresos WHERE categoriaId = :categoriaId")
    fun obtenerPorCategoria(categoriaId: Int): Flow<List<Ingreso>>

    @Query("SELECT * FROM ingresos WHERE fecha BETWEEN :inicio AND :fin")
    fun obtenerEntreFechas(inicio: Long, fin: Long): Flow<List<Ingreso>>

    @Query("SELECT SUM(cantidad) FROM ingresos WHERE fecha BETWEEN :inicio AND :fin")
    suspend fun sumarEntreFechas(inicio: Long, fin: Long): Double

    @Insert
    suspend fun insertar(ingreso: Ingreso)

    @Delete
    suspend fun eliminar(ingreso: Ingreso)
}