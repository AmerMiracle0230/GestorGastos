// archivo: Gasto.kt
// que hace: representa un gasto en la base de datos
// relacion: pertenece a una categoria (categoriaId)

package com.example.gestorgastos.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gastos")
data class Gasto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,       // descripcion del gasto ej: Cafe, Pizza
    val cantidad: Double,     // monto gastado
    val categoriaId: Int,     // id de la categoria a la que pertenece
    val fecha: Long,          // fecha en milisegundos
    val detalle: String = ""  // detalle opcional
)