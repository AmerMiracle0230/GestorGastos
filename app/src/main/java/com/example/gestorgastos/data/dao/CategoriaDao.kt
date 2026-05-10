// archivo: CategoriaDao.kt
// que hace: acceso a la tabla categorias en la base de datos
// operaciones: obtener, insertar, actualizar, eliminar

package com.example.gestorgastos.data.dao

import androidx.room.*
import com.example.gestorgastos.data.entity.Categoria
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriaDao {

    // obtener todas las categorias ordenadas por nombre
    @Query("SELECT * FROM categorias ORDER BY nombre ASC")
    fun obtenerTodas(): Flow<List<Categoria>>

    // obtener categorias por tipo (gasto, ingreso, ambos)
    @Query("SELECT * FROM categorias WHERE tipo = :tipo OR tipo = 'ambos'")
    fun obtenerPorTipo(tipo: String): Flow<List<Categoria>>

    // obtener una categoria por su id
    @Query("SELECT * FROM categorias WHERE id = :id")
    suspend fun obtenerPorId(id: Int): Categoria?

    // insertar una categoria
    @Insert
    suspend fun insertar(categoria: Categoria)

    // insertar varias categorias de una vez
    @Insert
    suspend fun insertarTodas(categorias: List<Categoria>)

    // actualizar una categoria
    @Update
    suspend fun actualizar(categoria: Categoria)

    // eliminar una categoria
    @Delete
    suspend fun eliminar(categoria: Categoria)
}