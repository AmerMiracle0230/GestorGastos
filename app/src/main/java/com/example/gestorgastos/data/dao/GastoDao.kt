package com.example.gestorgastos.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.gestorgastos.data.entity.Gasto
import kotlinx.coroutines.flow.Flow

@Dao
interface GastoDao {

    @Query("SELECT * FROM gastos ORDER BY fecha DESC")
    fun obtenerTodos(): Flow<List<Gasto>>

    @Query("SELECT * FROM gastos WHERE categoriaId = :categoriaId")
    fun obtenerPorCategoria(categoriaId: Int): Flow<List<Gasto>>

    @Query("SELECT * FROM gastos WHERE fecha BETWEEN :inicio AND :fin")
    fun obtenerEntreFechas(inicio: Long, fin: Long): Flow<List<Gasto>>

    @Query("SELECT SUM(cantidad) FROM gastos WHERE fecha BETWEEN :inicio AND :fin")
    suspend fun sumarEntreFechas(inicio: Long, fin: Long): Double

    @Insert
    suspend fun insertar(gasto: Gasto)

    @Delete
    suspend fun eliminar(gasto: Gasto)
}