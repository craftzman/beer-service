package org.zot.chai.beerservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BeerJsonTest {

    @Autowired
    private JacksonTester<Beer> json;

    @Test
    void testDeserialize() throws Exception {
        var beer = """
                {
                    "id": 1,
                    "name": "Erdinger"
                }
                """;

        assertThat(json.parse(beer))
                .usingRecursiveComparison()
                .isEqualTo(new Beer(1L,"Erdinger"));
    }

    @Test
    void testSerialize() throws Exception {
        var beer = new Beer(1L, "Erdinger");
        var jsonContent = json.write(beer);

        assertThat(jsonContent).extractingJsonPathNumberValue("@.id")
                .isEqualTo(beer.id().intValue());
        assertThat(jsonContent).extractingJsonPathStringValue("@.name")
                .isEqualTo(beer.name());
    }
}
