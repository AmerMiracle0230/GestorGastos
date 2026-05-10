// archivo: AppDatabase.kt
// que hace: base de datos Room con las tablas categoria, gasto, ingreso
// version: 3

package com.example.gestorgastos.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gestorgastos.data.dao.CategoriaDao
import com.example.gestorgastos.data.dao.GastoDao
import com.example.gestorgastos.data.dao.IngresoDao
import com.example.gestorgastos.data.entity.Categoria
import com.example.gestorgastos.data.entity.Gasto
import com.example.gestorgastos.data.entity.Ingreso

@Database(
    entities = [Categoria::class, Gasto::class, Ingreso::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoriaDao(): CategoriaDao
    abstract fun gastoDao(): GastoDao
    abstract fun ingresoDao(): IngresoDao
}