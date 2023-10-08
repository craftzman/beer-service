package org.zot.chai.beerservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(BeerController.class)
@AutoConfigureMockMvc
class BeerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BeerRepository beerRepository;

    List<Beer> beers = List.of(
            new Beer(1L, "Erdinger"),
            new Beer(2L, "Asashi"));

    @BeforeEach
    void setup(){

    }

    @Test
    void shouldFindAllBeers() throws Exception {

        this.mockMvc.perform(get("/api/beers"))
                .andExpect(status().isOk())
                .andExpect(content().string("Erdinger"));
    }


}
