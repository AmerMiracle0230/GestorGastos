package com.example.gestorgastos

import android.app.Application
import com.example.gestorgastos.data.CategoriaInicial
import com.example.gestorgastos.data.database.AppDatabase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class GestorGastosApp : Application() {

    @Inject
    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()

        // Insertar categorías por defecto la primera vez
        CoroutineScope(Dispatchers.IO).launch {
            if (database.categoriaDao().obtenerTodas().first().isEmpty()) {
                database.categoriaDao().insertarTodas(CategoriaInicial.categoriasPorDefecto)
            }
        }
    }
}