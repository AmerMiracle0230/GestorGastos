package com.example.gestorgastos.data

import androidx.compose.ui.graphics.Color
import com.example.gestorgastos.data.entity.Categoria

object CategoriaInicial {

    val categoriasPorDefecto = listOf(
        // GASTOS
        Categoria(nombre = "Comida", icono = "🍔", color = Color(0xFF4CAF50).value.toInt(), tipo = "gasto"),
        Categoria(nombre = "Transporte", icono = "🚗", color = Color(0xFF2196F3).value.toInt(), tipo = "gasto"),
        Categoria(nombre = "Ocio", icono = "🎮", color = Color(0xFFFF9800).value.toInt(), tipo = "gasto"),
        Categoria(nombre = "Vivienda", icono = "🏠", color = Color(0xFF795548).value.toInt(), tipo = "gasto"),
        Categoria(nombre = "Salud", icono = "💊", color = Color(0xFFF44336).value.toInt(), tipo = "gasto"),
        Categoria(nombre = "Educación", icono = "🎓", color = Color(0xFF3F51B5).value.toInt(), tipo = "gasto"),

        // INGRESOS
        Categoria(nombre = "Sueldo", icono = "💰", color = Color(0xFF4CAF50).value.toInt(), tipo = "ingreso"),
        Categoria(nombre = "Extra", icono = "🎁", color = Color(0xFF9C27B0).value.toInt(), tipo = "ingreso"),
        Categoria(nombre = "Regalo", icono = "🎀", color = Color(0xFFE91E63).value.toInt(), tipo = "ingreso"),

        // AMBOS
        Categoria(nombre = "Otros", icono = "📦", color = Color(0xFF9E9E9E).value.toInt(), tipo = "ambos")
    )
}