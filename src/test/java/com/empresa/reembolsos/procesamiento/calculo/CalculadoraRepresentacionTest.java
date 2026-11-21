package com.empresa.reembolsos.procesamiento.calculo;

import com.empresa.reembolsos.SolicitudesDePrueba;
import com.empresa.reembolsos.dominio.CategoriaGasto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculadoraRepresentacionTest {

    private final CalculadoraRepresentacion calculadora = new CalculadoraRepresentacion();

    @Test
    void montoDentroDelTope_seReembolsaCompleto() {
        var s = SolicitudesDePrueba.crear("emp-1", CategoriaGasto.REPRESENTACION, "500000",
                LocalDate.of(2026, 3, 10), 0, true);
        assertEquals(0, new BigDecimal("500000").compareTo(calculadora.calcular(s)));
    }

    @Test
    void montoSobreElTopeMensual_seLimitaAlTope() {
        var s = SolicitudesDePrueba.crear("emp-1", CategoriaGasto.REPRESENTACION, "2500000",
                LocalDate.of(2026, 3, 10), 0, true);
        assertEquals(0, new BigDecimal("2000000").compareTo(calculadora.calcular(s)));
    }
}
