package com.empresa.reembolsos.procesamiento.notificacion;

import com.empresa.reembolsos.dominio.EstadoSolicitud;
import com.empresa.reembolsos.dominio.SolicitudReembolso;

/**
 * Interesado en los cambios de estado de una solicitud (patron Observer).
 * Cada implementacion decide por su cuenta si reacciona y como.
 */
public interface ObservadorCambioEstado {

    void onCambioEstado(SolicitudReembolso solicitud, EstadoSolicitud estadoAnterior);
}
