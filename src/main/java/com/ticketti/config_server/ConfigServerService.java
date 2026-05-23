package com.ticketti.config_server;

/**
 * Servicio que recupera la configuración de una aplicación consultando la propiedad
 * {@code <appName>.config} a través de {@link EnvironmentService}.
 */
public class ConfigServerService {
	private final EnvironmentService environmentService;

	/**
	 * Construye un nuevo ConfigServerService con el servicio de entorno especificado.
	 *
	 * @param environmentService el servicio de entorno para obtener propiedades
	 */
	public ConfigServerService(EnvironmentService environmentService) {
		this.environmentService = environmentService;
	}

	/**
	 * Recupera la configuración de una aplicación consultando la propiedad
	 * {@code <appName>.config} a través de {@link EnvironmentService}.
	 *
	 * @param appName nombre de la aplicación; si es {@code null} consulta {@code "null.config"}
	 * @return el valor de la propiedad de configuración, o {@code null} si no existe
	 */
	public String getConfig(String appName) {
		return environmentService.getProperty(appName + ".config");
	}
}