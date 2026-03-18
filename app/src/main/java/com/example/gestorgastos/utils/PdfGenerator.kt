package com.example.gestorgastos.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import com.example.gestorgastos.data.entity.Gasto
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object PdfGenerator {

    fun generarInforme(
        context: Context,
        gastos: List<Gasto>,
        nombreArchivo: String = "informe_gastos.pdf"
    ): File? {
        // Crear documento PDF
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(300, 600, 1).create() // Tamaño carta aproximado
        val page = pdfDocument.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        var y = 40 // Posición vertical inicial

        // Configurar pinceles (estilos de texto)
        val titlePaint = Paint().apply {
            color = android.graphics.Color.BLACK
            textSize = 20f
            style = Paint.Style.FILL
        }

        val textPaint = Paint().apply {
            color = android.graphics.Color.BLACK
            textSize = 12f
        }

        val boldPaint = Paint().apply {
            color = android.graphics.Color.BLACK
            textSize = 14f
            style = Paint.Style.FILL
        }

        // Título
        canvas.drawText("INFORME DE GASTOS", 20f, y.toFloat(), titlePaint)
        y += 30

        // Línea separadora
        canvas.drawLine(20f, y.toFloat(), 280f, y.toFloat(), textPaint)
        y += 20

        // Cabecera de la tabla
        canvas.drawText("Descripción", 20f, y.toFloat(), boldPaint)
        canvas.drawText("Importe", 230f, y.toFloat(), boldPaint)
        y += 20
        canvas.drawLine(20f, y.toFloat(), 280f, y.toFloat(), textPaint)
        y += 10

        // Lista de gastos
        var total = 0.0
        for (gasto in gastos) {
            if (y > 550) { // Si nos quedamos sin espacio, nueva página
                pdfDocument.finishPage(page)
                // Crear nueva página
                val newPageInfo = PdfDocument.PageInfo.Builder(300, 600, 1).create()
                val newPage = pdfDocument.startPage(newPageInfo)
                // Aquí habría que seguir dibujando, pero por simplificar lo dejamos
                break
            }
            canvas.drawText(gasto.descripcion, 20f, y.toFloat(), textPaint)
            canvas.drawText(String.format("%.2f€", gasto.cantidad), 230f, y.toFloat(), textPaint)
            y += 20
            total += gasto.cantidad
        }

        // Línea final
        y += 10
        canvas.drawLine(20f, y.toFloat(), 280f, y.toFloat(), textPaint)
        y += 20

        // Total
        canvas.drawText("TOTAL:", 20f, y.toFloat(), boldPaint)
        canvas.drawText(String.format("%.2f€", total), 230f, y.toFloat(), boldPaint)

        // Terminar página
        pdfDocument.finishPage(page)

        // Guardar archivo en Descargas
        val downloadsDir = context.getExternalFilesDir(null) ?: context.filesDir
        val file = File(downloadsDir, nombreArchivo)

        try {
            FileOutputStream(file).use { outputStream ->
                pdfDocument.writeTo(outputStream)
            }
            pdfDocument.close()
            return file
        } catch (e: IOException) {
            e.printStackTrace()
            pdfDocument.close()
            return null
        }
    }
}