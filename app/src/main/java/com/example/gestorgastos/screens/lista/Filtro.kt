// archivo: Filtro.kt
// que hace: define los tipos de filtro para la lista de movimientos
// usado en: ListaScreen

package com.example.gestorgastos.screens.lista

sealed class Filtro {
    data object Todos : Filtro()
    data object SoloGastos : Filtro()
    data object SoloIngresos : Filtro()
    data class CategoriaGasto(val nombre: String) : Filtro()
    data class CategoriaIngreso(val nombre: String) : Filtro()
}