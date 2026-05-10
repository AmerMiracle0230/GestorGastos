// archivo: CategoriaInicial.kt
// que hace: lista de categorias predefinidas para la primera ejecucion
// se insertan en la base de datos si esta vacia

package com.example.gestorgastos.data

import com.example.gestorgastos.data.entity.Categoria

object CategoriaInicial {

    val categoriasPorDefecto = listOf(
        // gastos
        Categoria(nombre = "Comida", icono = "🍔", color = 0xFF4CAF50.toInt(), tipo = "gasto", objetivo = 200.0),
        Categoria(nombre = "Transporte", icono = "🚗", color = 0xFF2196F3.toInt(), tipo = "gasto", objetivo = 100.0),
        Categoria(nombre = "Ocio", icono = "🎮", color = 0xFFFF9800.toInt(), tipo = "gasto", objetivo = 80.0),
        Categoria(nombre = "Vivienda", icono = "🏠", color = 0xFF795548.toInt(), tipo = "gasto", objetivo = 500.0),
        Categoria(nombre = "Salud", icono = "💊", color = 0xFFF44336.toInt(), tipo = "gasto", objetivo = 100.0),
        Categoria(nombre = "Educacion", icono = "🎓", color = 0xFF3F51B5.toInt(), tipo = "gasto", objetivo = 150.0),

        // ingresos
        Categoria(nombre = "Sueldo", icono = "💰", color = 0xFF4CAF50.toInt(), tipo = "ingreso", objetivo = 0.0),
        Categoria(nombre = "Extra", icono = "🎁", color = 0xFF9C27B0.toInt(), tipo = "ingreso", objetivo = 0.0),
        Categoria(nombre = "Regalo", icono = "🎀", color = 0xFFE91E63.toInt(), tipo = "ingreso", objetivo = 0.0),

        // ambos (gasto e ingreso)
        Categoria(nombre = "Otros", icono = "📦", color = 0xFF9E9E9E.toInt(), tipo = "ambos", objetivo = 0.0)
    )
}