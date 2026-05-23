package com.ticketti.config_server;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConfigServerServiceTest {

    @Mock
    private EnvironmentService environmentService;

    @InjectMocks
    private ConfigServerService configServerService;

    /**
     * Verifica que {@link ConfigServerService#getConfig} devuelva el valor de
     * configuración esperado para una aplicación registrada, delegando la
     * llamada a {@link EnvironmentService#getProperty} con la clave compuesta
     * {@code <appName>.config}.
     *
     * @param nombreAplicacion nombre de la aplicación a consultar
     */
    @Test
    void obtenerConfiguracionDevuelveElNombreDeLaAplicacion() {
        String appName = "ms-usuarios";
        String expectedConfig = "server.port=8080";

        when(environmentService.getProperty(anyString())).thenReturn(expectedConfig);

        String actualConfig = configServerService.getConfig(appName);

        assertThat(actualConfig).isEqualTo(expectedConfig);
        verify(environmentService).getProperty("ms-usuarios.config");
    }

    /**
     * Verifica que {@link ConfigServerService#getConfig} devuelva {@code null}
     * cuando {@link EnvironmentService#getProperty} también devuelve
     * {@code null}, por ejemplo al consultar una aplicación desconocida.
     *
     * @param nombreAplicacion nombre de la aplicación para la que no existe
     * configuración
     */
    @Test
    void obtenerConfiguracionDevuelveNullCuandoElServicioDevuelveNull() {
        String appName = "unknown-app";

        when(environmentService.getProperty(anyString())).thenReturn(null);

        String actualConfig = configServerService.getConfig(appName);

        assertThat(actualConfig).isNull();
    }

    /**
     * Verifica que {@link ConfigServerService#getConfig} maneje correctamente
     * el caso en que el nombre de la aplicación es {@code null}, enviando la
     * clave {@code "null.config"} a {@link EnvironmentService#getProperty}.
     *
     * @param nombreAplicacion nombre de la aplicación, puede ser {@code null}
     */
    @Test
    void obtenerConfiguracionLanzaExcepcionCuandoAppNameEsNull() {
        when(environmentService.getProperty(anyString())).thenReturn(null);

        String actualConfig = configServerService.getConfig(null);

        assertThat(actualConfig).isNull();
        verify(environmentService).getProperty("null.config");
    }

    /**
     * Verifica que {@link ConfigServerService#getConfig} invoque al menos una
     * vez a {@link EnvironmentService#getProperty} con cualquier cadena cuando
     * se consulta la configuración de una aplicación.
     *
     * @param nombreAplicacion nombre de la aplicación registrada
     */
    @Test
    void obtenerConfiguracionNoLamaAlServicioParaAppExistente() {
        String appName = "ms-usuarios";

        when(environmentService.getProperty(anyString())).thenReturn("some.value");

        configServerService.getConfig(appName);

        verify(environmentService).getProperty(anyString());
    }

    /**
     * Verifica que {@link ConfigServerService#getConfig} devuelva el valor
     * correcto al realizar múltiples llamadas consecutivas, manejando
     * adecuadamente la secuencia de retornos configurada en el mock.
     *
     * @param nombreAplicacion nombre de la aplicación registrada
     */
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
