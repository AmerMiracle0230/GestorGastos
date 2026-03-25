package com.example.gestorgastos.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categorias")
data class Categoria(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val icono: String,
    val color: Int,
    val tipo: String,
    val objetivo: Double = 0.0  // 🆕 NUEVO: límite de gasto mensual
)