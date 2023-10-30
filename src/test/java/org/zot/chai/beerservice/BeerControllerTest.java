package org.zot.chai.beerservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
//@AutoConfigureMockMvc
class BeerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BeerRepository beerRepository;

    List<Beer> beers;

    @BeforeEach
    void setup(){
        beers = List.of(
                new Beer(1L, "Erdinger"),
                new Beer(2L, "Asashi"));
    }

    @Test
    void shouldFindAllBeers() throws Exception {

        when(beerRepository.findAll()).thenReturn(beers);

        this.mockMvc.perform(get("/api/beers"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(beers)));
    }

    @Test
    void  shouldFoundBeerById() throws Exception {
        when(beerRepository.findById(anyLong())).thenReturn(Optional.of(beers.get(0)));

        this.mockMvc.perform(get("/api/beers/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(beers.get(0))));
    }

}
