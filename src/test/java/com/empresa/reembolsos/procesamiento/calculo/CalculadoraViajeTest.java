package com.empresa.reembolsos.procesamiento.calculo;

import com.empresa.reembolsos.SolicitudesDePrueba;
import com.empresa.reembolsos.dominio.CategoriaGasto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculadoraViajeTest {

    private final CalculadoraViaje calculadora = new CalculadoraViaje();

    @Test
    void montoMayorAlTopeDiarioPorDias_seLimitaAlTope() {
        var s = SolicitudesDePrueba.crear("emp-1", CategoriaGasto.VIAJE, "500000",
                LocalDate.of(2026, 3, 10), 2, true);
        assertEquals(0, new BigDecimal("300000").compareTo(calculadora.calcular(s)));
    }

    @Test
    void montoMenorAlTope_seReembolsaCompleto() {
        var s = SolicitudesDePrueba.crear("emp-1", CategoriaGasto.VIAJE, "100000",
                LocalDate.of(2026, 3, 10), 2, true);
        assertEquals(0, new BigDecimal("100000").compareTo(calculadora.calcular(s)));
    }
}
