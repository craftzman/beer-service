package org.zot.chai.beerservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestEnvironmentConfiguration.class)
class BeerServiceApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void whenGetRequestWithIdThenBeerReturned(){
		var beerToCreated = new Beer(null,"name");
		Beer expectedBeer = webTestClient
				.post()
				.uri("/beers")
				.bodyValue(beerToCreated)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(Beer.class).value( beer -> assertThat(beer).isNotNull())
				.returnResult().getResponseBody();

		webTestClient
				.get()
				.uri("/beers/" + expectedBeer.id())
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(Beer.class).value( actualBeer -> {
					assertThat(actualBeer).isNotNull();
					assertThat(actualBeer.name()).isEqualTo(expectedBeer.name());
				});
	}

}
