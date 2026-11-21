package com.empresa.reembolsos.procesamiento.calculo;

import com.empresa.reembolsos.dominio.CategoriaGasto;
import com.empresa.reembolsos.dominio.SolicitudReembolso;

import java.math.BigDecimal;

/**
 * Estrategia de calculo del monto aprobado (patron Strategy).
 * Cada implementacion encapsula la formula de una categoria de gasto.
 */
public interface CalculadoraReembolso {

    /** Categoria de gasto que esta estrategia sabe calcular. */
    CategoriaGasto getCategoria();

    /** Monto aprobado segun la formula de la categoria. */
    BigDecimal calcular(SolicitudReembolso solicitud);
}
