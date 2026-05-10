// archivo: GestorGastosApp.kt
// que hace: aplicacion principal, se ejecuta al iniciar la app
// funcion: inserta categorias por defecto en la base de datos la primera vez

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

        // si la base de datos esta vacia, insertar categorias por defecto
        CoroutineScope(Dispatchers.IO).launch {
            if (database.categoriaDao().obtenerTodas().first().isEmpty()) {
                database.categoriaDao().insertarTodas(CategoriaInicial.categoriasPorDefecto)
            }
        }
    }
}