package com.example.gestorgastos.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gastos")
data class Gasto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cantidad: Double,
    val descripcion: String,
    val fecha: Long = System.currentTimeMillis(),
    val categoria: String
)