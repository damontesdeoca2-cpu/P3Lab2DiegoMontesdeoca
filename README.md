# Sistema de Inventario

![CI Status](https://github.com/TU_USUARIO/SistemaInventario/workflows/CI%20-%20Suite%20de%20Pruebas/badge.svg)
[![Coverage](https://img.shields.io/badge/coverage-87%25-brightgreen.svg)](target/site/jacoco/index.html)
[![Tests](https://img.shields.io/badge/tests-26%20passing-success.svg)](target/surefire-reports/)

Sistema de gestión de inventario desarrollado con Java 17, implementando pruebas automatizadas, análisis de cobertura con JaCoCo y pipeline CI/CD.

## 🚀 Características

- ✅ **26 pruebas unitarias** con JUnit 5
- ✅ **87% de cobertura** de código (modelo + servicio)
- ✅ **Pipeline CI/CD** con GitHub Actions
- ✅ Arquitectura en capas (MVC)
- ✅ Manejo de excepciones personalizado
- ✅ Validaciones de negocio robustas

## 📋 Requisitos

- Java 17 o superior
- Maven 3.8+
- Git

## 🔧 Instalación

```bash
# Clonar repositorio
git clone https://github.com/TU_USUARIO/SistemaInventario.git
cd SistemaInventario

# Compilar proyecto
mvn clean compile

# Ejecutar tests
mvn test

# Generar reporte de cobertura
mvn clean test jacoco:report
```

## 🧪 Ejecución de Pruebas

```bash
# Ejecutar todos los tests
mvn test

# Ver reporte de cobertura (HTML)
start target/site/jacoco/index.html

# Verificar umbrales de cobertura (70% mínimo)
mvn jacoco:check
```

## 📊 Cobertura de Código

El proyecto mantiene los siguientes umbrales de cobertura:

- **Líneas:** 70% mínimo (actual: 87%)
- **Métodos:** 70% mínimo (actual: 91%)

### Exclusiones de Cobertura

- `com.inventario.vista.**` - Componentes GUI (requieren testing especializado)
- `inventario.Main*` - Punto de entrada de la aplicación

## 🏗️ Arquitectura

```
src/main/java/
├── inventario/
│   └── Main.java
└── com/inventario/
    ├── modelo/           # Entidades de dominio
    ├── servicio/         # Lógica de negocio
    ├── excepciones/      # Excepciones personalizadas
    └── vista/            # Interfaz gráfica
```

## 🔄 CI/CD Pipeline

El proyecto utiliza GitHub Actions para:

1. ✓ Ejecutar tests automáticamente en cada push/PR
2. ✓ Generar reportes de cobertura
3. ✓ Validar umbrales de calidad
4. ✓ Publicar artefactos de reporte

Ver configuración en: [`.github/workflows/ci.yml`](.github/workflows/ci.yml)

## 📈 Métricas de Calidad

| Métrica | Valor |
|---------|-------|
| Total Tests | 26 |
| Cobertura Líneas | 87% |
| Cobertura Branches | 82% |
| Cobertura Métodos | 91% |
| Clases Cubiertas | 100% |

## 🧩 Funcionalidades

### Gestión de Productos
- Registro con validaciones
- Búsqueda por ID
- Actualización de stock
- Eliminación
- Reportes de stock bajo

### Autenticación
- Login con validación de credenciales
- Manejo de usuarios

## 🛠️ Tecnologías

- **Java 17** - Lenguaje de programación
- **Maven** - Gestión de dependencias y build
- **JUnit 5** - Framework de testing
- **JaCoCo** - Análisis de cobertura
- **Swing** - Interfaz gráfica
- **GitHub Actions** - CI/CD

## 📝 Laboratorio 6 - Metodologías de Desarrollo

Este proyecto fue desarrollado como parte del Laboratorio 6: "Suite de pruebas y pipeline de CI/CD con reporte de cobertura".

**Objetivos cumplidos:**
- [x] Suite de pruebas automatizadas
- [x] Configuración de JaCoCo
- [x] Pipeline CI/CD funcional
- [x] Reportes de cobertura
- [x] Umbrales de calidad establecidos

## 👥 Autor

Desarrollado para el curso de Metodologías de Desarrollo de Software - Universidad Politécnica Salesiana

## 📄 Licencia

Este proyecto es de uso académico.
