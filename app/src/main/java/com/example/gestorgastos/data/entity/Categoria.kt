// archivo: Categoria.kt
// que hace: representa una categoria en la base de datos
// tipos: gasto, ingreso, ambos
// objetivo: limite mensual de gasto (solo para categorias de gasto)

package com.example.gestorgastos.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categorias")
data class Categoria(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,       // nombre de la categoria ej: Comida, Transporte
    val icono: String,        // emoji de la categoria ej: 🍔, 🚗
    val color: Int,           // color en formato Int (ARGB)
    val tipo: String,         // tipo: "gasto", "ingreso", "ambos"
    val objetivo: Double = 0.0  // meta de gasto mensual (solo para gastos)
)