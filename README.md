# GestorGastos

Aplicación Android para gestión de gastos e ingresos personales.

## 📱 Características

- Registro de gastos e ingresos (modo básico y avanzado)
- Organización por categorías (gastos, ingresos, ambos)
- Selección de categorías destacadas (hasta 4 en pantalla de inicio)
- Resumen mensual con saldo, ingresos y gastos
- Estadísticas con gráfico circular y leyenda por categoría
- Generación de informes PDF
- Configuración personalizable:
  - Moneda (€, $, £)
  - Tema (sistema, claro, oscuro)
- Creación, edición y eliminación de categorías
- Metas de gasto por categoría
- Meta de ahorro personalizada
- Persistencia de datos con Room Database
- Preferencias guardadas con DataStore
- Soporte para tema oscuro y claro con imágenes de fondo
- Navegación con barra inferior (5 secciones)

## 🛠️ Tecnologías

- **Lenguaje:** Kotlin
- **UI:** Jetpack Compose (Material 3)
- **Arquitectura:** MVVM
- **Inyección de dependencias:** Hilt
- **Base de datos:** Room
- **Preferencias:** DataStore
- **Gráficos:** MPAndroidChart
- **Generación PDF:** PdfDocument (Android)

## 📸 Pantallas principales

1. **Home** - Saldo, categorías destacadas, meta de ahorro
2. **Lista** - Movimientos (gastos/ingresos) con filtros y swipe para eliminar
3. **Añadir** - Registro de movimientos (rápido o avanzado)
4. **Estadísticas** - Gráfico circular de gastos por categoría
5. **PDF** - Generación de informe financiero
6. **Configuración** - Moneda, tema, categorías destacadas, metas

## 🚀 Cómo ejecutar

1. Clonar el repositorio
2. Abrir en Android Studio
3. Sincronizar Gradle
4. Ejecutar en dispositivo o emulador (API 24+)

## 📦 Dependencias principales

```gradle
implementation("androidx.compose.material3:material3:1.2.0")
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.datastore:datastore-preferences:1.0.0")
implementation("com.google.dagger:hilt-android:2.48")
implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
