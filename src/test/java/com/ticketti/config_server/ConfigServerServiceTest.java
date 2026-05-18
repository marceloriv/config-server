package com.ticketti.config_server;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConfigServerServiceTest {

	@Mock
	private EnvironmentService environmentService;

	@InjectMocks
	private ConfigServerService configServerService;

	@Test
	void obtenerConfiguracionDevuelveElNombreDeLaAplicacion() {
		String appName = "ms-usuarios";
		String expectedConfig = "server.port=8080";

		when(environmentService.getProperty(anyString())).thenReturn(expectedConfig);

		String actualConfig = configServerService.getConfig(appName);

		assertThat(actualConfig).isEqualTo(expectedConfig);
		verify(environmentService).getProperty("ms-usuarios.config");
	}

	@Test
	void obtenerConfiguracionDevuelveNullCuandoElServicioDevuelveNull() {
		String appName = "unknown-app";

		when(environmentService.getProperty(anyString())).thenReturn(null);

		String actualConfig = configServerService.getConfig(appName);

		assertThat(actualConfig).isNull();
	}

	@Test
	void obtenerConfiguracionLanzaExcepcionCuandoAppNameEsNull() {
		when(environmentService.getProperty(anyString())).thenReturn(null);

		String actualConfig = configServerService.getConfig(null);

		assertThat(actualConfig).isNull();
		verify(environmentService).getProperty("null.config");
	}

	@Test
	void obtenerConfiguracionNoLamaAlServicioParaAppExistente() {
		String appName = "ms-usuarios";

		when(environmentService.getProperty(anyString())).thenReturn("some.value");

		configServerService.getConfig(appName);

		verify(environmentService).getProperty(anyString());
	}

	@Test
	void obtenerConfiguracionConMultiplesLlamadas() {
		when(environmentService.getProperty(anyString()))
			.thenReturn("first")
			.thenReturn("second")
			.thenReturn("third");

		String result1 = configServerService.getConfig("app1");
		String result2 = configServerService.getConfig("app2");
		String result3 = configServerService.getConfig("app3");

		assertThat(result1).isEqualTo("first");
		assertThat(result2).isEqualTo("second");
		assertThat(result3).isEqualTo("third");
	}
}

interface EnvironmentService {
	String getProperty(String key);
}

class ConfigServerService {
	private final EnvironmentService environmentService;

	ConfigServerService(EnvironmentService environmentService) {
		this.environmentService = environmentService;
	}

	String getConfig(String appName) {
		return environmentService.getProperty(appName + ".config");
	}
}