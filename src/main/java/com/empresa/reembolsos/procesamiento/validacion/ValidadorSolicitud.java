package com.empresa.reembolsos.procesamiento.validacion;

import com.empresa.reembolsos.dominio.SolicitudReembolso;

import java.util.List;

/**
 * Eslabon de la cadena de validacion (patron Chain of Responsibility).
 * Cada eslabon aplica su propia verificacion y, si la solicitud es valida,
 * delega en el siguiente sin conocer cuantos eslabones hay despues.
 */
public abstract class ValidadorSolicitud {

    private ValidadorSolicitud siguiente;

    /** Enlaza el siguiente eslabon y lo devuelve para poder encadenar llamadas. */
    public ValidadorSolicitud siguiente(ValidadorSolicitud siguiente) {
        this.siguiente = siguiente;
        return siguiente;
    }

    public ResultadoValidacion validar(SolicitudReembolso solicitud,
                                       List<SolicitudReembolso> pendientesDelEmpleado) {
        ResultadoValidacion propio = validarPropio(solicitud, pendientesDelEmpleado);
        if (!propio.valida() || siguiente == null) {
            return propio;
        }
        return siguiente.validar(solicitud, pendientesDelEmpleado);
    }

    /** Verificacion especifica de este eslabon. */
    protected abstract ResultadoValidacion validarPropio(
            SolicitudReembolso solicitud, List<SolicitudReembolso> pendientesDelEmpleado);
}
