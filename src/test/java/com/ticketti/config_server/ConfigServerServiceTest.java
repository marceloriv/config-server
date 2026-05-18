package com.ticketti.config_server;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
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
    }

    @Test
    void obtenerConfiguracionDevuelveNullCuandoElServicioDevuelveNull() {
        String appName = "unknown-app";

        when(environmentService.getProperty(anyString())).thenReturn(null);

        String actualConfig = configServerService.getConfig(appName);

        assertThat(actualConfig).isNull();
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
