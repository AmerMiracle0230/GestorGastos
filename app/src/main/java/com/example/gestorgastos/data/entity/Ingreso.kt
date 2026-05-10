// archivo: Ingreso.kt
// que hace: representa un ingreso en la base de datos
// relacion: pertenece a una categoria (categoriaId)

package com.example.gestorgastos.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingresos")
data class Ingreso(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,       // descripcion del ingreso ej.: Sueldo, Bono
    val cantidad: Double,     // monto recibido
    val categoriaId: Int,     // id de la categoria a la que pertenece
    val fecha: Long,          // fecha en milisegundos
    val detalle: String = ""  // detalle opcional
)