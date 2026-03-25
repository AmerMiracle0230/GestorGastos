package com.example.gestorgastos.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import com.example.gestorgastos.data.entity.Gasto
import com.example.gestorgastos.data.entity.Ingreso
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

object PdfGenerator {

    fun generarInforme(
        context: Context,
        gastos: List<Gasto>,
        ingresos: List<Ingreso>,
        mapaCategorias: Map<Int, com.example.gestorgastos.data.entity.Categoria>
    ): File? {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        var page = pdfDocument.startPage(pageInfo)
        var canvas: Canvas = page.canvas

        var y = 40
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        // Título
        val titlePaint = Paint().apply {
            color = android.graphics.Color.BLACK
            textSize = 20f
            style = Paint.Style.FILL
        }
        canvas.drawText("INFORME ARK", 20f, y.toFloat(), titlePaint)
        y += 30

        // Fecha
        val textPaint = Paint().apply {
            color = android.graphics.Color.BLACK
            textSize = 10f
        }
        canvas.drawText("Generado: ${dateFormat.format(Date())}", 20f, y.toFloat(), textPaint)
        y += 20

        // Línea separadora
        canvas.drawLine(20f, y.toFloat(), 575f, y.toFloat(), textPaint)
        y += 20

        // Totales
        val totalGastos = gastos.sumOf { it.cantidad }
        val totalIngresos = ingresos.sumOf { it.cantidad }
        val saldo = totalIngresos - totalGastos

        val boldPaint = Paint().apply {
            color = android.graphics.Color.BLACK
            textSize = 12f
            style = Paint.Style.FILL
        }

        canvas.drawText("RESUMEN", 20f, y.toFloat(), boldPaint)
        y += 20
        canvas.drawText("Total gastos: ${String.format("%.2f", totalGastos)}€", 20f, y.toFloat(), textPaint)
        y += 15
        canvas.drawText("Total ingresos: ${String.format("%.2f", totalIngresos)}€", 20f, y.toFloat(), textPaint)
        y += 15
        canvas.drawText("Saldo: ${String.format("%.2f", saldo)}€", 20f, y.toFloat(), textPaint)
        y += 25

        // Función para crear nueva página
        fun nuevaPagina(): Canvas {
            pdfDocument.finishPage(page)
            val newPageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
            page = pdfDocument.startPage(newPageInfo)
            y = 40
            return page.canvas
        }

        // Gastos
        if (gastos.isNotEmpty()) {
            canvas.drawText("GASTOS", 20f, y.toFloat(), boldPaint)
            y += 20
            for (gasto in gastos) {
                val categoria = mapaCategorias[gasto.categoriaId]
                val icono = categoria?.icono ?: "💸"
                val texto = "$icono ${gasto.nombre}: ${String.format("%.2f", gasto.cantidad)}€ - ${dateFormat.format(Date(gasto.fecha))}"
                if (y > 800) {
                    canvas = nuevaPagina()
                }
                canvas.drawText(texto, 20f, y.toFloat(), textPaint)
                y += 15
            }
            y += 10
        }

        // Ingresos
        if (ingresos.isNotEmpty()) {
            if (y > 780) {
                canvas = nuevaPagina()
            }
            canvas.drawText("INGRESOS", 20f, y.toFloat(), boldPaint)
            y += 20
            for (ingreso in ingresos) {
                val categoria = mapaCategorias[ingreso.categoriaId]
                val icono = categoria?.icono ?: "💰"
                val texto = "$icono ${ingreso.nombre}: ${String.format("%.2f", ingreso.cantidad)}€ - ${dateFormat.format(Date(ingreso.fecha))}"
                if (y > 800) {
                    canvas = nuevaPagina()
                }
                canvas.drawText(texto, 20f, y.toFloat(), textPaint)
                y += 15
            }
        }

        pdfDocument.finishPage(page)

        val downloadsDir = context.getExternalFilesDir(null) ?: context.filesDir
        val file = File(downloadsDir, "informe_ark_${System.currentTimeMillis()}.pdf")

        return try {
            FileOutputStream(file).use { outputStream ->
                pdfDocument.writeTo(outputStream)
            }
            pdfDocument.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            pdfDocument.close()
            null
        }
    }
}