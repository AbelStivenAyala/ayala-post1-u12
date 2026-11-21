package com.empresa.reembolsos.procesamiento.calculo;

import com.empresa.reembolsos.SolicitudesDePrueba;
import com.empresa.reembolsos.dominio.CategoriaGasto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculadoraCapacitacionTest {

    private final CalculadoraCapacitacion calculadora = new CalculadoraCapacitacion();

    @Test
    void reembolsaElOchentaPorCiento() {
        var s = SolicitudesDePrueba.crear("emp-1", CategoriaGasto.CAPACITACION, "1000000",
                LocalDate.of(2026, 3, 10), 0, true);
        assertEquals(0, new BigDecimal("800000").compareTo(calculadora.calcular(s)));
    }

    @Test
    void elOchentaPorCientoSobreElTopeAnual_seLimitaAlTope() {
        var s = SolicitudesDePrueba.crear("emp-1", CategoriaGasto.CAPACITACION, "4000000",
                LocalDate.of(2026, 3, 10), 0, true);
        assertEquals(0, new BigDecimal("3000000").compareTo(calculadora.calcular(s)));
    }
}
