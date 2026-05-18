package com.ticketti.config_server;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ConfigServerApplicationTests {

	@org.springframework.beans.factory.annotation.Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextoCarga() {
		assertThat(this.restTemplate).isNotNull();
	}

	@Test
	void elServidorDeConfiguracionDevuelveConfiguracionParaLasAplicacionesRegistradas() {
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

		for (String aplicacion : aplicaciones) {
			ResponseEntity<String> response = this.restTemplate.getForEntity(
				"/" + aplicacion + "/default", String.class);

			assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
			assertThat(response.getBody()).contains("\"name\":\"" + aplicacion + "\"");
		}
	}

	@Test
	void elServidorDeConfiguracionDevuelveRespuestaVaciaParaAplicacionInexistente() {
		ResponseEntity<String> response = this.restTemplate.getForEntity(
			"/aplicacion-inexistente/default", String.class);

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).contains("\"name\":\"aplicacion-inexistente\"");
		assertThat(response.getBody()).contains("\"propertySources\":[]");
	}
}
