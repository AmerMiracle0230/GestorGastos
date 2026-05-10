// archivo: PdfGenerator.kt
// que hace: genera informe PDF de gastos e ingresos

package com.example.gestorgastos.utils

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Environment
import com.example.gestorgastos.data.entity.Gasto
import com.example.gestorgastos.data.entity.Ingreso
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

object PdfGenerator {

    fun generarInforme(
        gastos: List<Gasto>,           // SIN context
        ingresos: List<Ingreso>,
        mapaCategorias: Map<Int, com.example.gestorgastos.data.entity.Categoria>,
        moneda: String = "€"
    ): File? {
        val pdfDocument = PdfDocument()
        val pageWidth = 595
        val pageHeight = 842

        var pageNumber = 1
        var pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
        var page = pdfDocument.startPage(pageInfo)
        var canvas: Canvas = page.canvas

        var y = 50f
        val locale = Locale.getDefault()
        val dateFormatLarge = SimpleDateFormat("dd 'de' MMMM 'de' yyyy", Locale("es", "ES"))
        val dateFormatFile = SimpleDateFormat("yyyyMMdd_HHmmss", locale)
        val dateFormatShort = SimpleDateFormat("dd/MM/yy", locale)


        val azulApp = android.graphics.Color.parseColor("#29B6F6")
        val grisClaro = android.graphics.Color.parseColor("#F5F5F5")
        val grisOscuro = android.graphics.Color.parseColor("#333333")
        val rojo = android.graphics.Color.parseColor("#F44336")
        val verde = android.graphics.Color.parseColor("#4CAF50")

        // pinceles
        val titlePaint = Paint().apply {
            color = azulApp
            textSize = 28f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            textAlign = Paint.Align.CENTER
        }
        val subtitlePaint = Paint().apply {
            color = android.graphics.Color.GRAY
            textSize = 12f
            textAlign = Paint.Align.CENTER
        }
        val linePaint = Paint().apply {
            color = azulApp
            strokeWidth = 2f
        }
        val textPaint = Paint().apply {
            color = grisOscuro
            textSize = 10f
        }
        val boldPaint = Paint().apply {
            color = azulApp
            textSize = 14f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }
        val cardBgPaint = Paint().apply {
            color = grisClaro
            style = Paint.Style.FILL
        }
        val rowAlternatePaint = Paint().apply {
            color = android.graphics.Color.parseColor("#F9F9F9")
            style = Paint.Style.FILL
        }
        val progressPaint = Paint().apply {
            color = azulApp
            style = Paint.Style.FILL
        }
        val headerPaint = Paint().apply {
            color = azulApp
            textSize = 10f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }
        val footerPaint = Paint().apply {
            color = android.graphics.Color.GRAY
            textSize = 8f
            textAlign = Paint.Align.CENTER
        }

        fun dibujarPiePagina() {
            canvas.drawText("ARK - Gestor de Finanzas", pageWidth / 2f, pageHeight - 20f, footerPaint)
            canvas.drawText("Pagina $pageNumber", pageWidth / 2f, pageHeight - 10f, footerPaint)
        }

        fun dibujarCabecera() {
            y = 50f
            canvas.drawText("ARK", pageWidth / 2f, y, titlePaint)
            y += 35f
            canvas.drawText("INFORME DE FINANZAS PERSONALES", pageWidth / 2f, y, subtitlePaint)
            y += 30f
            canvas.drawLine(50f, y, pageWidth - 50f, y, linePaint)
            y += 25f
            canvas.drawText("Generado: ${dateFormatLarge.format(Date())}", 50f, y, textPaint)
            y += 25f
        }

        fun nuevaPagina() {
            dibujarPiePagina()
            pdfDocument.finishPage(page)
            pageNumber++
            pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
            page = pdfDocument.startPage(pageInfo)
            canvas = page.canvas
            dibujarCabecera()
        }

        fun checkNuevaPagina(espacioNecesario: Float) {
            if (y + espacioNecesario > 800f) {
                nuevaPagina()
            }
        }

        dibujarCabecera()

        // resumen general
        val totalGastos = gastos.sumOf { it.cantidad }
        val totalIngresos = ingresos.sumOf { it.cantidad }
        val saldo = totalIngresos - totalGastos

        checkNuevaPagina(120f)
        canvas.drawRoundRect(50f, y, pageWidth - 50f, y + 120f, 10f, 10f, cardBgPaint)

        canvas.drawText("📊 RESUMEN GENERAL", 60f, y + 25f, boldPaint)
        canvas.drawText("💸 Gastos:", 60f, y + 55f, textPaint)

        val valuePaint = Paint().apply {
            textSize = 14f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }

        valuePaint.color = rojo
        canvas.drawText(String.format(locale, "%.2f%s", totalGastos, moneda), 250f, y + 55f, valuePaint)

        canvas.drawText("💰 Ingresos:", 60f, y + 80f, textPaint)
        valuePaint.color = verde
        canvas.drawText(String.format(locale, "%.2f%s", totalIngresos, moneda), 250f, y + 80f, valuePaint)

        canvas.drawText("💵 Saldo:", 60f, y + 105f, textPaint)
        valuePaint.color = if (saldo >= 0) verde else rojo
        canvas.drawText(String.format(locale, "%.2f%s", saldo, moneda), 250f, y + 105f, valuePaint)
        y += 145f

        // gastos por categoria
        if (gastos.isNotEmpty()) {
            checkNuevaPagina(60f)
            canvas.drawText("📊 GASTOS POR CATEGORIA", 50f, y, boldPaint)
            y += 25f
            canvas.drawLine(50f, y, pageWidth - 50f, y, linePaint)
            y += 25f

            val gastosPorCategoria = gastos.groupBy { it.categoriaId }
                .mapValues { (_, lista) -> lista.sumOf { it.cantidad } }
                .toList()
                .sortedByDescending { it.second }

            gastosPorCategoria.forEachIndexed { index, (categoriaId, total) ->
                checkNuevaPagina(30f)
                val categoria = mapaCategorias[categoriaId]
                val nombre = categoria?.nombre ?: "Sin categoria"
                val icono = categoria?.icono ?: "📦"
                val porcentaje = if (totalGastos > 0) (total / totalGastos * 100).toInt() else 0

                if (index % 2 == 1) {
                    canvas.drawRect(50f, y - 18f, pageWidth - 50f, y + 5f, rowAlternatePaint)
                }

                canvas.drawText("$icono $nombre", 60f, y, textPaint)
                val barWidth = if (totalGastos > 0) ((total / totalGastos) * 250).toFloat() else 0f
                canvas.drawRoundRect(200f, y - 12f, 200f + barWidth, y + 2f, 4f, 4f, progressPaint)
                canvas.drawText(String.format(locale, "%.2f%s (%d%%)", total, moneda, porcentaje), 460f, y, textPaint)
                y += 28f
            }
        }

        // detalle de movimientos
        val todosMovimientos = (gastos.map { "gasto" to it } + ingresos.map { "ingreso" to it })
            .sortedByDescending { pair ->
                when (val m = pair.second) {
                    is Gasto -> m.fecha
                    is Ingreso -> m.fecha
                    else -> 0L
                }
            }

        if (todosMovimientos.isNotEmpty()) {
            checkNuevaPagina(80f)
            y += 15f
            canvas.drawText("📋 DETALLE DE MOVIMIENTOS", 50f, y, boldPaint)
            y += 25f
            canvas.drawLine(50f, y, pageWidth - 50f, y, linePaint)
            y += 25f

            // encabezados
            canvas.drawText("FECHA", 60f, y, headerPaint)
            canvas.drawText("CONCEPTO", 140f, y, headerPaint)
            canvas.drawText("CATEGORIA", 320f, y, headerPaint)
            canvas.drawText("IMPORTE", 500f, y, headerPaint)
            y += 20f
            canvas.drawLine(50f, y, pageWidth - 50f, y, linePaint)
            y += 15f

            todosMovimientos.forEachIndexed { index, (tipo, m) ->
                checkNuevaPagina(25f)
                val nombre = if (tipo == "gasto") (m as Gasto).nombre else (m as Ingreso).nombre
                val cantidad = if (tipo == "gasto") (m as Gasto).cantidad else (m as Ingreso).cantidad
                val catId = if (tipo == "gasto") (m as Gasto).categoriaId else (m as Ingreso).categoriaId
                val fecha = if (tipo == "gasto") (m as Gasto).fecha else (m as Ingreso).fecha
                val catNombre = mapaCategorias[catId]?.nombre ?: "Sin categoria"
                val colorImporte = if (tipo == "gasto") rojo else verde

                if (index % 2 == 1) {
                    canvas.drawRect(50f, y - 12f, pageWidth - 50f, y + 5f, rowAlternatePaint)
                }

                canvas.drawText(dateFormatShort.format(Date(fecha)), 60f, y, textPaint)
                canvas.drawText(if (nombre.length > 20) nombre.take(17) + "..." else nombre, 140f, y, textPaint)
                canvas.drawText(if (catNombre.length > 20) catNombre.take(17) + "..." else catNombre, 320f, y, textPaint)

                val impPaint = Paint().apply {
                    textSize = 11f
                    color = colorImporte
                    typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                    textAlign = Paint.Align.RIGHT
                }
                canvas.drawText(String.format(locale, "%s%.2f%s", if (tipo == "gasto") "-" else "+", cantidad, moneda), 540f, y, impPaint)
                y += 22f
            }
        }

        dibujarPiePagina()
        pdfDocument.finishPage(page)

        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        if (!downloadsDir.exists()) {
            downloadsDir.mkdirs()
        }

        val fileName = "ARK_Informe_${dateFormatFile.format(Date())}.pdf"
        val file = File(downloadsDir, fileName)

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