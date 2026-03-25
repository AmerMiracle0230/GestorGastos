package com.example.gestorgastos.data.repository

import com.example.gestorgastos.data.dao.CategoriaDao
import com.example.gestorgastos.data.entity.Categoria
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoriaRepository @Inject constructor(
    private val categoriaDao: CategoriaDao
) {

    fun obtenerTodas(): Flow<List<Categoria>> = categoriaDao.obtenerTodas()

    fun obtenerPorTipo(tipo: String): Flow<List<Categoria>> = categoriaDao.obtenerPorTipo(tipo)

    suspend fun obtenerPorId(id: Int): Categoria? = categoriaDao.obtenerPorId(id)

    suspend fun insertar(categoria: Categoria) = categoriaDao.insertar(categoria)

    suspend fun insertarTodas(categorias: List<Categoria>) = categoriaDao.insertarTodas(categorias)

    suspend fun actualizar(categoria: Categoria) = categoriaDao.actualizar(categoria)

    suspend fun eliminar(categoria: Categoria) = categoriaDao.eliminar(categoria)
}