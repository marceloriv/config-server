package com.ticketti.config_server;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ConfigServerApplicationTests {

	@Autowired
	private Environment environment;

	@LocalServerPort
	private int port;

	/**
	 * Verifica que el contexto de Spring se cargue correctamente y que
	 * el bean {@link Environment} esté disponible en el contexto.
	 */
	@Test
	void contextoCarga() {
		assertThat(environment).isNotNull();
		assertThat(port).isGreaterThan(0);
	}

	/**
	 * Verifica que el servidor de configuración devuelva la configuración
	 * correcta para cada una de las aplicaciones registradas en el sistema,
	 * comprobando que la respuesta contenga el nombre de la aplicación
	 * y un código de estado HTTP exitoso.
	 */
	@Test
	void elServidorDeConfiguracionDevuelveConfiguracionParaLasAplicacionesRegistradas() throws Exception {
		List<String> aplicaciones = List.of(
			"ms-usuarios",
			"ms-eventos",
			"discovery-server",
			"api-gateway",
			"ms-mensajeria",
			"ms-donaciones",
			"ms-carrito",
			"bff-back-for-frontend"
		);

		HttpClient client = HttpClient.newHttpClient();
		for (String aplicacion : aplicaciones) {
			HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:" + port + "/" + aplicacion + "/default"))
				.GET()
				.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			assertThat(response.statusCode()).isEqualTo(200);
			assertThat(response.body()).contains("\"name\":\"" + aplicacion + "\"");
		}
	}

	/**
	 * Verifica que el servidor de configuración devuelve una respuesta válida
	 * con {@code propertySources} vacío cuando se consulta una aplicación
	 * que no se encuentra registrada en el sistema.
	 */
	@Test
	void elServidorDeConfiguracionDevuelveRespuestaVaciaParaAplicacionInexistente() throws Exception {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create("http://localhost:" + port + "/aplicacion-inexistente/default"))
			.GET()
			.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		assertThat(response.statusCode()).isEqualTo(200);
		assertThat(response.body()).contains("\"name\":\"aplicacion-inexistente\"");
		assertThat(response.body()).contains("\"propertySources\":[]");
	}
}
