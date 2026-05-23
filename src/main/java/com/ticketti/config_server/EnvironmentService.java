package com.ticketti.config_server;

/**
 * Obtiene el valor de una propiedad de configuración a partir de la clave indicada.
 */
public interface EnvironmentService {

	/**
	 * Devuelve el valor de la propiedad identificada por la clave dada.
	 *
	 * @param key clave de la propiedad
	 * @return valor de la propiedad, o {@code null} si no existe
	 */
	String getProperty(String key);
}
