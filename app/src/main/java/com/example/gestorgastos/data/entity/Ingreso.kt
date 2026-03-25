package com.example.gestorgastos.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingresos")
data class Ingreso(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,        // "Sueldo", "Bono"
    val cantidad: Double,
    val categoriaId: Int,      // ID de la categoría (relación)
    val fecha: Long,
    val detalle: String = ""   // opcional
)