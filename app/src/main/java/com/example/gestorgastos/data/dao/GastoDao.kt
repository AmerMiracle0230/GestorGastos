package com.example.gestorgastos.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.gestorgastos.data.entity.Gasto
import kotlinx.coroutines.flow.Flow

@Dao
interface GastoDao {

    @Insert
    suspend fun insertar(gasto: Gasto)

    @Query("SELECT * FROM gastos ORDER BY fecha DESC")
    fun obtenerTodos(): Flow<List<Gasto>>

    @Query("SELECT * FROM gastos WHERE categoria = :categoria")
    suspend fun obtenerPorCategoria(categoria: String): List<Gasto>

    @Query("SELECT * FROM gastos WHERE fecha BETWEEN :inicio AND :fin")
    suspend fun obtenerEntreFechas(inicio: Long, fin: Long): List<Gasto>

    @Query("SELECT SUM(cantidad) FROM gastos")
    suspend fun sumarTotal(): Double

    // arreglar eto crear carpeta y modificar clase
    data class TotalPorCategoria(
        val categoria: String,
        val total: Double
    )

    //modificar esta por la ruta que se pondra
    @Query("SELECT categoria, SUM(cantidad) as total FROM gastos GROUP BY categoria")
    suspend fun sumarPorCategoria(): List<TotalPorCategoria>

    @Insert
    suspend fun insertarTodos(gastos: List<Gasto>)

    @Update
    suspend fun actualizar(gasto: Gasto)

    @Query("DELETE FROM gastos WHERE id = :id")
    suspend fun borrarPorId(id: Int)

    @Query("DELETE FROM gastos")
    suspend fun borrarTodos()
    @Query("SELECT * FROM gastos WHERE categoria = :categoria")
    fun obtenerPorCategoriaFlow(categoria: String): Flow<List<Gasto>>

    @Delete
    suspend fun borrar(gasto: Gasto)
}