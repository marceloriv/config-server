package com.ticketti.config_server;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConfigServerApplicationTests {

	@org.springframework.beans.factory.annotation.Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextoCarga() {
	}

	@Test
	void elServidorDeConfiguracionSirveConfiguracionDeMsUsuarios() {
		ResponseEntity<String> response = this.restTemplate.getForEntity(
			"/ms-usuarios/default", String.class);

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).contains("ms-usuarios");
	}

	@Test
	void elServidorDeConfiguracionSirveConfiguracionDeMsEventos() {
		ResponseEntity<String> response = this.restTemplate.getForEntity(
			"/ms-eventos/default", String.class);

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).contains("ms-eventos");
	}

	@Test
	void elServidorDeConfiguracionSirveConfiguracionDeDiscoveryServer() {
		ResponseEntity<String> response = this.restTemplate.getForEntity(
			"/discovery-server/default", String.class);

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).contains("discovery-server");
	}

	@Test
	void elServidorDeConfiguracionSirveConfiguracionDeApiGateway() {
		ResponseEntity<String> response = this.restTemplate.getForEntity(
			"/api-gateway/default", String.class);

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).contains("api-gateway");
	}

	@Test
	void elServidorDeConfiguracionSirveConfiguracionDeMsMensajeria() {
		ResponseEntity<String> response = this.restTemplate.getForEntity(
			"/ms-mensajeria/default", String.class);

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).contains("ms-mensajeria");
	}

	@Test
	void elServidorDeConfiguracionSirveConfiguracionDeMsDonaciones() {
		ResponseEntity<String> response = this.restTemplate.getForEntity(
			"/ms-donaciones/default", String.class);

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).contains("ms-donaciones");
	}

	@Test
	void elServidorDeConfiguracionSirveConfiguracionDeMsCarrito() {
		ResponseEntity<String> response = this.restTemplate.getForEntity(
			"/ms-carrito/default", String.class);

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).contains("ms-carrito");
	}

	@Test
	void elServidorDeConfiguracionSirveConfiguracionDeBff() {
		ResponseEntity<String> response = this.restTemplate.getForEntity(
			"/bff-back-for-frontend/default", String.class);

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).contains("bff-back-for-frontend");
	}
}