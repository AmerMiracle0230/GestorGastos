package com.example.gestorgastos.data.dao

import androidx.room.*
import com.example.gestorgastos.data.entity.Categoria
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriaDao {

    @Query("SELECT * FROM categorias ORDER BY nombre ASC")
    fun obtenerTodas(): Flow<List<Categoria>>

    @Query("SELECT * FROM categorias WHERE tipo = :tipo OR tipo = 'ambos'")
    fun obtenerPorTipo(tipo: String): Flow<List<Categoria>>

    @Query("SELECT * FROM categorias WHERE id = :id")
    suspend fun obtenerPorId(id: Int): Categoria?

    @Insert
    suspend fun insertar(categoria: Categoria)

    @Insert
    suspend fun insertarTodas(categorias: List<Categoria>)

    @Update
    suspend fun actualizar(categoria: Categoria)

    @Delete
    suspend fun eliminar(categoria: Categoria)
}