package com.example.gestorgastos.screens.lista

sealed class Filtro {
    data object TODOS : Filtro()
    data object SOLO_GASTOS : Filtro()
    data object SOLO_INGRESOS : Filtro()
    data class CATEGORIA_GASTO(val nombre: String) : Filtro()
    data class CATEGORIA_INGRESO(val nombre: String) : Filtro()
}