# apellido-post1-u12 — Proyecto Integrador: Sistema de Solicitudes de Reembolso de Gastos Corporativos

Post-contenido Unidad 12 · Patrones de Diseño de Software
Autor: _NOMBRE APELLIDO_ · Código: _CODIGO_

Sistema Spring Boot 3 (Java 17, Maven, H2) con arquitectura en capas simples y tres patrones de comportamiento elegidos a partir de los requisitos de Finanzas: **Chain of Responsibility** (validación), **Strategy** (cálculo por categoría) y **Observer** (notificaciones). La Parte 2 documenta la decisión arquitectónica (ADR-001), verifica los límites entre paquetes con ArchUnit en GitHub Actions y cierra con un análisis de lo que se descartó por sobreingeniería.

---

## 1. Diseño Propuesto

> Esta sección se escribió **antes** de programar cualquier patrón (commit 1 del historial).

| Requisito | Qué varía (punto de variabilidad) | Patrón | Justificación |
|-----------|-----------------------------------|--------|---------------|
| **RF1** Validación en varias etapas | El conjunto y el orden de verificaciones previas a la aprobación: hoy son tres (monto, documentos, duplicados) y Finanzas ya anunció una cuarta (límite anual acumulado). | **Chain of Responsibility** | Cada verificación es un eslabón independiente que rechaza o pasa la solicitud al siguiente, de modo que agregar el límite anual es crear un eslabón nuevo y enlazarlo, sin modificar los existentes. |
| **RF2** Cálculo por categoría | La fórmula del monto aprobado, distinta para viáticos, representación y capacitación, y con una cuarta categoría ("bienestar") prevista. | **Strategy** | Cada categoría es una estrategia intercambiable que encapsula su fórmula, así una categoría nueva es una clase nueva y ninguna fórmula existente se toca. |
| **RF3** Notificación a varios interesados | Quién reacciona a un cambio de estado y cómo: el empleado (siempre), Contabilidad (solo aprobaciones), Auditoría (todo) y un panel de métricas en camino. | **Observer** | Cada interesado se suscribe con su propia reacción, de modo que el servicio publica el cambio de estado una sola vez sin conocer cuántos ni cuáles son los interesados. |

**Por qué estos patrones y no otros**

- RF1: una cadena y no un único método con tres `if` porque el requisito exige crecer sin modificar lo ya escrito; un `if` más sí obligaría a tocar el método existente.
- RF2: Strategy y no un `switch` sobre `CategoriaGasto` porque la variabilidad es la fórmula completa, y las categorías nuevas ya están anunciadas (ver Decisión de diseño 2).
- RF3: Observer y no llamadas directas a tres clases porque el número de interesados crece (panel de métricas) y cada uno reacciona distinto a los mismos eventos.

---
